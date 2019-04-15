package sk96.dev.kik.bot.command.broadcast;

import sk96.dev.kik.bot.command.Command;
import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;

public class BroadcastCommand extends Command<MultiTextMessage, TextMessage> {

    @Override
    public MultiTextMessage run(TextMessage message) {
        if(!(message.to.equalsIgnoreCase("surajjj_") || message.to.equalsIgnoreCase("ashi_16"))) {
            return new MultiTextMessage(message.chatId, message.to, "Need help? Just enter 'help'");
        }

        String received = message.body.trim();

        if (received.startsWith("broadcast")) {
            received = received.replaceFirst("broadcast", "").trim();
        } else if (received.startsWith("Broadcast")) {
            received = received.replaceFirst("Broadcast", "").trim();
        }

        if (received.length() <= 0) {
            return new MultiTextMessage(message.chatId, message.to, "That message is too short.");
        }

        Broadcast.broadcast(received);

        return new MultiTextMessage(message.chatId, message.to, "Sent broadcast to " + Chat.chats.size() + " people!");
    }
}