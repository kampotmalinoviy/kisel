package service.entity;

public class Entity {
    private String field;
    private double value;

    public Entity(String field, double value) {
        this.field = field;
        this.value = value;
    }

    public double setValue(double value) {
        this.value = value;
        return value;
    }

    public String getField() {
        return field;
    }
    public double getValue() {
        return value;
    }
}
