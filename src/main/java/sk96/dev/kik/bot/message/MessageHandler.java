package sk96.dev.kik.bot.message;

public abstract class MessageHandler<T extends Message> {
    public abstract Message handle(T message);
}