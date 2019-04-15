package sk96.dev.kik.bot;

public class Configuration {
    public final String apiURL;
    public final String botName;
    public final String apiKey;
    public final String host;
    public final int port;
    public final boolean receiveReadReceipts;
    public final boolean receiveIsTyping;
    public final boolean manuallySendReadReceipts;
    public final boolean receiveDeliveryReceipts;

    public Configuration(String apiURL, String botName, String apiKey, String host, int port, boolean receiveReadReceipts, boolean receiveIsTyping, boolean manuallySendReadReceipts, boolean receiveDeliveryReceipts) {
        this.apiURL = apiURL;
        this.botName = botName;
        this.apiKey = apiKey;
        this.host = host;
        this.port = port;
        this.receiveReadReceipts = receiveReadReceipts;
        this.receiveIsTyping = receiveIsTyping;
        this.manuallySendReadReceipts = manuallySendReadReceipts;
        this.receiveDeliveryReceipts = receiveDeliveryReceipts;
    }
}