package sk96.dev.kik.bot.message;

public class StartChattingMessage extends Message {
    public final String body;

    public StartChattingMessage(String chatId, String to, String body, MessageChatType type) {
        super(MessageType.TEXT, chatId, to, type);
        this.body = body;
    }
}