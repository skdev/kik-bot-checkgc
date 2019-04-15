package sk96.dev.kik.bot.message;

public class TextMessageEncoder extends MessageEncoder<TextMessage> {

    @Override
    public String encode(TextMessage message) {
        String json = "{ \"messages\": [{";
        json += "\"chatId\": \"" + message.chatId + "\",";
        json += "\"type\": \"" + message.type.toString() + "\",";
        json += "\"to\": \"" + message.to + "\",";
        json += "\"body\": \"" + message.body + "\"";
        json += "}]}";
        return json;
    }
}