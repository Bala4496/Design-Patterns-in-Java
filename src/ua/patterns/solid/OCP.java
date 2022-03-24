package ua.patterns.solid;

import java.util.List;
import java.util.stream.Stream;

/*

    Open-closed principle (OCP): one object = one responsible
    + Specification
 */

enum Color {
    RED, GREEN, BLUE
}

enum Size {
    SMALL, MEDIUM, LARGE, HUGE

    }
class Product {
    String name;
    Size size;
    Color color;

    public Product(String name, Size size, Color color) {
        this.name = name;
        this.size = size;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", color=" + color +
                '}';
    }
}

interface Specification<T> {
    boolean isSatisfied(T item);
}

interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> specification);
}

class BetterFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> specification) {
        return items.stream().filter(specification::isSatisfied);
    }
}

class ColorSpecification implements Specification<Product> {
    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.color == color;
    }
}

class SizeSpecification implements Specification<Product> {
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.size == size;
    }
}

class AndSpecification<T> implements Specification<T> {
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

public class OCP {
    public static void main(String[] args) throws Exception{
        Product apple = new Product("Apple", Size.SMALL, Color.GREEN);
        Product tree = new Product("Tree", Size.LARGE, Color.GREEN);
        Product house = new Product("House", Size.LARGE, Color.BLUE);

        List<Product> products = List.of(apple, tree, house);

        BetterFilter bf = new BetterFilter();
        bf.filter(products, new ColorSpecification(Color.GREEN)).forEach(System.out::println);

        bf.filter(products,
                new AndSpecification<>(
                        new ColorSpecification(Color.GREEN),
                        new SizeSpecification(Size.LARGE)
                ))
                .forEach(System.out::println);

    }
}