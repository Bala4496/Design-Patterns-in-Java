package ua.patterns.solid;

/*

    Liskov substitution principle (LSP)

 */

class Rectangle {
    protected int width, height;

    public Rectangle() {
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea(){
        return width * height;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public boolean isSquare() {
        return width == height;
    }
}

class Square extends Rectangle {
    public Square() {
    }

    public Square(int size) {
        super(size, size);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}

class RectangleFactory {
    public static Rectangle newRectangle(int width, int height) {
        return new Rectangle(width, height);
    }
    public static Rectangle newSquare(int size) {
        return new Rectangle(size, size);
    }
}

public class LSP {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(2, 3);

        userIt(rectangle);

        Rectangle square = new Square();
        square.setWidth(5);
        userIt(square);
    }

    static void userIt(Rectangle r) {
        int width = r.getWidth();
        r.setHeight(10);
        System.out.println("Expected area " + (width * 10) + "\n Real area " + r.getArea());
    }
}
