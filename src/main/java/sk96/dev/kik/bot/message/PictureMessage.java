package sk96.dev.kik.bot.message;

public class PictureMessage extends Message {
    public final String picUrl;
    public final MediaAttribute attribute;

    public PictureMessage(String chatId, String to, String picUrl, MediaAttribute attribute, MessageChatType type) {
        super(MessageType.PICTURE, chatId, to, type);
        this.picUrl = picUrl;
        this.attribute = attribute;
    }
}