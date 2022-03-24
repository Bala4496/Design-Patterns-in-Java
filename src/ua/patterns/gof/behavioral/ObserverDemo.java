package ua.patterns.gof.behavioral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ObserverDemo implements Observer<ObsPerson>{
    public static void main(String[] args) {
        new ObserverDemo();
    }

    public ObserverDemo() {
        ObsPerson person = new ObsPerson();
        person.subscribe(this);
        for (int i = 20; i < 24; i++) {
            person.setAge(i);
        }

        Person3 person1 = new Person3();
        Event1<PropertyChangedEventArgs1>.Subscription sub = person1.propertyChanged.addHandler(x -> {
            System.out.println("Person's " + x.propertyName + " hasChanged");
        });
        person1.setAge(17);
        person1.setAge(18);
        sub.close();
        person1.setAge(19);
    }

    @Override
    public void handle(PropertyChangedEventArgs<ObsPerson> args) {
        System.out.println("Person's " + args.propertyName + " has changed to " + args.newValue);
    }
}


//Observer + Observable
class PropertyChangedEventArgs<T> {
    public T source;
    public String propertyName;
    public Object newValue;

    public PropertyChangedEventArgs(T source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }
}

interface Observer<T> {
    void handle(PropertyChangedEventArgs<T> args);
}

class Observable<T> {
    private List<Observer<T>> observers = new ArrayList<>();

    public void subscribe(Observer<T> observer) {
        observers.add(observer);
    }

    protected void propertyChanged(T source, String propertyName, Object newValue) {
        for (Observer<T> observer: observers) {
            observer.handle(new PropertyChangedEventArgs<>(source, propertyName, newValue));
        }
    }
}

class ObsPerson extends Observable<ObsPerson>{
    public int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(this.age == age) return;
        this.age = age;
        propertyChanged(this, "age", age);
    }
}

//An Event Class
class Event1<TArgs> {
    private int count = 0;
    private Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();

    public Subscription addHandler(Consumer<TArgs> handler) {
        int i = count;
        handlers.put(count++, handler);
        return new Subscription(this, i);
    }

    public void fire(TArgs args) {
        for (Consumer<TArgs> handler : handlers.values()) {
            handler.accept(args);
        }
    }

    public class Subscription implements AutoCloseable{
        private Event1<TArgs> event;
        private int id;

        public Subscription(Event1<TArgs> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() {
            event.handlers.remove(id);
        }
    }
}

class PropertyChangedEventArgs1 {
    public Object source;
    public String propertyName;

    public PropertyChangedEventArgs1(Object source, String propertyName) {
        this.source = source;
        this.propertyName = propertyName;
    }
}

class Person3 {
    public Event1<PropertyChangedEventArgs1> propertyChanged = new Event1<>();
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (this.age == age) return;
        this.age = age;
        propertyChanged.fire(new PropertyChangedEventArgs1(this, "age"));
    }

    public boolean getCanVote() {
        return age >= 18;
    }
}