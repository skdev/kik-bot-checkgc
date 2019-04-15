package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;

public class InformationCommand extends Command<MultiTextMessage, TextMessage> {
    private static final String[] INFORMATION = {
            "Coming soon."
    };

    @Override
    public MultiTextMessage run(TextMessage message) {
        return new MultiTextMessage(message.chatId, message.to, INFORMATION);
    }
}