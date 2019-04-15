package sk96.dev.kik.bot.message;

import sk96.dev.kik.bot.command.broadcast.Chat;
import sk96.dev.kik.bot.utils.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class TextMessageDecoder extends MessageDecoder<TextMessage> {
    private static final Logger L = Logger.getTextFileLogger("TextMessageDecoder");

    @Override
    public TextMessage decode(String json) {
        final String chatId = getString(json, "chatId");
        final String to = getString(json, "from");
        final String body = getString(json, "body");
        final String chatType = getString(json, "chatType");
        MessageChatType type = MessageChatType.UNKNOWN;
        if (chatType.toLowerCase().trim().equalsIgnoreCase("direct")) {
            type = MessageChatType.DIRECT;
        } else if (chatType.toLowerCase().trim().equalsIgnoreCase("private")) {
            type = MessageChatType.PRIVATE;
        }

        final List<?> participants = getStringArr(json, "messages.participants");
        final List<String> users = new ArrayList<>();
        List<String> test = (List<String>) participants.get(0);
        for (String E : test) {
            users.add(E);
        }
        String[] part = users.toArray(new String[users.size()]);

        new Thread(() -> {
            L.info("Updating chats.txt, this will be done async");
            boolean addNew = true;
            for (Chat c : Chat.chats) {
                if (c.chatId.equalsIgnoreCase(chatId)) {
                    addNew = false;
                } else if (c.to.equalsIgnoreCase(to)) {
                    addNew = false;
                }
            }
            if (addNew) {
                new Chat(chatId, to);
                L.info("Added to chats.txt [chatId=" + chatId + "][to=" + to + "]");
            }
        }).start();

        return new TextMessage(chatId, to, body, type, part);
    }
}