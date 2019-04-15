package sk96.dev.kik.bot.message;

import sk96.dev.kik.bot.MessageConstants;

public class DummyMessageHandler extends MessageHandler<DummyMessage> {

    public DummyMessageHandler(MessageType type) { }

    @Override
    public Message handle(DummyMessage message) {
        return new DummyMessage(message.chatId, message.to, MessageConstants.UNKNOWN_TYPE, message.chatType);
    }
}