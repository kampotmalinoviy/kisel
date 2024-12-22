package bsu.rfe.java.group10.lab1.Kisel.varA3;

public class Milk extends Food {

    private String fat;
    public Milk(String fat) {
// Вызвать конструктор предка, передав ему имя класса
        super("Milk");
// Инициализировать размер яблока
        this.fat = fat;
    }

    public void consume() {
        System.out.println(this + " finished");
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public boolean equals(Object arg1) {
        if (super.equals(arg1)) {
            if (!(arg1 instanceof Milk)) return false;
            return fat.equals(((Milk)arg1).fat);
        } else
            return false;
    }

    public String toString() {
        return super.toString() + " fat '" + fat + "'";
    }
}