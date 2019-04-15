package sk96.dev.kik.bot.message;

public class KeyboardMessageEncoder extends MessageEncoder<KeyboardMessage> {

    @Override
    public String encode(KeyboardMessage message) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"messages\": [");
        json.append("{");
        json.append("\"chatId\": \"").append(message.chatId).append("\",");
        json.append("\"type\": \"").append(message.type.toString()).append("\",");
        json.append("\"to\": \"").append(message.to).append("\",");
        json.append("\"body\": \"").append(message.title).append("\",");
        json.append("\"keyboards\": [");
        json.append("{");
        json.append("\"type\": \"" + "suggested" + "\",");
        json.append("\"responses\": [");
        for (int i = 0; i < message.body.length; i++) {
            String body = message.body[i];
            json.append("{");
            json.append("\"type\": \"" + "text" + "\",");
            json.append("\"body\": \"").append(body).append("\"");
            if (i == (message.body.length - 1)) {
                json.append("}");
                json.append("]");
            } else {
                json.append("},");
            }
        }
        json.append("}");
        json.append("]");
        json.append("}");
        json.append("]");
        json.append("}");
        return json.toString();
    }
}