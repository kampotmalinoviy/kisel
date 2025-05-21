package service.model;

import repos.InMemoryRepository;
import service.entity.User;
import service.entity.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityModel {
    private final Map<Integer, Double> userExpenses = new HashMap<>();
    private final Map<Integer, Double> userIncomes = new HashMap<>();
    private final Map<Integer, List<Entity>> userFields = new HashMap<>();
    private InMemoryRepository repository = new InMemoryRepository();


    public void addExpense(User user, String type, double amount) {
        userExpenses.put(user.getId(), userExpenses.getOrDefault(user.getId(), 0.0) + amount);

        List<Entity> outcomeList = user.getOutcome();
        boolean found = false;
        for (Entity e : outcomeList) {
            if (e.getField().equalsIgnoreCase(type.trim())) {
                e.setValue(e.getValue() + amount);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Не найдена сфера с названием: " + type);
        }
    }

    public void addIncome(User user, String source, double amount) {
        userIncomes.put(user.getId(), userIncomes.getOrDefault(user.getId(), 0.0) + amount);

        List<Entity> incomeList = user.getIncome();
        if (incomeList == null) {
            incomeList = new ArrayList<>();
            user.setIncome(incomeList);
        }

        boolean found = false;
        for (Entity e : incomeList) {
            if (e.getField().equalsIgnoreCase(source.trim())) {
                if (amount != 0) {
                    e.setValue(e.getValue() + amount);
                }
                found = true;
                break;
            }
        }
        if (!found && amount != 0) {
            incomeList.add(new Entity(source.trim(), amount));
        }
        user.setIncome(incomeList);
        repository.update(user);
    }

    public double getBalance(User user) {
        return getIncome(user) - getExpense(user);
    }

    public double getExpense(User user) {
        if (user == null || user.getOutcome() == null) {
            return 0.0;
        }
        return user.getOutcome().stream()
                .mapToDouble(Entity::getValue)
                .sum();
    }

    public double getIncome(User user) {
        if (user == null || user.getIncome() == null) {
            return 0.0;
        }
        return user.getIncome().stream()
                .mapToDouble(Entity::getValue)
                .sum();
    }

    public boolean addField(User user, String fieldName) {
        if (user == null) {
            System.out.println("Ошибка: пользователь равен null");
            return false;
        }
        if (fieldName == null || fieldName.trim().isEmpty()) {
            System.out.println("Ошибка: название поля пустое");
            return false;
        }
        List<Entity> fields;
        if (userFields.containsKey(user.getId())) {
            fields = userFields.get(user.getId());
        } else {

            fields = user.getOutcome();
            if (fields == null || fields.isEmpty()) {
                System.out.println("Outcome пуст");
                fields = new ArrayList<>();
            }
            userFields.put(user.getId(), fields);
        }
        for (Entity e : fields) {
            if (e.getField().equalsIgnoreCase(fieldName.trim())) {
                System.out.println("Ошибка: сфера с названием '" + fieldName.trim() + "' уже существует");
                return false;
            }
        }

        Entity newField = new Entity(fieldName.trim(), 0.0);
        fields.add(newField);
        userFields.put(user.getId(), fields);

        user.setOutcome(fields);

        return true;
    }

    public Map<String, Object> getStatistics(User user) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("Общий доход", getIncome(user));
        stats.put("Общие расходы", getExpense(user));
        stats.put("Баланс", getBalance(user));
        return stats;
    }
}
