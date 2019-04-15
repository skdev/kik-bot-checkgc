package sk96.dev.kik.bot.message;

public class WebhookMessage extends Message {
    public final String url;
    public final boolean receiveReadReceipts;
    public final boolean receiveIsTyping;
    public final boolean manuallySendReadReceipts;
    public final boolean receiveDeliveryReceipts;
    public final boolean staticKeyboard;

    public WebhookMessage(String url, boolean receiveReadReceipts, boolean receiveIsTyping, boolean manuallySendReadReceipts, boolean receiveDeliveryReceipts, boolean staticKeyboard) {
        super(MessageType.WEBHOOK, null, null, null); //'chatId', 'to' and  'chatType' is null because a webhook message is not sent to a person
        this.url = url;
        this.receiveReadReceipts = receiveReadReceipts;
        this.receiveIsTyping = receiveIsTyping;
        this.manuallySendReadReceipts = manuallySendReadReceipts;
        this.receiveDeliveryReceipts = receiveDeliveryReceipts;
        this.staticKeyboard = staticKeyboard;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (obj instanceof WebhookMessage) {
            final WebhookMessage other = (WebhookMessage) obj;
            return other.url.equals(url)
                    && other.receiveReadReceipts == receiveReadReceipts
                    && other.receiveIsTyping == receiveIsTyping
                    && other.manuallySendReadReceipts == manuallySendReadReceipts
                    && other.receiveDeliveryReceipts == receiveDeliveryReceipts;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[url=" + url
                + "][receiveDeliveryReceipts=" + receiveDeliveryReceipts
                + "][receiveIsTyping=" + receiveIsTyping
                + "][receiveReadReceipts=" + receiveReadReceipts + "]";
    }
}