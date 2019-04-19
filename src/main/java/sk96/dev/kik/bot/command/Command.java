package sk96.dev.kik.bot.command;

import sk96.dev.kik.bot.command.broadcast.BroadcastCommand;
import sk96.dev.kik.bot.command.broadcast.BroadcastToCommand;
import sk96.dev.kik.bot.message.Message;

import java.util.HashMap;
import java.util.Map;

public abstract class Command<T extends Message, V extends Message> {
    public static final Map<String, Command> commands = new HashMap<>();
    static {
        commands.put("check", new CheckActiveCommand());
        commands.put("just active", new JustActiveCommand());
        commands.put("Just Active", new JustActiveCommand());
        commands.put("Just Inactive", new JustInactiveCommand());
        commands.put("just inactive", new JustInactiveCommand());
        commands.put("reset", new ResetCommand());
        commands.put("help", new HelpCommand());

        final InformationCommand info = new InformationCommand();
        commands.put("info", info);
        commands.put("information", info);
        commands.put("broadcast", new BroadcastCommand());
        commands.put("to", new BroadcastToCommand());
    }

    /**
     * Handles a request made by the user.
     * T message that is being sent to the user.
     * V message that was received
     */
    public abstract T run(V message);
}