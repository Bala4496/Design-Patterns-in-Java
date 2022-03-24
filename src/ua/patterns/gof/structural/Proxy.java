package ua.patterns.gof.structural;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Proxy {
    @SuppressWarnings("unchecked")
    public static <T> T withLogging(T target, Class<T> ith) {
        return (T) java.lang.reflect.Proxy.newProxyInstance(
                ith.getClassLoader(),
                new Class<?>[] { ith },
                new LoggingHandler(target)
        );
    }

    public static void main(String[] args) {
        Driver driver = new Driver(18);
        Car car = new CarProxy(driver);
        car.drive();

        Creature1 creature = new Creature1();
        creature.setAgility(10);

        Person person = new Person();
        Human logger = withLogging(person, Human.class);
        logger.talk();
        logger.walk();
        logger.talk();
        logger.walk();
        System.out.println(logger);
    }
}

//Protection proxy
interface Drivable {
    void drive();
}

class Car implements Drivable {
    protected Driver driver;

    public Car(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void drive() {
        System.out.println("Car drive");
    }
}

class Driver {
    public int age;

    public Driver(int age) {
        this.age = age;
    }
}

class CarProxy extends Car{

    public CarProxy(Driver driver) {
        super(driver);
    }

    @Override
    public void drive() {
        if (driver.age >= 16)
            super.drive();
        else
            System.out.println("Driver too young!");
    }
}

//Property proxy
class Property<T> {
    private T value;

    public Property(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        //logging
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property<?> property = (Property<?>) o;

        return Objects.equals(value, property.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}

class Creature1 {
    private Property<Integer> agility = new Property<>(10);

    public void setAgility(int value){
        agility.setValue(value);
    }

    public int getAgility() {
        return agility.getValue();
    }
}

//Dynamic proxy for proxy
interface Human {
    void walk();
    void talk();
}

class Person implements Human {

    @Override
    public void walk() {
        System.out.println("I am walking");
    }

    @Override
    public void talk() {
        System.out.println("I am talking");
    }
}

class LoggingHandler implements InvocationHandler {

    private final Object target;
    private Map<String, Integer> calls = new HashMap<>();

    public LoggingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();

        if (name.contains("toString")){
            return calls.toString();
        }

        calls.merge(name, 1, Integer::sum);
        return method.invoke(target, args);
    }
}