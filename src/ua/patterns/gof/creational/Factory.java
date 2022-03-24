package ua.patterns.gof.creational;

import org.javatuples.Pair;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Factory {
    public static void main(String[] args) throws Exception {
//    Point cartesianPoint = Point.newCartesianPoint(2,3);
//    Point polarPoint = Point.newPolarPoint(2,3);
//
//    Point point = Point.PointFactory.newCartesianPoint(2,3);

        HotDrinkMachine machine = new HotDrinkMachine();
        HotDrink drink = machine.makeDrink();
        drink.consume();
    }
}

// Factory method

//class Point {
//    private double x, y;
//
//    private Point(double x, double y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public static Point newCartesianPoint(double x, double y) {
//        return new Point(x, y);
//    }
//
//    public static Point newPolarPoint(double x, double y) {
//        return new Point(x * Math.cos(y), x * Math.sin(y));
//    }
//}

//Factory

//class Point {
//    private double x, y;
//
//    private Point(double x, double y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    public static class PointFactory {
//        public static Point newCartesianPoint(double x, double y) {
//            return new Point(x, y);
//        }
//
//        public static Point newPolarPoint(double x, double y) {
//            return new Point(x * Math.cos(y), x * Math.sin(y));
//        }
//    }
//}

//Abstract factory

interface HotDrink {
    void consume();
}

class Tea implements HotDrink {
    @Override
    public void consume() {
        System.out.println("This tea is delicious");
    }
}

class Coffee implements HotDrink {
    @Override
    public void consume() {
        System.out.println("This coffee is delicious");
    }
}

interface HotDrinkFactory {
    HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory {
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Put tea bug, pour " + amount + "ml.");
        return new Tea();
    }
}

class CoffeeFactory implements HotDrinkFactory {
    @Override
    public HotDrink prepare(int amount) {
        System.out.println("Grind some beans, pour " + amount + "ml.");
        return new Coffee();
    }
}

class HotDrinkMachine {
    private List<Pair<String, HotDrinkFactory>> namedFactories = new ArrayList<>();

    public HotDrinkMachine() throws Exception {
        Set<Class<? extends HotDrinkFactory>> types = new Reflections("").getSubTypesOf(HotDrinkFactory.class);
        for (Class<? extends HotDrinkFactory> type: types){
            namedFactories.add(new Pair<>(
                    type.getSimpleName().replace("Factory", ""),
                    type.getDeclaredConstructor().newInstance()
            ));
        }
    }

    public HotDrink makeDrink() throws Exception{
        System.out.println("Available drinks:");
        for (int i = 0; i < namedFactories.size(); ++i){
            Pair<String, HotDrinkFactory> item = namedFactories.get(i);
            System.out.println("" + i + ": " + item.getValue0());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String s;
            int i, amount;
            if((s = reader.readLine()) != null
                    && (i = Integer.parseInt(s)) >= 0
                    && i < namedFactories.size()) {
                System.out.println("Specify amount:");
                s = reader.readLine();
                if(s != null && (amount = Integer.parseInt(s)) > 0){
                    return namedFactories.get(i).getValue1().prepare(amount);
                }
            }
            System.out.println("Incorrect input, try again.");
        }
    }
}


