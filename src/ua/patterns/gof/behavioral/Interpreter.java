package ua.patterns.gof.behavioral;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ua.patterns.gof.behavioral.BinaryOperation.parse;

public class Interpreter {
    //Lexing
    static List<Token> lex(String input) {
        ArrayList<Token> result = new ArrayList<>();

        for (int i = 0; i < input.length(); ++i) {
            switch (input.charAt(i)){
                case '+':
                    result.add(new Token(Token.Type.PLUS, "+"));
                    break;
                case '-':
                    result.add(new Token(Token.Type.MINUS, "-"));
                    break;
                case '(':
                    result.add(new Token(Token.Type.LPAREN, "("));
                    break;
                case ')':
                    result.add(new Token(Token.Type.RPAREN, ")"));
                    break;
                default:
                    StringBuilder stringBuilder = new StringBuilder("" + input.charAt(i));
                    for (int j = i + 1; j < input.length(); ++j) {
                        if (Character.isDigit(input.charAt(j))) {
                            stringBuilder.append(input.charAt(j));
                            ++i;
                        } else {
                            result.add(new Token(
                                    Token.Type.INTEGER, stringBuilder.toString()
                            ));
                            break;
                        }
                    }
                    break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String input = "(13+4)-(12+1)";
        List<Token> tokens = lex(input);
        System.out.println(tokens.stream()
                .map(Token::toString)
                .collect(Collectors.joining("\t")));

        Element parsed = parse(tokens);
        System.out.println(input + " = " + parsed.eval());
    }
}

//Lexing
class Token {
    public enum Type {
        INTEGER,
        PLUS,
        MINUS,
        LPAREN,
        RPAREN
    }

    public Type type;
    public String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "'" + text + "'";
    }
}

//Parser
interface Element {
    int eval();
}

class Integer1 implements Element {
    private int value;

    public Integer1(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOperation implements Element {
    public enum Type {
        ADDITION,
        SUBTRACTION
    }
    public Type type;
    public Element left, right;

    @Override
    public int eval() {
        switch (type) {
            case ADDITION:
                return left.eval() + right.eval();
            case SUBTRACTION:
                return left.eval() - right.eval();
            default:
                return 0;
        }
    }

    static Element parse(List<Token> tokens) {
        BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false;

        for (int i = 0; i < tokens.size(); ++i) {
            Token token = tokens.get(i);

            switch (token.type) {
                case INTEGER -> {
                    Integer1 integer = new Integer1(java.lang.Integer.parseInt(token.text));
                    if(!haveLHS) {
                        result.left = integer;
                        haveLHS = true;
                    } else {
                        result.right = integer;
                    }
                }
                case PLUS -> {
                    result.type = Type.ADDITION;
                }
                case MINUS -> {
                    result.type = Type.SUBTRACTION;
                }
                case LPAREN -> {
                    int j = 0;
                    for (; j < tokens.size(); ++j)
                        if (tokens.get(j).type == Token.Type.RPAREN)
                            break;
                    List<Token> subexpression = tokens.stream()
                            .skip(i + 1)
                            .limit(j - i - 1)
                            .toList();
                    Element element = parse(subexpression);
                    if (!haveLHS) {
                        result.left = element;
                        haveLHS = true;
                    } else {
                        result.right = element;
                    }
                    i = j;
                }
            }
        }
        return result;
    }
}








