package ua.patterns.gof.behavioral;

import com.google.common.collect.Lists;

import java.util.*;

public class Command {
    public static void main(String[] args) {
        BankAccount ba = new BankAccount();
        System.out.println(ba);

        List<BankAccountCommand> commands = Arrays.asList(
                new BankAccountCommand(ba, BankAccountCommand.Action.DEPOSIT, 100),
                new BankAccountCommand(ba, BankAccountCommand.Action.WITHDRAW, 1000)
        );

        for (Command1 c : commands) {
            c.call();
            System.out.println(ba);
        }

//        for (Command1 c : Lists.reverse(commands)) { //Smth wrong with Guava
//            c.undo();
//            System.out.println(ba);
//        }

    }
}

//Command. Undo command
class BankAccount {
    private int balance;
    private int overdraftLimit = -500;

    public void deposit(int amount){
        balance += amount;
        System.out.println("Deposited " + amount + ", balance is " + balance);
    }

    public boolean withdraw(int amount){
        if(balance - amount >= overdraftLimit){
            balance -= amount;
            System.out.println("Withdrew " + amount + ", balance is " + balance);
            return true;
        } else {
            System.out.println("Overdraft is limited");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }
}

interface Command1 {
    void call();
    void undo();
}

class BankAccountCommand implements Command1{
    private BankAccount account;
    private boolean succeeded;

    @Override
    public void call() {
        switch (action) {
            case DEPOSIT :
                succeeded = true;
                account.deposit(amount);
                break;
            case WITHDRAW :
                succeeded = account.withdraw(amount);
                break;
        }
    }

    @Override
    public void undo() {
        if (!succeeded) return;
        switch (action) {
            case DEPOSIT -> account.withdraw(amount);
            case WITHDRAW -> account.deposit(amount);
        }
    }

    public enum Action {
        DEPOSIT, WITHDRAW
    }

    private Action action;
    private int amount;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }
}



