package sk96.dev.kik.bot.server;

import sk96.dev.kik.bot.utils.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class Server {
    private static final Logger L = Logger.getTextFileLogger("Server");

    public static void postOnNewThread(String base64Key, String url, String data) {
        new Thread(() -> {
            final StringBuilder result = new StringBuilder();
            try {
                final URL url2 = new URL(url);
                final HttpURLConnection http = (HttpURLConnection) url2.openConnection();
                http.setRequestMethod("POST");
                http.setRequestProperty("Authorization", "Basic " + base64Key);
                http.setRequestProperty("Content-Type", "application/json");
                http.setRequestProperty("Content-Length", Arrays.toString(data.getBytes()));
                http.setDoOutput(true);
                http.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

                final Reader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8));

                int read;
                while ((read = reader.read()) >= 0) {
                    result.append((char) read);
                }

                L.info("Received response: " + result.toString());
            } catch (Exception e) {
                L.error("Failed to send POST to '" + url + "': Error: " + e.getMessage());
            }
        }).start();
    }

    public static String post(String base64Key, String url, String data) {

        final StringBuilder result = new StringBuilder();
        try {
            final URL url2 = new URL(url);
            final HttpURLConnection http = (HttpURLConnection) url2.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Authorization", "Basic " + base64Key);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Content-Length", Arrays.toString(data.getBytes()));
            http.setDoOutput(true);
            http.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

            final Reader reader = new BufferedReader(new InputStreamReader(http.getInputStream(), StandardCharsets.UTF_8));

            int read;
            while ((read = reader.read()) >= 0) {
                result.append((char) read);
            }

            L.info("Received response: " + result.toString());

            return result.toString();
        } catch (Exception e) {
            L.error("Failed to send POST to '" + url + "': Error: " + e.getMessage());
            return result.toString();
        }
    }
}