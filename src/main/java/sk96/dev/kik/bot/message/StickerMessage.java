package sk96.dev.kik.bot.message;

public class StickerMessage extends Message {
    public final String stickerPackId;
    public final String stickerUrl;

    public StickerMessage(String chatId, String to, String stickerPackId, String stickerUrl, MessageChatType type) {
        super(MessageType.STICKER, chatId, to, type);
        this.stickerPackId = stickerPackId;
        this.stickerUrl = stickerUrl;
    }
}