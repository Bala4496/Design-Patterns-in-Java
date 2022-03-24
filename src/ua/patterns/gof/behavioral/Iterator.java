package ua.patterns.gof.behavioral;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Iterator {
    public static void main(String[] args) {
        Node<java.lang.Integer> root = new Node<>(1, new Node<>(2), new Node<>(3));

        InOrderIterator<java.lang.Integer> it = new InOrderIterator<>(root);

        while (it.hasNext()) {
            System.out.print("" + it.next());
        }
        System.out.println();

        BinaryTree<java.lang.Integer> tree = new BinaryTree<>(root);
        for (int n: tree)
            System.out.print("" + n);
        System.out.println();


        Creature creature = new Creature();
        creature.setStrength(10);
        creature.setAgility(12);
        creature.setIntelligence(8);
        System.out.println("Max: " + creature.max());
        System.out.println("Total: " + creature.sum());
        System.out.println("Average: " + creature.average());
    }
}

//Tree traversal
class BinaryTree<T> implements Iterable<T>{

    private Node<T> root;

    public BinaryTree(Node<T> root) {
        this.root = root;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new InOrderIterator<>(root);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for(T item: this)
            action.accept(item);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}

class Node<T>
{
    public T value;
    public Node<T> left, right, parent;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> left, Node<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;

        left.parent = right.parent = this;
    }
}

class InOrderIterator<T> implements java.util.Iterator<T> {
private Node<T> current, root;
private boolean yieldedStart;

    public InOrderIterator(Node<T> root) {
        this.root = this.current = root;

        while (current.left != null) {
            current = current.left;
        }
    }

    private boolean hasRightmostParent(Node<T> node) {
        if (node.parent == null) return false;
        return (node == node.parent.left)
                || (hasRightmostParent(node.parent));
    }

    @Override
    public boolean hasNext() {
        return current.left != null
                || current.right != null
                || hasRightmostParent(current);
    }

    @Override
    public T next() {
        if (!yieldedStart){
            yieldedStart = true;
            return current.value;
        }

        if (current.right != null) {
            current = current.right;
            while (current.left != null)
                current = current.left;
            return current.value;
        }
        else
        {
            Node<T> p = current.parent;
            while (p != null && current == p.right) {
                current = p;
                p = p.parent;
            }
            current = p;
            return current.value;
        }
    }
}

//Array-Backed properties
class SimpleCreature {
    private int strength, agility, intelligence;

    public int max() {
        return Math.max(strength, Math.max(agility, intelligence));
    }

    public int sum() {
        return strength + agility + intelligence;
    }

    public double average() {
        return sum() / 3.0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
}

class Creature implements Iterable<java.lang.Integer>{
    private int[] stats = new int[3];
    private final int str = 0;
    private final int agi = 1;
    private final int intel = 2;

    public int getStrength() {
        return stats[str];
    }

    public void setStrength(int value) {
        stats[str] = value;
    }

    public int getAgility() {
        return stats[agi];
    }

    public void setAgility(int value) {
        stats[agi] = value;
    }

    public int getIntelligence() {
        return stats[intel];
    }

    public void setIntelligence(int value) {
        stats[intel] = value;
    }

    public int sum() {
        return IntStream.of(stats).sum();
    }

    public int max() {
        return IntStream.of(stats).max().getAsInt();
    }

    public double average() {
        return IntStream.of(stats).average().getAsDouble();
    }

    @Override
    public java.util.Iterator<java.lang.Integer> iterator() {
        return IntStream.of(stats).iterator();
    }

    @Override
    public void forEach(Consumer<? super java.lang.Integer> action) {
        for (int x: stats)
            action.accept(x);
    }

    @Override
    public Spliterator<java.lang.Integer> spliterator() {
        return Iterable.super.spliterator();
    }
}





