package sk96.dev.kik.bot.message;

public class StartChattingMessageEncoder extends MessageEncoder<StartChattingMessage> {

    @Override
    public String encode(StartChattingMessage message) {
        String json = "{ \"messages\": [{";
        json += "\"chatId\": \"" + message.chatId + "\",";
        json += "\"type\": \"" + message.type.toString() + "\",";
        json += "\"to\": \"" + message.to + "\",";
        json += "\"body\": \"" + message.body + "\"";
        json += "}]}";
        return json;
    }

}