package ua.patterns.gof.structural;

import jdk.internal.vm.annotation.IntrinsicCandidate;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Decorator {
    public static void main(String[] args) {
        MagicString string = new MagicString("hello");
        System.out.println(string.getNumberOfVowels());

        ColoredShape<Square> blueSquare = new ColoredShape<>(
                () -> new Square(20),
                "blue"
        );
    }
}

//Final class decorator
class MagicString {
    private String string;

    public MagicString(String string) {
        this.string = string;
    }

    public long getNumberOfVowels() {
        return string.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> "aeiou".contains(c.toString()))
                .count();
    }

    @Override
    public String toString() {
        return string;
    }

    public int length() {
        return string.length();
    }

    public boolean isEmpty() {
        return string.isEmpty();
    }

    public char charAt(int index) {
        return string.charAt(index);
    }

    public int codePointAt(int index) {
        return string.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return string.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return string.codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return string.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        string.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    @Deprecated(since = "1.1")
    public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
        string.getBytes(srcBegin, srcEnd, dst, dstBegin);
    }

    public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
        return string.getBytes(charsetName);
    }

    public byte[] getBytes(Charset charset) {
        return string.getBytes(charset);
    }

    public byte[] getBytes() {
        return string.getBytes();
    }

    public boolean contentEquals(StringBuffer sb) {
        return string.contentEquals(sb);
    }

    public boolean contentEquals(CharSequence cs) {
        return string.contentEquals(cs);
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return string.equalsIgnoreCase(anotherString);
    }

    public int compareTo(String anotherString) {
        return string.compareTo(anotherString);
    }

    public int compareToIgnoreCase(String str) {
        return string.compareToIgnoreCase(str);
    }

    public boolean regionMatches(int toffset, String other, int ooffset, int len) {
        return string.regionMatches(toffset, other, ooffset, len);
    }

    public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
        return string.regionMatches(ignoreCase, toffset, other, ooffset, len);
    }

    public boolean startsWith(String prefix, int toffset) {
        return string.startsWith(prefix, toffset);
    }

    public boolean startsWith(String prefix) {
        return string.startsWith(prefix);
    }

    public boolean endsWith(String suffix) {
        return string.endsWith(suffix);
    }

    public int indexOf(int ch) {
        return string.indexOf(ch);
    }

    public int indexOf(int ch, int fromIndex) {
        return string.indexOf(ch, fromIndex);
    }

    public int lastIndexOf(int ch) {
        return string.lastIndexOf(ch);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return string.lastIndexOf(ch, fromIndex);
    }

    public int indexOf(String str) {
        return string.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return string.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return string.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return string.lastIndexOf(str, fromIndex);
    }

    public String substring(int beginIndex) {
        return string.substring(beginIndex);
    }

    public String substring(int beginIndex, int endIndex) {
        return string.substring(beginIndex, endIndex);
    }

    public CharSequence subSequence(int beginIndex, int endIndex) {
        return string.subSequence(beginIndex, endIndex);
    }

    public String concat(String str) {
        return string.concat(str);
    }

    public String replace(char oldChar, char newChar) {
        return string.replace(oldChar, newChar);
    }

    public boolean matches(String regex) {
        return string.matches(regex);
    }

    public boolean contains(CharSequence s) {
        return string.contains(s);
    }

    public String replaceFirst(String regex, String replacement) {
        return string.replaceFirst(regex, replacement);
    }

    public String replaceAll(String regex, String replacement) {
        return string.replaceAll(regex, replacement);
    }

    public String replace(CharSequence target, CharSequence replacement) {
        return string.replace(target, replacement);
    }

    public String[] split(String regex, int limit) {
        return string.split(regex, limit);
    }

    public String[] split(String regex) {
        return string.split(regex);
    }

    public String toLowerCase(Locale locale) {
        return string.toLowerCase(locale);
    }

    public String toLowerCase() {
        return string.toLowerCase();
    }

    public String toUpperCase(Locale locale) {
        return string.toUpperCase(locale);
    }

    public String toUpperCase() {
        return string.toUpperCase();
    }

    public String trim() {
        return string.trim();
    }

    public String strip() {
        return string.strip();
    }

    public String stripLeading() {
        return string.stripLeading();
    }

    public String stripTrailing() {
        return string.stripTrailing();
    }

    public boolean isBlank() {
        return string.isBlank();
    }

    public Stream<String> lines() {
        return string.lines();
    }

    public String indent(int n) {
        return string.indent(n);
    }

    public String stripIndent() {
        return string.stripIndent();
    }

    public String translateEscapes() {
        return string.translateEscapes();
    }

    public <R> R transform(Function<? super String, ? extends R> f) {
        return string.transform(f);
    }

    public IntStream chars() {
        return string.chars();
    }

    public IntStream codePoints() {
        return string.codePoints();
    }

    public char[] toCharArray() {
        return string.toCharArray();
    }

    public String formatted(Object... args) {
        return string.formatted(args);
    }

    public String intern() {
        return string.intern();
    }

    public String repeat(int count) {
        return string.repeat(count);
    }

    public Optional<String> describeConstable() {
        return string.describeConstable();
    }

    public String resolveConstantDesc(MethodHandles.Lookup lookup) {
        return string.resolveConstantDesc(lookup);
    }
}

//Dynamic Decorator Composition
interface Shape {
    String info();
}

class Circle implements Shape {
    private float radius;

    public Circle() {
    }

    public Circle(float radius) {
        this.radius = radius;
    }

    void resize(float factor) {
        radius *= factor;
    }

    @Override
    public String info() {
        return null;
    }
}

class Square implements Shape {
    private float side;

    public Square() {
    }

    public Square(float side) {
        this.side = side;
    }

    void resize(float factor) {
        side *= factor;
    }

    @Override
    public String info() {
        return null;
    }
}

//class ColoredShape implements Shape {
//    private Shape shape;
//    private String color;
//
//    public ColoredShape(Shape shape, String color) {
//        this.shape = shape;
//        this.color = color;
//    }
//
//    @Override
//    public String info() {
//        return shape.info() + " has " + color + " color";
//    }
//}
//
//class TransparentShape implements Shape {
//    private Shape shape;
//    private int transparency;
//
//    public TransparentShape(Shape shape, int transparency) {
//        this.shape = shape;
//        this.transparency = transparency;
//    }
//
//    @Override
//    public String info() {
//        return shape.info() + " has " + transparency + " transparency";
//    }
//}

//Dynamic Decorator Composition

class ColoredShape<T extends Shape> implements Shape {
    private Shape shape;
    private String color;

    public ColoredShape(Supplier<? extends T> ctor, String color) {
        this.color = color;
        this.shape = ctor.get();
    }

    @Override
    public String info() {
        return shape.info() + " has " + color + " color";
    }
}

class TransparentShape<T extends Shape> implements Shape {
    private Shape shape;
    private int transparency;

    public TransparentShape(Supplier<? extends T> ctor, int transparency) {
        this.transparency = transparency;
        this.shape = ctor.get();
    }

    @Override
    public String info() {
        return shape.info() + " has " + transparency + " transparency";
    }
}

//Adapter-decorator
class MyStringBuilder {
    private StringBuilder sb;

    public MyStringBuilder() {
        sb = new StringBuilder();
    }

    public MyStringBuilder(String str) {
        sb = new StringBuilder(str);
    }

    public int compareTo(StringBuilder another) {
        return sb.compareTo(another);
    }

    public MyStringBuilder append(Object obj) {
        sb.append(obj);
        return this;
    }

    public StringBuilder append(String str) {
        return sb.append(str);
    }

    public StringBuilder append(StringBuffer sb) {
        return this.sb.append(sb);
    }

    public StringBuilder append(CharSequence s) {
        return sb.append(s);
    }

    public StringBuilder append(CharSequence s, int start, int end) {
        return sb.append(s, start, end);
    }

    public StringBuilder append(char[] str) {
        return sb.append(str);
    }

    public StringBuilder append(char[] str, int offset, int len) {
        return sb.append(str, offset, len);
    }

    public StringBuilder append(boolean b) {
        return sb.append(b);
    }

    public StringBuilder append(char c) {
        return sb.append(c);
    }

    public StringBuilder append(int i) {
        return sb.append(i);
    }

    public StringBuilder append(long lng) {
        return sb.append(lng);
    }

    public StringBuilder append(float f) {
        return sb.append(f);
    }

    public StringBuilder append(double d) {
        return sb.append(d);
    }

    public StringBuilder appendCodePoint(int codePoint) {
        return sb.appendCodePoint(codePoint);
    }

    public StringBuilder delete(int start, int end) {
        return sb.delete(start, end);
    }

    public StringBuilder deleteCharAt(int index) {
        return sb.deleteCharAt(index);
    }

    public StringBuilder replace(int start, int end, String str) {
        return sb.replace(start, end, str);
    }

    public StringBuilder insert(int index, char[] str, int offset, int len) {
        return sb.insert(index, str, offset, len);
    }

    public StringBuilder insert(int offset, Object obj) {
        return sb.insert(offset, obj);
    }

    public StringBuilder insert(int offset, String str) {
        return sb.insert(offset, str);
    }

    public StringBuilder insert(int offset, char[] str) {
        return sb.insert(offset, str);
    }

    public StringBuilder insert(int dstOffset, CharSequence s) {
        return sb.insert(dstOffset, s);
    }

    public StringBuilder insert(int dstOffset, CharSequence s, int start, int end) {
        return sb.insert(dstOffset, s, start, end);
    }

    public StringBuilder insert(int offset, boolean b) {
        return sb.insert(offset, b);
    }

    public StringBuilder insert(int offset, char c) {
        return sb.insert(offset, c);
    }

    public StringBuilder insert(int offset, int i) {
        return sb.insert(offset, i);
    }

    public StringBuilder insert(int offset, long l) {
        return sb.insert(offset, l);
    }

    public StringBuilder insert(int offset, float f) {
        return sb.insert(offset, f);
    }

    public StringBuilder insert(int offset, double d) {
        return sb.insert(offset, d);
    }

    public int indexOf(String str) {
        return sb.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return sb.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return sb.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return sb.lastIndexOf(str, fromIndex);
    }

    public StringBuilder reverse() {
        return sb.reverse();
    }

    public int length() {
        return sb.length();
    }

    public int capacity() {
        return sb.capacity();
    }

    public void ensureCapacity(int minimumCapacity) {
        sb.ensureCapacity(minimumCapacity);
    }

    public void trimToSize() {
        sb.trimToSize();
    }

    public void setLength(int newLength) {
        sb.setLength(newLength);
    }

    public char charAt(int index) {
        return sb.charAt(index);
    }

    public int codePointAt(int index) {
        return sb.codePointAt(index);
    }

    public int codePointBefore(int index) {
        return sb.codePointBefore(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return sb.codePointCount(beginIndex, endIndex);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return sb.offsetByCodePoints(index, codePointOffset);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        sb.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public void setCharAt(int index, char ch) {
        sb.setCharAt(index, ch);
    }

    public String substring(int start) {
        return sb.substring(start);
    }

    public CharSequence subSequence(int start, int end) {
        return sb.subSequence(start, end);
    }

    public String substring(int start, int end) {
        return sb.substring(start, end);
    }

    public IntStream chars() {
        return sb.chars();
    }

    public IntStream codePoints() {
        return sb.codePoints();
    }

    public boolean isEmpty() {
        return sb.isEmpty();
    }
}
