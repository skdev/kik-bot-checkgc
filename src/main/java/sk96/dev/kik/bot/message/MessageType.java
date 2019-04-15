package sk96.dev.kik.bot.message;

public enum MessageType {
    WEBHOOK("webhook"),
    TEXT("text"),
    LINK("link"),
    PICTURE("picture"),
    VIDEO("video"),
    START_CHATTING("start-chatting"),
    SCAN("scan-data"),
    STICKER("sticker"),
    IS_TYPING("is-typing"),
    DELIVERY_RECEIPT(""),
    READ_RECEIPT("read-receipt"),
    FRIEND_PICKER("");

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
