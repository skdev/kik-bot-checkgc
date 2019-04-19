package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;

public class HelpCommand extends Command<MultiTextMessage, TextMessage> {
    private static final String[] HELP = {
            "~Help\\n\\nTo start using the bot in a group chat simply say '@CheckGC Check' or '@CheckGC Just Active'. \\n\\nIf CheckGC has been used in the group before, you will see a list of active and inactive users otherwise the bot starts to monitor so you will only see yourself as active.",
            "How does it work?\\n\\n"
                    + "When @CheckGC sends a message, we receive a notification that somebody has read the message. If they have read it, they are active or lurking.\\n\\n"
                    + "To reset the timer, say '@CheckGC Reset' and monitoring will start again.",
            "~Please note\\n\\nIf a new member joins a group after CheckGC is monitoring, we will not know if they are active because they would not have seen our message. To include new members you must say '@CheckGC' again to include them."
    };

    @Override
    public MultiTextMessage run(TextMessage message) {
        return new MultiTextMessage(message.chatId, message.to, HELP);
    }
}