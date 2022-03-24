package ua.patterns.gof.creational;

import com.google.common.collect.Iterables;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Singleton {
    public static void main(String[] args) {
//        BasicSingleton singleton = BasicSingleton.getInstance();
    }
    @Test
    public void dependentPopulationTest() {
        DummyDatabase db = new DummyDatabase();
        ConfigurableRecordFinder rf = new ConfigurableRecordFinder(db);
        assertEquals(4, rf.getTotalPopulation(
                List.of("Alpha", "Gamma")
        ));
    }
}

class BasicSingleton {
    private BasicSingleton() {
        System.out.println("BasicSingleton is initializing");
    }

    private static final BasicSingleton INSTANCE = new BasicSingleton();
    public static BasicSingleton getInstance() {
        return INSTANCE;
    }

    public int value = 0;
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}

class StaticBlockSingleton {
    private StaticBlockSingleton() throws Exception{
        System.out.println("StaticBlockSingleton is initializing");
        File.createTempFile(".", ".");
    }

    private static StaticBlockSingleton instance;

    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            System.err.println("StaticBlockSingleton initializing failed");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}

class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("LazySingleton is initializing");
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}

class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {
        System.out.println("ThreadSafeSingleton is initializing");
    }

//    public static synchronized ThreadSafeSingleton getInstance() {
//        if (instance == null) {
//            instance = new ThreadSafeSingleton();
//        }
//        return instance;
//    }

    //double-checked locking
    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }
}

class InnerStaticSingleton {
    private InnerStaticSingleton() {
        System.out.println("InnerStaticSingleton is initializing");
    }

    private static class Impl {
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
    }

    public InnerStaticSingleton getInstance() {
        return Impl.INSTANCE;
    }
}

enum EnumBasedSingleton {
    INSTANCE;

    EnumBasedSingleton(){
        System.out.println("EnumBasedSingleton is initializing");
        value = 42;
    }

    private int value;

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}

// Monostate
class ChiefExecutiveOfficer {
    private static String name;
    private static int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ChiefExecutiveOfficer.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ChiefExecutiveOfficer.age = age;
    }

    @Override
    public String toString() {
        return "ChiefExecutiveOfficer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

//Multitone
enum Subsystem {
    PRIMARY,
    AUXILIARY,
    FALLBACK
}

class MutlitonPrinter {
    private static int instanceCount = 0;
    private MutlitonPrinter() {
        instanceCount++;
        System.out.println("Created " + instanceCount + "instances at all");
    }

    private static HashMap<Subsystem, MutlitonPrinter> instances = new HashMap<>();

    public static MutlitonPrinter get(Subsystem ss) {
        if(instances.containsKey(ss))
            return instances.get(ss);

        MutlitonPrinter instance = new MutlitonPrinter();
        instances.put(ss, instance);
        return instance;
    }
}

//Testability issues

//class SingletonDatabase {
//    private Dictionary<String, Integer> capitals = new Hashtable<>();
//    private static int instanceCount = 0;
//    public static int getCount() {
//        return instanceCount;
//    }
//
//    private SingletonDatabase() {
//        instanceCount++;
//        System.out.println("Initializing database");
//
//        try {
//            File file = new File(
//                    SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath()
//            );
//            Path fullPath = Paths.get(file.getPath(), "capitals.txt");
//            List<String> lines = Files.readAllLines(fullPath);
//
//            Iterables.partition(lines, 2)
//                    .forEach(kv -> capitals.put(kv.get(0).trim(),
//                            Integer.parseInt(kv.get(1))));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private static final SingletonDatabase INSTANCE = new SingletonDatabase();
//
//    public static SingletonDatabase getInstance() {
//        return INSTANCE;
//    }
//
//    public int getPopulation(String name) {
//        return capitals.get(name);
//    }
//}
//
//class SingletonRecordFinder {
//    public int getTotalPopulation(List<String> names) {
//        int result = 0;
//        for (String name : names)
//            result += SingletonDatabase.getInstance().getPopulation(name);
//        return result;
//    }
//}
//
//class Tests {
//    @Test
//    public void singletonTotalPopulationTest() {
//        SingletonRecordFinder rf = new SingletonRecordFinder();
//        List<String> names = List.of("Kyiv", "Kharkiv");
//        int tp = rf.getTotalPopulation(names);
//        assertEquals(2_000_000, tp);
//    }
//}

//Singleton in Dependency Injection

interface Database {
    int getPopulation(String name);
}

class SingletonDatabase implements Database {
    private Dictionary<String, Integer> capitals = new Hashtable<>();
    private static int instanceCount = 0;
    public static int getCount() {
        return instanceCount;
    }

    private SingletonDatabase() {
        instanceCount++;
        System.out.println("Initializing database");

        try {
            File file = new File(
                    SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath()
            );
            Path fullPath = Paths.get(file.getPath(), "capitals.txt");
            List<String> lines = Files.readAllLines(fullPath);

            Iterables.partition(lines, 2)
                    .forEach(kv -> capitals.put(kv.get(0).trim(),
                            Integer.parseInt(kv.get(1))));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    public static SingletonDatabase getInstance() {
        return INSTANCE;
    }

    public int getPopulation(String name) {
        return capitals.get(name);
    }
}

class DummyDatabase implements Database {

    private Dictionary<String, Integer> data = new Hashtable<>();

    public DummyDatabase () {
        data.put("Alpha", 1);
        data.put("Beta", 2);
        data.put("Gamma", 3);
    }

    @Override
    public int getPopulation(String name) {
        return data.get(name);
    }
}

class ConfigurableRecordFinder {
    private Database database;

    public ConfigurableRecordFinder(Database database) {
        this.database = database;
    }

    public int getTotalPopulation(List<String> names) {
        int result = 0;
        for (String name : names)
            result += database.getPopulation(name);
        return result;
    }
}