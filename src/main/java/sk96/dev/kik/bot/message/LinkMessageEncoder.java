package sk96.dev.kik.bot.message;

public class LinkMessageEncoder extends MessageEncoder<LinkMessage> {
    @Override
    public String encode(LinkMessage message) {
        String json = "{ \"messages\": [{";
        json += "\"chatId\": \"" + message.chatId + "\",";
        json += "\"type\": \"" + message.type.toString() + "\",";
        json += "\"to\": \"" + message.to + "\",";
        json += "\"url\": \"" + message.url + "\"";
        json += "}]}";
        return json;
    }
}
