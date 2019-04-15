package sk96.dev.kik.bot;

import com.jayway.restassured.path.json.JsonPath;
import io.vertx.core.Vertx;
import sk96.dev.kik.bot.command.broadcast.Chat;
import sk96.dev.kik.bot.command.persistence.HibernateUtil;
import sk96.dev.kik.bot.message.MessageDecoder;
import sk96.dev.kik.bot.message.WebhookMessage;
import sk96.dev.kik.bot.message.WebhookMessageDecoder;
import sk96.dev.kik.bot.message.WebhookMessageEncoder;
import sk96.dev.kik.bot.server.HttpServerVerticle;
import sk96.dev.kik.bot.server.Server;
import sk96.dev.kik.bot.utils.logging.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public final class Main {
    private static final Logger L = Logger.getTextFileLogger("Main");
    public static Configuration configuration;

    public static void main(String[] args) {
        L.info("Starting Kik Bot");

        L.info("Opening connection to database");
        if(!HibernateUtil.open()) {
            L.error("Connection to database failed.");
            return;
        }
        L.info("Connection to database successful");

        L.info("Loading configuration");
        String config;
        if(args.length > 0) {
            config = args[0].trim();
        } else {
            config = "config.json";
        }
        configuration = loadConfig(config);
        if(null == configuration) {
            L.warning("Could not load configuration.");
            return;
        }
        L.info("Loaded configuration");

        File f = new File("chat.txt");
        if(f.exists()) {
            new Thread(() -> {
                L.info("Loading chat.txt, this will be done async");
                try (BufferedReader reader = new BufferedReader(new FileReader("chat.txt"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String data = line;
                        data = data.substring(data.indexOf("[Info]")).trim();
                        data = data.replaceFirst("\\[Info]", "").trim();
                        data = data.replaceFirst("\\[Chat]", "").trim();
                        data = data.replaceFirst("\\[to=", "").trim();
                        data = data.replaceFirst("chatId=", "").trim();
                        data = data.replaceFirst("]", "").trim();
                        String[] parts = data.split(",");
                        Chat.chats.add(new Chat(parts[1], parts[0], false));
                    }
                    /* Removes duplicate chatIds */
                    List<Chat> chats = new ArrayList<>(Chat.chats);
                    for (Chat c : chats) {
                        for (Chat c1 : chats) {
                            if (!c.chatId.equalsIgnoreCase(c1.chatId)) {
                                break;
                            }
                            Chat.chats.remove(c);
                        }
                    }
                    Chat.chats = chats;
                    System.out.println("Finished loading chat.txt: " + Chat.chats.size() + " chat Ids loaded");
                } catch (IOException e) {
                    L.warning("Error loading chats.txt: " + e.getMessage());
                }
            }).start();
        } else {
            L.warning("No chat.txt found, ignoring error...");
        }

        L.info("Sending web hook");
        final WebhookMessage message = new WebhookMessage(configuration.host + ":" + configuration.port, configuration.receiveReadReceipts, configuration.receiveIsTyping, configuration.manuallySendReadReceipts, configuration.receiveDeliveryReceipts, false);
        final String base64Key = Base64.getEncoder().encodeToString((configuration.botName + ":" + configuration.apiKey).getBytes(StandardCharsets.UTF_8));
        final String result = Server.post(base64Key, configuration.apiURL + "/config", new WebhookMessageEncoder().encode(message));
        if(result.length() <= 0) {
            L.error("Received no response from Kik after sending web hook.");
            return;
        }
        L.info("Result from sending web hook: " + result);
        final MessageDecoder<WebhookMessage> decoder = new WebhookMessageDecoder();
        final WebhookMessage decodedMessage = decoder.decode(result);
        if(!decodedMessage.equals(message)) {
            L.error("Error validating web hook");
            return;
        }
        L.info("Web hook has been verified");

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpServerVerticle(configuration.apiURL, base64Key, configuration.port));
    }

    private static Configuration loadConfig(String path) {
        final File file = new File(path);
        if (!file.exists()) {
            L.error("Could not find or load " + path);
            return null;
        }

        byte[] arr;

        try {
            arr = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            L.error("Unable to load " + path + ": " + e.getMessage());
            return null;
        }

        final String json = new String(arr, Charset.forName("UTF-8"));
        final String api_url = JsonPath.from(json).get("api.api_url");
        final String name = JsonPath.from(json).get("api.name");
        final String key = JsonPath.from(json).get("api.key");
        final String host = JsonPath.from(json).get("webhook.host");
        final int port = Integer.parseInt(JsonPath.from(json).get("webhook.port"));
        final boolean receiveReadReceipts = Boolean.parseBoolean(JsonPath.from(json).get("webhook.receiveReadReceipts"));
        final boolean receiveIsTyping = Boolean.parseBoolean(JsonPath.from(json).get("webhook.receiveIsTyping"));
        final boolean manuallySendReadReceipts = Boolean.parseBoolean(JsonPath.from(json).get("webhook.manuallySendReadReceipts"));
        final boolean receiveDeliveryReceipts = Boolean.parseBoolean(JsonPath.from(json).get("webhook.receiveDeliveryReceipts"));
        return new Configuration(api_url,name, key, host, port, receiveReadReceipts, receiveIsTyping, manuallySendReadReceipts, receiveDeliveryReceipts);
    }
}