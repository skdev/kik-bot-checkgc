package sk96.dev.kik.bot.utils.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Logger {
    public final String name;

    public Logger(String name) {
        this.name = name;
    }

    public static Logger getTextFileLogger(String name) {
        return new TextFileLogger(name);
    }

    public abstract void trace(String message);

    public abstract void info(String message);

    public abstract void warning(String message);

    public abstract void error(String message);

    public static String timestamp() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }
}