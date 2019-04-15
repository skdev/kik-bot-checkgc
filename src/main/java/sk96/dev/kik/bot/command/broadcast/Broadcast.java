package sk96.dev.kik.bot.command.broadcast;

import sk96.dev.kik.bot.Main;
import sk96.dev.kik.bot.message.Codec;
import sk96.dev.kik.bot.message.MessageEncoder;
import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.server.Server;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Broadcast {
    public static void broadcast(String chatId, String to, String say) {
        final MultiTextMessage message = new MultiTextMessage(chatId, to, say);
        final MessageEncoder encoder = Codec.encoders.get(message.getClass());
        final String base64Key = Base64.getEncoder().encodeToString((Main.configuration.botName + ":" + Main.configuration.apiKey).getBytes(StandardCharsets.UTF_8));
        Server.post(base64Key, Main.configuration.apiURL + "/message", encoder.encode(message));
    }

    public static void broadcast(String toSay) {
        new Thread(() -> {
            List<Chat> chats = Chat.chats;
            List<String> sent = new ArrayList<>();
            for(Chat c : chats) {
                if(sent.contains(c.chatId.toLowerCase())) {
                    continue;
                }
                sent.add(c.chatId.toLowerCase());
                String chatId = c.chatId;
                String to = c.to;
                broadcast(chatId, to, toSay);
            }
        }).start();
    }
}