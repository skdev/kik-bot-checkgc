package sk96.dev.kik.bot.message;

import java.util.HashMap;
import java.util.Map;

public final class Codec {
    public static final Map<Class<? extends Message>, MessageEncoder<?>> encoders = new HashMap<>();
    public static final Map<MessageType, MessageHandler<?>> handlers = new HashMap<>();
    public static final Map<MessageType, MessageDecoder<?>> decoders = new HashMap<>();
    static {
        encoders.put(DummyMessage.class, new DummyMessageEncoder()); //DummyMessageEncoder
        encoders.put(MultiTextMessage.class, new MultiTextMessageEncoder());
        encoders.put(KeyboardMessage.class, new KeyboardMessageEncoder());
        encoders.put(LinkMessage.class, new LinkMessageEncoder());

        handlers.put(MessageType.TEXT, new TextMessageHandler());
        decoders.put(MessageType.TEXT, new TextMessageDecoder());
        encoders.put(TextMessage.class, new TextMessageEncoder());

        handlers.put(MessageType.START_CHATTING, new StartChattingMessageHandler());
        decoders.put(MessageType.START_CHATTING, new StartChattingMessageDecoder());
        encoders.put(StartChattingMessage.class, new StartChattingMessageEncoder());

        handlers.put(MessageType.PICTURE, new PictureMessageHandler());
        decoders.put(MessageType.PICTURE, new PictureMessageDecoder());
        encoders.put(PictureMessage.class, new PictureMessageEncoder());

        handlers.put(MessageType.IS_TYPING, new IsTypingMessageHandler());
        decoders.put(MessageType.IS_TYPING, new IsTypingMessageDecoder());

        handlers.put(MessageType.LINK, new DummyMessageHandler(MessageType.LINK));
        decoders.put(MessageType.LINK, new DummyMessageDecoder());

        handlers.put(MessageType.VIDEO, new DummyMessageHandler(MessageType.VIDEO));
        decoders.put(MessageType.VIDEO, new DummyMessageDecoder());

        handlers.put(MessageType.SCAN, new DummyMessageHandler(MessageType.SCAN));
        decoders.put(MessageType.SCAN, new DummyMessageDecoder());

        handlers.put(MessageType.STICKER, new DummyMessageHandler(MessageType.STICKER));
        decoders.put(MessageType.STICKER, new DummyMessageDecoder());

        handlers.put(MessageType.DELIVERY_RECEIPT, new DummyMessageHandler(MessageType.DELIVERY_RECEIPT));
        decoders.put(MessageType.DELIVERY_RECEIPT, new DummyMessageDecoder());

        handlers.put(MessageType.READ_RECEIPT, new ReadReceiptMessageHandler());
        decoders.put(MessageType.READ_RECEIPT, new ReadReceiptMessageDecoder());

        handlers.put(MessageType.FRIEND_PICKER, new DummyMessageHandler(MessageType.FRIEND_PICKER));
        decoders.put(MessageType.FRIEND_PICKER, new DummyMessageDecoder());
    }
}