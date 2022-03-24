package ua.patterns.gof.behavioral;

public class Memento {
    public static void main(String[] args) {
        BankAccount1 bankAccount = new BankAccount1(100);
        Memento1 m1 = bankAccount.deposit(50);
        Memento1 m2 = bankAccount.deposit(25);
        System.out.println(bankAccount);

        bankAccount.restore(m1);
        System.out.println(bankAccount);

        bankAccount.restore(m2);
        System.out.println(bankAccount);
    }
}

class BankAccount1 {
    private int balance;

    public BankAccount1(int balance) {
        this.balance = balance;
    }

    public Memento1 deposit(int amount) {
        balance += amount;
        return new Memento1(balance);
    }

    public void restore(Memento1 m) {
        this.balance = m.balance;
    }

    @Override
    public String toString() {
        return "BankAccount1{" +
                "balance=" + balance +
                '}';
    }
}

class Memento1 {
    public int balance;

    public Memento1(int balance) {
        this.balance = balance;
    }
}





















