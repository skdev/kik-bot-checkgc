package sk96.dev.kik.bot.utils;

public class JsonSearch {
    public static boolean has(String json, String key) {
        return json.contains("\"" + key + "\":");
    }

    public static String get(final String json, String key) {
        final String formattedKey = "\"" + key + "\":";
        final int start = json.indexOf(formattedKey);
        return start < 0 ? "-1" : json.substring(start)
                .replace(formattedKey, "")
                .trim()
                .split("\"")[1];
    }

    public static String getKdr(final String json) {
        final String formattedKey = "\"key\": \"K/d\",";
        final int start = json.indexOf(formattedKey);
        return start < 0 ? "Unknown" : json.substring(start)
                .replace(formattedKey, "")
                .trim()
                .split("\n")[0]
                .replaceAll("\"value\": \"", "")
                .replaceAll("\"", "")
                .trim();
    }
}