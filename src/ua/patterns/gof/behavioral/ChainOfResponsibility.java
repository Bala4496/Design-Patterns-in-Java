package ua.patterns.gof.behavioral;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ChainOfResponsibility {
    public static void main(String[] args) {
        Creature2 goblin = new Creature2("Goblin", 2, 2);
        System.out.println(goblin);

        CreatureModifier1 root = new CreatureModifier1(goblin);

        root.add(new NoBonusesModifier(goblin));

        System.out.println("Goblin's attack increased");
        root.add(new DoubleAttackModifier1(goblin));
        System.out.println("Goblin's defence increased");
        root.add(new IncreaseDefenceModifier1(goblin));

        root.handle();
        System.out.println(goblin);

        Game game = new Game();
        Creature3 goblin1 = new Creature3(game, "Goblin", 2, 2);
        System.out.println(goblin1);

        IncreaseDefenceModifier icm = new IncreaseDefenceModifier(game, goblin1);
        DoubleAttackModifier dam = new DoubleAttackModifier(game, goblin1);
        try (dam){
            System.out.println(goblin1);
        }

        System.out.println(goblin1);

        if(dam instanceof CreatureModifier) {
            System.out.println(1);
        }
    }
}

//Chain method
class Creature2 {
    public String name;
    public int attack, defence;

    public Creature2(String name, int attack, int defence) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
    }

    @Override
    public String toString() {
        return "Creature2{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defence=" + defence +
                '}';
    }
}

class CreatureModifier1 {
    protected Creature2 creature;
    protected CreatureModifier1 next;

    public CreatureModifier1(Creature2 creature) {
        this.creature = creature;
    }

    public void add(CreatureModifier1 cm) {
        if (next != null){
            next.add(cm);
        }
        else next = cm;
    }

    public void handle(){
        if (next != null) next.handle();
    }
}

class DoubleAttackModifier1 extends CreatureModifier1 {

    public DoubleAttackModifier1(Creature2 creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("Doubling " + creature.name + "'s attack");
        creature.attack *= 2;
        super.handle();
    }
}

class IncreaseDefenceModifier1 extends CreatureModifier1 {

    public IncreaseDefenceModifier1(Creature2 creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("Increasing " + creature.name + "'s defence");
        creature.defence += 3;
        super.handle();
    }
}

class NoBonusesModifier extends CreatureModifier1 {

    public NoBonusesModifier(Creature2 creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("No bonuses");
    }
}

//Command Query Separation
class Event<Args> {
    private int index = 0;
    private Map<java.lang.Integer, Consumer<Args>> handles = new HashMap<java.lang.Integer, Consumer<Args>>();

    public int subscribe(Consumer<Args> handler) {
        int i = index;
        handles.put(index++, handler);
        return i;
    }

    public void unsubscribe(int key) {
        handles.remove(key);
    }

    public void fire(Args args) {
        for (Consumer<Args> handler: handles.values()) {
            handler.accept(args);
        }
    }
}

class Query {
    public String creatureName;
    enum Argument {
        ATTACK, DEFENCE
    }
    public Argument argument;
    public int result;

    public Query(String creatureName, Argument argument, int result) {
        this.creatureName = creatureName;
        this.argument = argument;
        this.result = result;
    }
}

class Game {
    public Event<Query> queries = new Event<>();
}

class Creature3 {
    private Game game;
    public String name;
    public int baseAttack, baseDefence;

    public Creature3(Game game, String name, int baseAttack, int baseDefence) {
        this.game = game;
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefence = baseDefence;
    }

    public int getAttack() {
        Query query = new Query(name, Query.Argument.ATTACK, baseAttack);
        game.queries.fire(query);
        return query.result;
    }

    public int getDefence() {
        Query query = new Query(name, Query.Argument.DEFENCE, baseDefence);
        game.queries.fire(query);
        return query.result;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", attack=" + getAttack() +
                ", defence=" + getDefence() +
                '}';
    }
}

class CreatureModifier {
    protected Game game;
    protected Creature3 creature;

    public CreatureModifier(Game game, Creature3 creature) {
        this.game = game;
        this.creature = creature;
    }
}

class DoubleAttackModifier extends CreatureModifier implements AutoCloseable{
    private final int token;

    public DoubleAttackModifier(Game game, Creature3 creature) {
        super(game, creature);

        token = game.queries.subscribe(q -> {
            if(q.creatureName.equals(creature.name) && q.argument == Query.Argument.ATTACK) {
                q.result *= 2;
            }
        });
    }

    @Override
    public void close() {
        game.queries.unsubscribe(token);
    }
}

class IncreaseDefenceModifier extends CreatureModifier implements AutoCloseable{
    private final int token;

    public IncreaseDefenceModifier(Game game, Creature3 creature) {
        super(game, creature);

        token = game.queries.subscribe(q -> {
            if(q.creatureName.equals(creature.name) && q.argument == Query.Argument.DEFENCE) {
                q.result *= 2;
            }
        });
    }

    @Override
    public void close() {
        game.queries.unsubscribe(token);
    }
}







