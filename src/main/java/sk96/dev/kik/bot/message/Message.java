package sk96.dev.kik.bot.message;

/**
 * Messages are what we send and receive over the network.
 * <p>
 * The required fields for any message is:
 * - type : The type of message (@see MessageType)
 * - chatId : Where the message originated from
 * - to - The name of the username of the person we are interacting with
 * <p>
 * This class is used for both inbound and outbound messages.
 */
public abstract class Message {
    public final MessageType type;
    public final MessageChatType chatType;
    public final String chatId;
    public final String to;

    public Message(MessageType type, String chatId, String to, MessageChatType chatType) {
        this.type = type;
        this.chatId = chatId;
        this.to = to;
        this.chatType = chatType;
    }
}

