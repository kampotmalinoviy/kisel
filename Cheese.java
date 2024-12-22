package bsu.rfe.java.group10.lab1.Kisel.varA3;

public class Cheese extends Food{

    public static int Cheese_count =0;
    public Cheese() {
        super("Cheese");
    }

    public void consume() {
        System.out.println(this + " eaten");
    }

}
