package sk96.dev.kik.bot.message;

public class StartChattingMessageHandler extends MessageHandler<StartChattingMessage> {
    private static final String[] START_CHATTING = {
            "FortniteAce is a bot used to search and compare player stats for Fortnite.",
            "The bot is under new ownership so there will be more frequent development and new features. If you want more information say 'information'."
                    + "\\n\\nIf you need help, say 'help', alternatively join #FortniteAce or #fnstats",
    };

    @Override
    public Message handle(StartChattingMessage message) {
        return new MultiTextMessage(message.chatId, message.to, START_CHATTING);
    }
}