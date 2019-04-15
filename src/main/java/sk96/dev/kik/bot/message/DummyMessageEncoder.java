package sk96.dev.kik.bot.message;

public class DummyMessageEncoder extends MessageEncoder<DummyMessage> {

    @Override
    public String encode(DummyMessage message) {
        String json = "{ \"messages\": [{";
        json += "\"chatId\": \"" + message.chatId + "\",";
        json += "\"type\": \"" + message.type.toString() + "\",";
        json += "\"to\": \"" + message.to + "\",";
        json += "\"body\": \"" + message.body + "\"";
        json += "}]}";
        return json;
    }
}