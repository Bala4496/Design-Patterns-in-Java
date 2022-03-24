package ua.patterns.gof.behavioral;

import java.lang.reflect.Proxy;

public class NullObject {
    //Dynamic Null Object
    @SuppressWarnings("unchecked")
    public static <T> T noOp(Class<T> itf) {
        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[]{itf},
                (proxy, method, args) -> {
                    if(method.getReturnType().equals(Void.TYPE))
                        return null;
                    else
                        return method.getReturnType().getConstructor().newInstance();
                }
        );
    }

    public static void main(String[] args) {
        Log log = noOp(Log.class);
        BankAccount2 account = new BankAccount2(log);

        account.deposit(100);
    }
}

interface Log {
    void info(String msg);
    void warn(String msg);
}
//Null Object
final class NullLog implements Log {

    @Override
    public void info(String msg) {

    }

    @Override
    public void warn(String msg) {

    }
}

class ConsoleLog implements Log{

    @Override
    public void info(String msg) {
        System.out.println("INFO: " + msg);
    }

    @Override
    public void warn(String msg) {
        System.out.println("WARNING: " + msg);
    }
}

class BankAccount2 {
    private Log log;
    public int balance;
    public BankAccount2(Log log) {
        this.log = log;
    }

    public void deposit(int amount) {
        balance += amount;

        log.info("Deposited " + amount);
    }
}
