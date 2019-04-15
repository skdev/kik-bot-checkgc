package sk96.dev.kik.bot.command.broadcast;

import sk96.dev.kik.bot.command.Command;
import sk96.dev.kik.bot.message.MultiTextMessage;
import sk96.dev.kik.bot.message.TextMessage;

public class BroadcastToCommand extends Command<MultiTextMessage, TextMessage> {

    @Override
    public MultiTextMessage run(TextMessage message) {
        if(!(message.to.equalsIgnoreCase("surajjj_") || message.to.equalsIgnoreCase("ashi_16"))) {
            return new MultiTextMessage(message.chatId, message.to, "Need help? Just enter 'help'");
        }
        String received = message.body.trim();
        if (received.startsWith("to")) {
            received = received.replaceFirst("to", "").trim();
        } else if (received.startsWith("To")) {
            received = received.replaceFirst("To", "").trim();
        }
        String[] parts = received.split(" ");
        String to = parts[0];
        String msg = received.replaceFirst(to, "").trim();
        for(Chat c : Chat.chats) {
            if(c.to.equalsIgnoreCase(to)) {
                if(msg.length() <= 0) {
                    return new MultiTextMessage(message.chatId, message.to, "That message is too short.");
                }
                Broadcast.broadcast(c.chatId, c.to, msg);
                return new MultiTextMessage(message.chatId, message.to, "Sent message to " + to);
            }
        }
        return new MultiTextMessage(message.chatId, message.to, "Couldn't find chatId for " + to);
    }
}