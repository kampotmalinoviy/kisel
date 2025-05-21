package service.entity;
import java.util.ArrayList;
import java.util.List;

public class User extends BasicUser {
    public int id;
    private List<Entity> income;
    private List<Entity> expenses;

    public User(int id, String login, String password, String name) {
        super(login, password, name);
        this.id = id;
        this.income =  new ArrayList<>();
        this.expenses = new ArrayList<>();
    }

    public int getId() {return id;}
    public List<Entity> getIncome() { return income; }
    public List<Entity> getOutcome() { return expenses; }
    public void setIncome(List<Entity> income) { this.income = income;}

    public void setOutcome(List<Entity> expenses) {
        this.expenses = expenses;
    }
}
