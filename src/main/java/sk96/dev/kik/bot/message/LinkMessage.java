package sk96.dev.kik.bot.message;

import java.util.Map;

public class LinkMessage extends Message {
    public final String url;
    public final String title;
    public final String text;
    public final boolean noForward;
    public final String picUrl;
    public final Map<String, String> attributions;
    public final Map<String, String> kikJsData;

    public LinkMessage(String chatId, String to, String url, String title, String text, boolean noForward, String picUrl, Map<String, String> attributions, Map<String, String> kikJsData, MessageChatType type) {
        super(MessageType.LINK, chatId, to, type);
        this.url = url;
        this.title = title;
        this.text = text;
        this.noForward = noForward;
        this.picUrl = picUrl;
        this.attributions = attributions;
        this.kikJsData = kikJsData;
    }
}