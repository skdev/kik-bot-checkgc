package sk96.dev.kik.bot.message;

import com.jayway.restassured.path.json.JsonPath;

public class WebhookMessageDecoder extends MessageDecoder<WebhookMessage> {

    @Override
    public WebhookMessage decode(String json) {
        final String url = JsonPath.from(json).get("webhook");
        final boolean receiveReadReceipts = JsonPath.from(json).get("features.receiveReadReceipts");
        final boolean receiveIsTyping = JsonPath.from(json).get("features.receiveIsTyping");
        final boolean manuallySendReadReceipts = JsonPath.from(json).get("features.manuallySendReadReceipts");
        final boolean receiveDeliveryReceipts = JsonPath.from(json).get("features.receiveDeliveryReceipts");

        //Kik will never send the API key and bot name hence the null values
        return new WebhookMessage(url, receiveReadReceipts, receiveIsTyping, manuallySendReadReceipts, receiveDeliveryReceipts, false);
    }

}