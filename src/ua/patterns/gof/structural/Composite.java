package ua.patterns.gof.structural;

import java.util.*;
import java.util.function.Consumer;

//Simple Composite
//public class Composite {
//    public static void main(String[] args) {
//        GraphicObject drawing = new GraphicObject();
//        drawing.setName("Drawing");
//        drawing.children.add(new Square("Red"));
//        drawing.children.add(new Circle("Yellow"));
//
//        GraphicObject group = new GraphicObject();
//        group.children.add(new Circle("Blue"));
//        group.children.add(new Square("Blue"));
//        drawing.children.add(group);
//
//        System.out.println(drawing);
//    }
//}
//
//class GraphicObject {
//    protected String name = "Group";
//
//    public GraphicObject() {
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String color;
//    public List<GraphicObject> children = new ArrayList<>();
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        print(sb, 0);
//        return sb.toString();
//    }
//
//    private void print(StringBuilder stringBuilder, int depth) {
//        stringBuilder.append(String.join("", Collections.nCopies(depth, "*")))
//                .append(depth > 0 ? " " : "")
//                .append((color == null || color.isEmpty()) ? "" : color + " ")
//                .append(getName())
//                .append(System.lineSeparator());
//        for (GraphicObject child : children)
//            child.print(stringBuilder,  depth+1);
//    }
//}
//
//class Circle extends GraphicObject {
//    public Circle(String color) {
//        name = "Circle";
//        this.color = color;
//    }
//}
//
//class Square extends GraphicObject {
//    public Square(String color) {
//        name = "Square";
//        this.color = color;
//    }
//}

//Neural Composite
public class Composite {
    public static void main(String[] args) {
        Neuron neuron = new Neuron();
        Neuron neuron2 = new Neuron();

        NeuronLayer layer = new NeuronLayer();
        NeuronLayer layer2 = new NeuronLayer();

        neuron.connectTo(neuron2);
        neuron.connectTo(layer);
        layer.connectTo(neuron);
        layer.connectTo(neuron2);
    }
}

interface SomeNeurons extends Iterable<Neuron> {
    default void connectTo(SomeNeurons someNeurons) {
        if (this == someNeurons) return;

        for (Neuron from : this){
            for (Neuron to : someNeurons) {
                from.out.add(to);
                to.in.add(from);
            }
        }
    }
}

class Neuron implements SomeNeurons{
    public ArrayList<Neuron> in, out;

    @Override
    public Iterator<Neuron> iterator() {
        return Collections.singleton(this).iterator();
    }

    @Override
    public void forEach(Consumer<? super Neuron> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<Neuron> spliterator() {
        return Collections.singleton(this).spliterator();
    }

//    public void connectTo(Neuron other) {
//        out.add(other);
//        other.in.add(this);
//    }
}

class NeuronLayer extends ArrayList<Neuron> implements SomeNeurons{

}