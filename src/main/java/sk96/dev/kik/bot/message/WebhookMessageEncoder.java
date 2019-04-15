package sk96.dev.kik.bot.message;


import sk96.dev.kik.bot.MessageConstants;

public class WebhookMessageEncoder extends MessageEncoder<WebhookMessage> {

    @Override
    public String encode(WebhookMessage message) {
        final KeyboardMessage keyboard = new KeyboardMessage("chatId", "to", "hidden", "title", MessageConstants.DISPLAY_COMMANDS);

        StringBuilder json = new StringBuilder("{\"webhook\":\"" + message.url + "\",\"features\":{");
        json.append("\"manuallySendReadReceipts\":").append(message.manuallySendReadReceipts).append(",");
        json.append("\"receiveReadReceipts\":").append(message.receiveReadReceipts).append(",");
        json.append("\"receiveDeliveryReceipts\":").append(message.receiveDeliveryReceipts).append(",");
        json.append("\"receiveIsTyping\":").append(message.receiveIsTyping).append("},");
        json.append("\"staticKeyboard\": {");
        json.append("\"type\": \"" + "suggested" + "\",");
        json.append("\"responses\": [");
        for (int i = 0; i < keyboard.body.length; i++) {
            String body = keyboard.body[i];
            json.append("{");
            json.append("\"type\": \"" + "text" + "\",");
            json.append("\"body\": \"").append(body).append("\"");
            if (i == (keyboard.body.length - 1)) {
                json.append("}");
                json.append("]");
            } else {
                json.append("},");
            }
        }
        json.append("}");
        json.append("}");
        return json.toString();
    }
}