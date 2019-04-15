package sk96.dev.kik.bot.message;

public class KeyboardMessage extends Message {
    public final String hidden;
    public final String title;
    public final String[] body;

    public KeyboardMessage(String chatId, String to, String hidden, String title, String... body) {
        super(MessageType.TEXT, chatId, to, MessageChatType.SUGGESTED);
        this.hidden = hidden;
        this.title = title;
        this.body = body;
    }
}