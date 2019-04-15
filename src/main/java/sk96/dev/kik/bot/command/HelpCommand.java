package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;

public class HelpCommand extends Command<MultiTextMessage, TextMessage> {
    private static final String[] HELP = {
            "Coming soon."
    };

    @Override
    public MultiTextMessage run(TextMessage message) {
        return new MultiTextMessage(message.chatId, message.to, HELP);
    }
}