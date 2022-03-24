package ua.patterns.gof.structural;

import java.util.ArrayList;
import java.util.List;

public class Facade {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(20, 30);
        ViewPort vp = new ViewPort(buffer, 20,  30, 0, 0);
        Console console = new Console(20, 30);
        console.addViewport(vp);
        console.render();

        //Facade
        Console console1 = Console.newConsole(30, 20);
        console1.render();
    }
}

class Buffer {
    private char[] chars;
    private int lineWidth;

    public Buffer(int lineWidth, int lineHeight) {
        this.lineWidth = lineWidth;
        this.chars = new char[lineHeight*lineWidth];
    }

    public char charAt(int x, int y) {
        return chars[y * lineWidth + x];
    }
}

class ViewPort {

    private final Buffer buffer;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    public ViewPort(Buffer buffer, int width, int height,
                    int offsetX, int offsetY) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public char charAt(int x, int y) {
        return buffer.charAt(x + offsetX, y + offsetY);
    }
}

class Console {
    private List<ViewPort> viewPorts = new ArrayList<>();
    int width, height;

    public Console(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addViewport(ViewPort viewPort) {
        viewPorts.add(viewPort);
    }

    //Facade
    public static Console newConsole(int width, int height) {
        Buffer buffer = new Buffer(width, height);
        ViewPort vp = new ViewPort(buffer, width,  height, 0, 0);
        Console console = new Console(width, height);
        console.addViewport(vp);
        return console;
    }

    public void render() {
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < width; ++x) {
                for (ViewPort vp : viewPorts)
                    System.out.print(vp.charAt(x, y));
            }
            System.out.println();
        }
    }
}




