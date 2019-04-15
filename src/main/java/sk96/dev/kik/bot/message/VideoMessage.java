package sk96.dev.kik.bot.message;

public class VideoMessage extends Message {
    public final String videoUrl;
    public final boolean loop;
    public final boolean muted;
    public final boolean autoplay;
    public final boolean noSave;

    public VideoMessage(String chatId, String to, String videoUrl, boolean loop, boolean muted, boolean autoplay, boolean noSave, MessageChatType type) {
        super(MessageType.VIDEO, chatId, to, type);
        this.videoUrl = videoUrl;
        this.loop = loop;
        this.muted = muted;
        this.autoplay = autoplay;
        this.noSave = noSave;
    }
}