package sk96.dev.kik.bot.message;

public class IsTypingMessageHandler extends MessageHandler<IsTypingMessage> {

    @Override
    public Message handle(IsTypingMessage message) {
        return new KeyboardMessage(message.chatId, message.to, "true", "", "");
    }
}