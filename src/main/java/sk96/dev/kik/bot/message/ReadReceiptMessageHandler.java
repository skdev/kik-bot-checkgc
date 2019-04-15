package sk96.dev.kik.bot.message;

import sk96.dev.kik.bot.command.persistence.GroupUtils;
import sk96.dev.kik.bot.utils.logging.Logger;

public class ReadReceiptMessageHandler extends MessageHandler<ReadReceiptMessage> {
    private static final Logger L = Logger.getTextFileLogger("ReadReceiptMessageHandler");

    @Override
    public Message handle(ReadReceiptMessage message) {
        final String chatId = message.chatId;
        final String from = message.to;
        final String timestamp = message.timestamp;

        if(!GroupUtils.groupExists(chatId)) {
            L.info("Group not found: " + chatId);
            return null;
        }

        //Check if member is already cached
        if(GroupUtils.memberExists(chatId, from)) {
            return null;
        }

        GroupUtils.addNewActiveMember(chatId, from);
        L.info("Added new member to group [chatId=" + chatId + "][member=" + from + "]");
        return null;
    }
}