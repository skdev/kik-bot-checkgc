package sk96.dev.kik.bot.message;

import sk96.dev.kik.bot.command.Command;

import java.util.Map;

public class TextMessageHandler extends MessageHandler<TextMessage> {
    private static final String NEED_HELP = "Need help? Just enter 'help'";

    @Override
    public Message handle(TextMessage message) {
        final String received = message.body;
        if (received.length() <= 0) {
            return new TextMessage(message.chatId, message.to, NEED_HELP);
        }

        String[] parts = received.split(" ");
        String input = parts[0].toLowerCase();
        Map<String, Command> commands = Command.commands;

        if(commands.containsKey(input)) {
            return commands.get(input).run(message);
        }
        if(commands.containsKey(received)) {
            return commands.get(received).run(message);
        }

        return commands.get("lookup").run(message);
    }
}