package sk96.dev.kik.bot.message;

import com.jayway.restassured.path.json.JsonPath;
import sk96.dev.kik.bot.utils.JsonSearch;

import java.util.List;

public abstract class MessageDecoder<T extends Message> {
    /**
     * Decodes JSON data received from Kik into a suitable Message object.
     */
    public abstract T decode(String json);

    /**
     * Helper function to get a String value from a json by it's key
     */
    protected String getString(String json, String name) {
        if (JsonSearch.has(json, name)) {
            return JsonSearch.get(json, name);
        }
        throw new IllegalArgumentException("Cannot find " + name + " in json: " + json);
    }

    public List<?> getStringArr(String json, String name) {
        return JsonPath.from(json).get(name);
    }

    /**
     * Helper function to get a Boolean value from a json by it's key
     */
    protected boolean getBoolean(String json, String name) {
        return Boolean.parseBoolean(getString(json, name));
    }
}