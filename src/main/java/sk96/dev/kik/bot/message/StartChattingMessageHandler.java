package sk96.dev.kik.bot.message;

public class StartChattingMessageHandler extends MessageHandler<StartChattingMessage> {
    private static final String[] START_CHATTING = {
            "CheckGC is a bot that allows you to see active and inactive members in a group chat.",
            "To get started say 'help'\\nYou can also join the #CheckGC group chat.",
    };

    @Override
    public Message handle(StartChattingMessage message) {
        return new MultiTextMessage(message.chatId, message.to, START_CHATTING);
    }
}