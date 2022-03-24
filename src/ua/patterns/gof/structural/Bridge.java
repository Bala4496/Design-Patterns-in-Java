package ua.patterns.gof.structural;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

public class Bridge {
    public static void main(String[] args) throws ClassNotFoundException {
//        Google Giuce
//        Injector injector = Guice.createInjector(new ShapeModule());
//        Circle instance = injector.getInstance(Circle.class);
//        instance.radius = 3;
//        instance.draw();
//        instance.resize(2);
//        instance.draw();

        RasterRenderer rasterRenderer = new RasterRenderer();
        VectorRenderer vectorRenderer = new VectorRenderer();
        Circle1 circle = new Circle1(vectorRenderer, 5);
        circle.draw();
        circle.resize(2);
        circle.draw();
        Rectangle rectangle = new Rectangle(rasterRenderer, 5);
        rectangle.draw();
        rectangle.resize(2);
        rectangle.draw();
    }
}

interface Renderer {
    void renderCircle(float radius);
    void renderRectangle(float size);
}

class VectorRenderer implements Renderer {

    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing a circle of radius " + radius);
    }

    @Override
    public void renderRectangle(float size) {
        System.out.println("Drawing a rectangle of size " + size);
    }
}

class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing pixels for a circle of radius " + radius);
    }

    @Override
    public void renderRectangle(float size) {
        System.out.println("Drawing pixels for a rectangle of size " + size);
    }
}

abstract class Shape1 {
    protected Renderer renderer;

    public Shape1(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void draw();
    public abstract void resize(float factor);
}

class Circle1 extends Shape1 {
    public float radius;

    @Inject
    public Circle1(Renderer renderer) {
        super(renderer);
    }

    public Circle1(Renderer renderer, float radius) {
        super(renderer);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(float factor) {
        radius *= factor;
    }
}

class Rectangle extends Shape1 {
    public float size;

    @Inject
    public Rectangle(Renderer renderer) {
        super(renderer);
    }

    public Rectangle(Renderer renderer, float size) {
        super(renderer);
        this.size = size;
    }

    @Override
    public void draw() {
        renderer.renderRectangle(size);
    }

    @Override
    public void resize(float factor) {
        size *= factor;
    }
}

//Google Guice
class ShapeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Renderer.class).to(VectorRenderer.class);
    }
}