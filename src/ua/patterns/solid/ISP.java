package ua.patterns.solid;

/*

    Interface segregation principle (ICP)

 */

class Document {

}

interface MultiFunctionMachine extends Printer, Scanner{

}

class MultiFunctionPrinter implements MultiFunctionMachine{
    @Override
    public void print(Document document) {

    }

    @Override
    public void scan(Document document) {

    }
}

class OldFashionPrinter implements Printer{
    @Override
    public void print(Document document) {

    }
}

interface Printer {
    void print(Document document);
}

interface Scanner {
    void scan(Document document);
}


public class ISP {
    public static void main(String[] args) {

    }
}
