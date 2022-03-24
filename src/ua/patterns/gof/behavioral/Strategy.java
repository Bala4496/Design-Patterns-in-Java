package ua.patterns.gof.behavioral;

import java.util.List;
import java.util.function.Supplier;

public class Strategy {
    public static void main(String[] args) {
        //Dynamic

//        TextProcessor dtp = new TextProcessor(OutputFormat.MARKDOWN);
//        dtp.appendList(List.of("strength", "agility", "intelligence"));
//        System.out.println(dtp);
//
//        dtp.clear();
//        dtp.setOutputFormat(OutputFormat.HTML);
//        dtp.appendList(List.of("inheritance", "encapsulation", "polymorphism"));
//        System.out.println(dtp);

        //Static
        TextProcessor<MarkdownListStrategy> stp1 = new TextProcessor<>(MarkdownListStrategy::new);
        stp1.appendList(List.of("strength", "agility", "intelligence"));
        System.out.println(stp1);

        TextProcessor<HtmlListStrategy> stp2 = new TextProcessor<>(HtmlListStrategy::new);
        stp2.appendList(List.of("inheritance", "encapsulation", "polymorphism"));
        System.out.println(stp2);
    }
}

//Dynamic strategy
enum OutputFormat {
    MARKDOWN,
    HTML
}

interface ListStrategy {
    default void start(StringBuilder sb) {};
    void addListItem(StringBuilder sb, String item);
    default void end(StringBuilder sb) {};
}

class  MarkdownListStrategy implements ListStrategy {
    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append(" * ").append(item).append(System.lineSeparator());
    }
}

class HtmlListStrategy implements ListStrategy {

    @Override
    public void start(StringBuilder sb) {
        sb.append("<ul>").append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder sb, String item) {
        sb.append("  <li>")
                .append(item)
                .append("</li>")
                .append(System.lineSeparator());
    }

    @Override
    public void end(StringBuilder sb) {
        sb.append("</ul>").append(System.lineSeparator());
    }
}

//class TextProcessor {
//    private StringBuilder sb = new StringBuilder();
//    private ListStrategy listStrategy;
//
//    public TextProcessor(OutputFormat format) {
//        setOutputFormat(format);
//    }
//
//    public void setOutputFormat(OutputFormat format) {
//        switch (format){
//            case HTML -> listStrategy = new HtmlListStrategy();
//            case MARKDOWN -> listStrategy = new MarkdownListStrategy();
//        }
//    }
//
//    public void appendList(List<String> items) {
//        listStrategy.start(sb);
//        for (String item: items)
//            listStrategy.addListItem(sb, item);
//        listStrategy.end(sb);
//    }
//
//    public void clear()
//    {
//        sb.setLength(0);
//    }
//
//    @Override
//    public String toString() {
//        return sb.toString();
//    }
//}

//Static strategy
class TextProcessor<LS extends ListStrategy> {
    private StringBuilder sb = new StringBuilder();
    private LS listStrategy;

    public TextProcessor(Supplier<? extends LS> ctor) {
        listStrategy = ctor.get();
    }

    public void appendList(List<String> items) {
        listStrategy.start(sb);
        for (String item: items)
            listStrategy.addListItem(sb, item);
        listStrategy.end(sb);
    }

    public void clear()
    {
        sb.setLength(0);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}





