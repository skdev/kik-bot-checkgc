package sk96.dev.kik.bot.message;

public class DummyMessage extends Message {
    public final String body;

    public DummyMessage(String chatId, String to, String body, MessageChatType type) {
        super(MessageType.TEXT, chatId, to, type);
        this.body = body;
    }
}