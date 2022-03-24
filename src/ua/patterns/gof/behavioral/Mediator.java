package ua.patterns.gof.behavioral;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Observable;

import java.util.*;

public class Mediator {
    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        Person john = new Person("John");
        Person jane = new Person("Jane");

        room.join(john);
        room.join(jane);

        john.say("Hi room!");
        jane.say("Hey John!");

        Person simon = new Person("Simon");
        room.join(simon);
        simon.say("Hi everyone!");
        jane.privateMessage("Simon", "glad you could join us!");



        EventBroker broker = new EventBroker();
        FootballPlayer player = new FootballPlayer(broker, "John");
        FootballCoach coach = new FootballCoach(broker);

        player.score();
        player.score();
        player.score();
    }
}

//Chat room
class Person {
    public String name;
    public ChatRoom room;
    private List<String> chatLog = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public void receive (String sender, String message) {
        String s = sender + ": '" + message + "'";
        System.out.println("[" + name + "'s chat session] " + s);
        chatLog.add(s);
    }

    public void say(String message) {
        room.broadcast(name, message);
    }

    public void privateMessage(String who, String message) {
        room.message(name, who, message);
    }
}

class ChatRoom {
    private List<Person> people = new ArrayList<>();

    public void join(Person person) {
        String joinMsg = person.name + " joins the room";
        broadcast("room", joinMsg);

        person.room = this;
        people.add(person);
    }

    public void broadcast(String source, String message){
        for (Person person: people)
            if(!person.name.equals(source))
                person.receive(source,message);
    }

    public void message(String source, String destination, String message) {
        people.stream()
                .filter(p -> p.name.equals(destination))
                .findFirst()
                .ifPresent(person -> person.receive(source, message));
    }
}

//Reactive Extensions Event Broker
class EventBroker extends Observable<Integer> {
    private List<Observer<? super Integer>> observers = new ArrayList<>();

    @Override
    protected void subscribeActual(Observer<? super Integer> observer) {
        observers.add(observer);
    }

    public void publish(int n) {
        for (Observer<? super Integer> o : observers) {
            o.onNext(n);
        }
    }
}

class FootballPlayer {
    private int goalsScore = 0;
    private EventBroker broker;
    private String name;

    public FootballPlayer(EventBroker broker, String name) {
        this.broker = broker;
        this.name = name;
    }

    public void score() {
        broker.publish(++goalsScore);
    }
}

class FootballCoach {
    public FootballCoach(EventBroker broker) {
        broker.subscribe(i -> {
            System.out.println("Your score is " + i + " goals!");
        });
    }
}