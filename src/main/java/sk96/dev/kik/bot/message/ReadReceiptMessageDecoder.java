package sk96.dev.kik.bot.message;

public class ReadReceiptMessageDecoder extends MessageDecoder<ReadReceiptMessage> {

    @Override
    public ReadReceiptMessage decode(String json) {
        final String chatId = getString(json, "chatId");
        final String to = getString(json, "from");
        final String timestamp = getString(json, "timestamp");
        return new ReadReceiptMessage(chatId, to,timestamp);
    }
}