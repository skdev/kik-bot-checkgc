package sk96.dev.kik.bot.message;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public abstract class MessageEncoder<T extends Message> {
    protected final Map<String, String> out = new HashMap<>();

    protected void put(String key, Object value) {
        out.put(key, String.valueOf(value));
    }

    protected String toJson() {
        return new Gson().toJson(out);
    }

    public abstract String encode(T message);
}