package sk96.dev.kik.bot.message;

public class PictureMessageDecoder extends MessageDecoder<PictureMessage> {

    @Override
    public PictureMessage decode(String json) {
        final String chatId = getString(json, "chatId");
        final String to = getString(json, "from");
        final String url = getString(json, "picUrl");
        final String chatType = getString(json, "chatType");
        MessageChatType type = MessageChatType.UNKNOWN;
        if (chatType.toLowerCase().trim().equalsIgnoreCase("direct")) {
            type = MessageChatType.DIRECT;
        } else if (chatType.toLowerCase().trim().equalsIgnoreCase("private")) {
            type = MessageChatType.PRIVATE;
        }
        return new PictureMessage(chatId, to, url, MediaAttribute.GALLERY, type);
    }
}