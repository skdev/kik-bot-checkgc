package sk96.dev.kik.bot.message;

public class ScanMessage extends Message {
    public final String data;

    public ScanMessage(String chatId, String to, String data) {
        super(MessageType.SCAN, chatId, to, MessageChatType.DIRECT);
        this.data = data;
    }
}