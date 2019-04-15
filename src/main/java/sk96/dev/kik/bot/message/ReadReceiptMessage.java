package sk96.dev.kik.bot.message;

public class ReadReceiptMessage extends Message {
    public final String timestamp;

    public ReadReceiptMessage(String chatId, String to, String timestamp) {
        super(MessageType.READ_RECEIPT, chatId, to, MessageChatType.DIRECT);
        this.timestamp = timestamp;
    }
}