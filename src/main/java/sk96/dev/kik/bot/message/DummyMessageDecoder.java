package sk96.dev.kik.bot.message;

public class DummyMessageDecoder extends MessageDecoder<DummyMessage> {

    @Override
    public DummyMessage decode(String json) {
        final String chatId = getString(json, "chatId");
        final String to = getString(json, "from");
        final String chatType = getString(json, "chatType");
        MessageChatType type = MessageChatType.UNKNOWN;
        if (chatType.toLowerCase().trim().equalsIgnoreCase("direct")) {
            type = MessageChatType.DIRECT;
        } else if (chatType.toLowerCase().trim().equalsIgnoreCase("private")) {
            type = MessageChatType.PRIVATE;
        }
        return new DummyMessage(chatId, to, "", type);
    }
}