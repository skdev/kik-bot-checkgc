package sk96.dev.kik.bot.message;

public class MultiTextMessageEncoder extends MessageEncoder<MultiTextMessage> {

    @Override
    public String encode(MultiTextMessage message) {
        StringBuilder json = new StringBuilder("{ \"messages\": [");
        for (int i = 0; i < message.body.length; i++) {
            String body = message.body[i];
            json.append("{\"chatId\": \"").append(message.chatId).append("\",");
            json.append("\"type\": \"").append(message.type.toString()).append("\",");
            json.append("\"to\": \"").append(message.to).append("\",");
            if (i == (message.body.length - 1)) {
                json.append("\"body\": \"").append(body).append("\"}");
            } else {
                json.append("\"body\": \"").append(body).append("\"},");
            }
        }
        json.append("]}");
        return json.toString();
    }
}