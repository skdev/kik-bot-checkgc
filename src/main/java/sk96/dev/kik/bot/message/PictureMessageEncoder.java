package sk96.dev.kik.bot.message;

public class PictureMessageEncoder extends MessageEncoder<PictureMessage> {

    @Override
    public String encode(PictureMessage message) {
        String json = "{ \"messages\": [{";
        json += "\"chatId\": \"" + message.chatId + "\",";
        json += "\"type\": \"" + message.type.toString() + "\",";
        json += "\"to\": \"" + message.to + "\",";
        json += "\"picUrl\": \"" + message.picUrl + "\"";
        json += "}]}";
        return json;
    }
}