package sk96.dev.kik.bot.message;

public class IsTypingMessage extends Message {
    public final boolean isTyping;

    public IsTypingMessage(String chatId, String to, boolean isTyping, MessageChatType type) {
        super(MessageType.IS_TYPING, chatId, to, type);
        this.isTyping = isTyping;
    }
}