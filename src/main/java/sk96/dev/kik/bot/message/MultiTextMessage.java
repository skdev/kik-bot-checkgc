package sk96.dev.kik.bot.message;

public class MultiTextMessage extends Message {
    public final String to;
    public final String[] body;

    public MultiTextMessage(String chatId, String to, String... body) {
        super(MessageType.TEXT, chatId, to, MessageChatType.DIRECT);
        this.to = to;
        this.body = body;
    }
}