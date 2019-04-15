package sk96.dev.kik.bot.message;

import sk96.dev.kik.bot.MessageConstants;

public class PictureMessageHandler extends MessageHandler<PictureMessage> {

    @Override
    public Message handle(PictureMessage message) {
        return new TextMessage(message.chatId, message.to, MessageConstants.UNKNOWN_TYPE, message.chatType);
    }
}