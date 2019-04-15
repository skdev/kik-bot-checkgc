package sk96.dev.kik.bot.utils.logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TextFileLogger extends Logger {
    private final String file;

    public TextFileLogger(String name) {
        this(name, "./logs.txt");
    }

    public TextFileLogger(String name, String file) {
        super(name);
        this.file = file;
    }

    public void trace(String message) {
        write("[" + timestamp() + "][Trace][" + name + "] " + message);
    }

    public void info(String message) {
        write("[" + timestamp() + "][Info][" + name + "] " + message);
    }

    public void warning(String message) {
        write("[" + timestamp() + "][Warning][" + name + "] " + message);
    }

    public void error(String message) {
        write("[" + timestamp() + "][Error][" + name + "] " + message);
    }

    private void write(String text) {
        try {
            File f = new File(file);
            if (!f.exists() && !f.createNewFile()) {
                System.err.println("Error: Log file does not exist and could not be created.");
                return;
            }
            final String toWrite = text + "\n";
            Files.write(Paths.get(file), toWrite.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println(String.format("Error writing to file [%s]: %s", file, e.getMessage()));
        }
        System.out.println(text);
    }
}