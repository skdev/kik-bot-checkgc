package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;

public class InformationCommand extends Command<MultiTextMessage, TextMessage> {
    private static final String[] INFORMATION = {
            "~Information\\n\\n" +
                    "This bot is designed to see who is active in a group chat real time.\\n\\nYou can do this to see the current members of an on-going conversation, find out who is leaving you on R(read) or to find out which members are inactive.",
            "~Why use @CheckGC?\\n\\n" +
                    "Third-party bots like RageBot are not legal. Using Rage is against Kik's terms and you could get banned for using it. @CheckGC is legal and available through the Kik Bot Shop.\\n\\n"
                    + "Our competitor bot Who's Lurking does a similar job however is not as informative as us. We're just better.\\n\\n"
                    + "You can join our group chat #CheckGC and give us your feedback, speak to the developers and suggest ideas (we also love memes!)."
    };

    @Override
    public MultiTextMessage run(TextMessage message) {
        return new MultiTextMessage(message.chatId, message.to, INFORMATION);
    }
}