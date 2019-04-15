package sk96.dev.kik.bot.message;

public class StartChattingMessageDecoder extends MessageDecoder<StartChattingMessage> {

    @Override
    public StartChattingMessage decode(String json) {
        final String chatId = getString(json, "chatId");
        final String to = getString(json, "from");
        final String body = "";
        final String chatType = getString(json, "chatType");
        MessageChatType type = MessageChatType.UNKNOWN;
        if (chatType.toLowerCase().trim().equalsIgnoreCase("direct")) {
            type = MessageChatType.DIRECT;
        } else if (chatType.toLowerCase().trim().equalsIgnoreCase("private")) {
            type = MessageChatType.PRIVATE;
        }
        return new StartChattingMessage(chatId, to, body, type);
    }
}