package ua.patterns.gof.behavioral;

public class VisitorDemo {
    public static void main(String[] args) {
        AdditionExpression expression = new AdditionExpression(
            new DoubleExpression(1),
            new AdditionExpression(
                new DoubleExpression(2),
                new DoubleExpression(3)
            )
        );
        StringBuilder sb = new StringBuilder();
//        expression.print(sb); //Intrusive VisitorDemo

//        ExpressionPrinter.print(expression, sb);    //Reflective VisitorDemo

        ExpressionPrinter ep = new ExpressionPrinter(); //Classic VisitorDemo, Double Dispatch, AcyclicVisitor
        ep.visit(expression);
//        ExpressionCalculator ec = new ExpressionCalculator();
//        ec.visit(expression);
//        System.out.println(ep + " = " + ec.result);


    }
}

//Intrusive VisitorDemo

//abstract class Expression {
//    public abstract void print(StringBuilder sb);
//}
//
//class DoubleExpression extends Expression{
//    private double value;
//
//    public DoubleExpression(double value) {
//        this.value = value;
//    }
//
//    @Override
//    public void print(StringBuilder sb) {
//        sb.append(value);
//    }
//}
//
//class AdditionExpression extends Expression{
//    private Expression left, right;
//
//    public AdditionExpression(Expression left, Expression right) {
//        this.left = left;
//        this.right = right;
//    }
//
//    @Override
//    public void print(StringBuilder sb) {
//        sb.append("(");
//        left.print(sb);
//        sb.append("+");
//        right.print(sb);
//        sb.append(")");
//    }
//}

//Reflective VisitorDemo

//abstract class Expression {
//}
//
//class ExpressionPrinter{
//    public static void print(Expression e, StringBuilder sb) {
//        if (e.getClass() == DoubleExpression.class){
//            sb.append(((DoubleExpression)e).value);
//        } else if (e.getClass() == AdditionExpression.class) {
//            AdditionExpression ae = (AdditionExpression) e;
//            sb.append("(");
//            print(ae.left, sb);
//            sb.append("+");
//            print(ae.right, sb);
//            sb.append(")");
//        }
//    }
//}
//
//class DoubleExpression extends Expression{
//    public double value;
//
//    public DoubleExpression(double value) {
//        this.value = value;
//    }
//}
//
//class AdditionExpression extends Expression{
//    public Expression left, right;
//
//    public AdditionExpression(Expression left, Expression right) {
//        this.left = left;
//        this.right = right;
//    }
//
//}

//Classic VisitorDemo

//interface ExpressionVisitor {
//    void visit(DoubleExpression de);
//    void visit(AdditionExpression ae);
//}
//
//abstract class Expression {
//    public abstract void accept(ExpressionVisitor visitor);
//}
//
//class ExpressionPrinter implements ExpressionVisitor{
//    private StringBuilder sb = new StringBuilder();
//
//    @Override
//    public void visit(DoubleExpression de) {
//        sb.append(de.value);
//    }
//
//    @Override
//    public void visit(AdditionExpression ae) {
//        sb.append("(");
//        ae.left.accept(this);
//        sb.append("+");
//        ae.right.accept(this);
//        sb.append(")");
//    }
//
//    @Override
//    public String toString() {
//        return sb.toString();
//    }
//}
//
//class ExpressionCalculator implements ExpressionVisitor {
//    public double result;
//
//    @Override
//    public void visit(DoubleExpression de) {
//        result = de.value;
//    }
//
//    @Override
//    public void visit(AdditionExpression ae) {
//        ae.left.accept(this);
//        double a = result;
//        ae.right.accept(this);
//        double b = result;
//        result = a+b;
//    }
//}
//
//class DoubleExpression extends Expression{
//    public double value;
//
//    public DoubleExpression(double value) {
//        this.value = value;
//    }
//
//    @Override
//    public void accept(ExpressionVisitor visitor) {
//        visitor.visit(this);
//    }
//}
//
//class AdditionExpression extends Expression{
//    public Expression left, right;
//
//    public AdditionExpression(Expression left, Expression right) {
//        this.left = left;
//        this.right = right;
//    }
//
//    @Override
//    public void accept(ExpressionVisitor visitor) {
//        visitor.visit(this);
//    }
//}

//Double Dispatch

//interface Visitor { }
//
//interface ExpressionVisitor extends Visitor{
//    void visit(Expression e);
//}
//
//interface DoubleExpressionVisitor extends Visitor{
//    void visit(DoubleExpression de);
//}
//
//interface AdditionExpressionVisitor extends Visitor{
//    void visit(AdditionExpression ae);
//}
//
//abstract class Expression {
//    public void accept(Visitor visitor) {
//        if (visitor instanceof ExpressionVisitor)
//            ((ExpressionVisitor) visitor).visit(this);
//    }
//}
//
//class ExpressionPrinter implements DoubleExpressionVisitor, AdditionExpressionVisitor{
//    private StringBuilder sb = new StringBuilder();
//
//    @Override
//    public void visit(DoubleExpression de) {
//        sb.append(de.value);
//    }
//
//    @Override
//    public void visit(AdditionExpression ae) {
//        sb.append("(");
//        ae.left.accept(this);
//        sb.append("+");
//        ae.right.accept(this);
//        sb.append(")");
//    }
//
//    @Override
//    public String toString() {
//        return sb.toString();
//    }
//}
//
//class ExpressionCalculator implements DoubleExpressionVisitor, AdditionExpressionVisitor {
//    public double result;
//
//    @Override
//    public void visit(DoubleExpression de) {
//        result = de.value;
//    }
//
//    @Override
//    public void visit(AdditionExpression ae) {
//        ae.left.accept(this);
//        double a = result;
//        ae.right.accept(this);
//        double b = result;
//        result = a+b;
//    }
//}
//
//class DoubleExpression extends Expression{
//    public double value;
//
//    public DoubleExpression(double value) {
//        this.value = value;
//    }
//
//    @Override
//    public void accept(Visitor visitor) {
//        if (visitor instanceof DoubleExpressionVisitor)
//            ((DoubleExpressionVisitor) visitor).visit(this);
//    }
//}
//
//class AdditionExpression extends Expression{
//    public Expression left, right;
//
//    public AdditionExpression(Expression left, Expression right) {
//        this.left = left;
//        this.right = right;
//    }
//
//    @Override
//    public void accept(Visitor visitor) {
//        if (visitor instanceof AdditionExpressionVisitor)
//            ((AdditionExpressionVisitor) visitor).visit(this);
//    }
//}

//AcyclicVisitor

interface Visitor {}

interface ExpressionVisitor extends Visitor {
    void visit(Expression obj);
}

interface DoubleExpressionVisitor extends Visitor
{
    void visit(DoubleExpression obj);
}

interface AdditionExpressionVisitor extends Visitor
{
    void visit(AdditionExpression obj);
}

abstract class Expression
{
    // optional
    public void accept(Visitor visitor)
    {
        if (visitor instanceof ExpressionVisitor) {
            ((ExpressionVisitor) visitor).visit(this);
        }
    }
}

class DoubleExpression extends Expression
{
    public double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
        if (visitor instanceof ExpressionVisitor) {
            ((ExpressionVisitor) visitor).visit(this);
        }
    }
}

class AdditionExpression extends Expression
{
    public Expression left, right;

    public AdditionExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(Visitor visitor) {
        if (visitor instanceof AdditionExpressionVisitor) {
            ((AdditionExpressionVisitor) visitor).visit(this);
        }
    }
}

class ExpressionPrinter implements
    DoubleExpressionVisitor,
    AdditionExpressionVisitor
{
    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpression obj) {
        sb.append(obj.value);
    }

    @Override
    public void visit(AdditionExpression obj) {
        sb.append('(');
        obj.left.accept(this);
        sb.append('+');
        obj.right.accept(this);
        sb.append(')');
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
