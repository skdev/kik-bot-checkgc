package sk96.dev.kik.bot.message;

public class IsTypingMessageDecoder extends MessageDecoder<IsTypingMessage> {

    @Override
    public IsTypingMessage decode(String json) {
        final String chatId = getString(json, "chatId");
        final String to = getString(json, "from");
        final boolean isTyping = getBoolean(json, "isTyping");
        final String chatType = getString(json, "chatType");
        MessageChatType type = MessageChatType.UNKNOWN;
        if (chatType.toLowerCase().trim().equalsIgnoreCase("direct")) {
            type = MessageChatType.DIRECT;
        } else if (chatType.toLowerCase().trim().equalsIgnoreCase("private")) {
            type = MessageChatType.PRIVATE;
        }
        return new IsTypingMessage(chatId, to, isTyping, type);
    }

}