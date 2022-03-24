package ua.patterns.gof.structural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Flyweight {
    public static void main(String[] args) {
        User user1 = new User("John Smith");
        User user2 = new User("Jane Smith");

        User2 user21 = new User2("John Smith");
        User2 user22 = new User2("Jane Smith");

        String hello = "Hello world";
        System.out.println(hello);
        String word = hello.split(" ")[1];
        System.out.println(word);
        int start = hello.indexOf(word);
        System.out.printf("Start: %d\n", start);
        int end = start + word.length() - 1;
        System.out.printf("End: %d\n", end);
        System.out.println("----------------------------");

        FormattedText formattedText = new FormattedText("Hello the World!");
        formattedText.capitalize(6,8);
        System.out.println(formattedText);

        BetterFormattedText bfp = new BetterFormattedText("Bye the World!");
        bfp.getRange(4,6).capitalize = true;
        System.out.println(bfp);
    }
}

//Repeating names
class User {
    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }
}

class User2 {
    private static List<String> strings = new ArrayList<>();
    private int[] names;

    public User2(String fullName) {
        Function<String, Integer> getOrAdd = (String s) -> {
            int i = strings.indexOf(s);
            if(i != -1) return i;
            else {
                strings.add(s);
                return strings.size() - 1;
            }
        };

        this.names = Arrays.stream(fullName.split(" "))
                .mapToInt(s -> getOrAdd.apply(s))
                .toArray();
    }
}

//Text formatting
class FormattedText {
    private String plainText;
    private boolean[] capitalize;

    public FormattedText(String plainText) {
        this.plainText = plainText;
        capitalize = new boolean[plainText.length()];
    }

    public void capitalize(int start, int end) {
        for (int i = start; i <= end; i++) {
            capitalize[i] = true;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            sb.append(
                    capitalize[i] ? Character.toUpperCase(c) : c
            );
        }
        return sb.toString();
    }
}

class BetterFormattedText {

    private String plainText;
    private List<TextRange> formatting = new ArrayList<>();

    public BetterFormattedText(String plainText) {
        this.plainText = plainText;
    }

    public TextRange getRange(int start, int end) {
        TextRange textRange = new TextRange(start, end);
        formatting.add(textRange);
        return textRange;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            char c = plainText.charAt(i);
            for (TextRange range: formatting)
                if (range.covers(i) && range.capitalize)
                    c = Character.toUpperCase(c);
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public class TextRange {
        public int start, end;
        public boolean capitalize, bolt, italic;

        public TextRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean covers(int position) {
            return position >= start && position <= end;
        }
    }
}