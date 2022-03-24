package ua.patterns.solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/*

    Single responsibility principle (RSP): one object = one responsible

 */

class Journal {
    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    public void addEntry(String text) {
        entries.add("" + (++count) + ": " + text);
    }

    public void removeEntity(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }
}

class Persistence {
    public void saveToFile(Journal journal,
                           String filename,
                           boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(filename).exists()) {
            try (PrintStream out = new PrintStream(filename)) {
                out.println(journal);
            }
        }
    }
}

public class SRP {
    public static void main(String[] args) throws Exception{
        Journal journal = new Journal();
        journal.addEntry("I waked up");
        journal.addEntry("I ate");
        journal.addEntry("I worked");
        journal.addEntry("I went to bed");

        System.out.println(journal);

        Persistence persistence = new Persistence();
        String filename = ".\\msg.txt";
        persistence.saveToFile(journal, filename, true);

        Runtime.getRuntime().exec("notepad.exe " + filename);
    }
}