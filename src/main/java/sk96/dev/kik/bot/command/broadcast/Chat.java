package sk96.dev.kik.bot.command.broadcast;

import sk96.dev.kik.bot.utils.logging.Logger;
import sk96.dev.kik.bot.utils.logging.TextFileLogger;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private static final Logger L = new TextFileLogger("Chat", "chat.txt");
    public static List<Chat> chats = new ArrayList<>();
    public final String chatId;
    public final String to;

    public Chat(String chatId, String to, boolean append) {
        this.chatId = chatId;
        this.to = to;
        chats.add(this);
        if(append) {
            L.info("[to=" + to + "," + "chatId=" + chatId + "]");
        }
    }

    public Chat(String chatId, String to) {
        this(chatId, to, true);
    }
}
