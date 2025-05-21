package repos;
import service.repository.IUserRepository;
import service.entity.BasicUser;
import service.entity.User;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.lang.AutoCloseable;
import service.entity.Entity;

public class InMemoryRepository implements IUserRepository, AutoCloseable {
    private final List<StoredUser> users = new ArrayList<>();

    private int currentId = 1;
    private final String file_path = "users.txt";

    public InMemoryRepository(){
        loadUsers();
    }

    private void loadUsers(){
        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 6);
                int id = Integer.parseInt(parts[0]);
                String login = parts[1];
                String password = parts[2];
                String name = parts[3];

                User user = new User(id, login, password, name);

                java.util.List<Entity> incomesList = new java.util.ArrayList<>();
                if (parts.length >= 5 && parts[4] != null && !parts[4].isEmpty()) {
                    String incomesData = parts[4];
                    String[] incomeItems = incomesData.split(",");
                    for (String item : incomeItems) {
                        String[] pair = item.split(":");
                        if (pair.length == 2) {
                            String key = pair[0].trim();
                            double value = Double.parseDouble(pair[1].trim());
                            incomesList.add(new Entity(key, value));
                        }
                    }
                }
                boolean hasParents = incomesList.stream().anyMatch(e -> e.getField().equalsIgnoreCase("parents"));
                boolean hasScholar = incomesList.stream().anyMatch(e -> e.getField().equalsIgnoreCase("scholar"));
                if (!hasParents) {
                    incomesList.add(new Entity("parents", 0.0));
                }
                if (!hasScholar) {
                    incomesList.add(new Entity("scholar", 0.0));
                }
                user.setIncome(incomesList);

                if (parts.length == 6 && parts[5] != null && !parts[5].isEmpty()){
                    String outcomeData = parts[5];
                    String[] outcomeItems = outcomeData.split(";");
                    java.util.List<Entity> outcomeList = new java.util.ArrayList<>();
                    for (String item : outcomeItems) {
                        String[] pair = item.split(":");
                        if (pair.length == 2) {
                            String fieldName = pair[0].trim();
                            double value = Double.parseDouble(pair[1].trim());
                            outcomeList.add(new Entity(fieldName, value));
                        }
                    }
                    user.setOutcome(outcomeList);
                }

                users.add(new StoredUser(id, user));
            }
        } catch (IOException e) {
            System.out.println("Ошибка загрузки пользователей: " + e.getMessage());
        }
    }

    private void saveUsersToFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path))) {
            for (StoredUser storedUser : users) {
                User user = storedUser.user;

                if(user.getIncome() == null || user.getIncome().isEmpty()){
                    java.util.List<Entity> defaultIncomes = new java.util.ArrayList<>();
                    defaultIncomes.add(new Entity("parents", 0.0));
                    defaultIncomes.add(new Entity("scholar", 0.0));
                    user.setIncome(defaultIncomes);
                } else {
                    boolean hasParents = user.getIncome().stream()
                            .anyMatch(e -> e.getField().equalsIgnoreCase("parents"));
                    boolean hasScholar = user.getIncome().stream()
                            .anyMatch(e -> e.getField().equalsIgnoreCase("scholar"));
                    if (!hasParents) {
                        user.getIncome().add(new Entity("parents", 0.0));
                    }
                    if (!hasScholar) {
                        user.getIncome().add(new Entity("scholar", 0.0));
                    }
                }

                String incomesData = "";
                if (user.getIncome() != null && !user.getIncome().isEmpty()){
                    incomesData = user.getIncome().stream()
                            .map(entity -> entity.getField() + ":" + entity.getValue())
                            .collect(java.util.stream.Collectors.joining(","));
                }

                String outcomeData = "";
                if (user.getOutcome() != null && !user.getOutcome().isEmpty()){
                    outcomeData = user.getOutcome().stream()
                            .map(entity -> entity.getField() + ":" + entity.getValue())
                            .collect(java.util.stream.Collectors.joining(";"));
                }

                writer.write(user.getId() + ";"
                        + user.getLogin() + ";"
                        + user.getPassword() + ";"
                        + user.getName() + ";"
                        + incomesData + ";"
                        + outcomeData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения пользователей: " + e.getMessage());
        }
    }


    public boolean create(User user){
        users.add(new StoredUser(users.size() + 1, user));
        currentId++;
        saveUsersToFile();
        return true;
    }

    @Override
    public User getById(int id) {
        users.clear();
        loadUsers();
        StoredUser stored = read(id);
        return stored != null ? stored.user : null;
    }

    @Override
    public int addUser(BasicUser user) {
        int newId = getNextUserId();
        User newUser = new User(newId, user.getLogin(), user.getPassword(), user.getName());
        users.add(new StoredUser(newId, newUser));
        saveUsersToFile();
        return newId;
    }

    @Override
    public int getNextUserId() {
        if (users.isEmpty()) {
            return 1;
        } else {
            int maxId = users.stream()
                    .mapToInt(storedUser -> storedUser.id)
                    .max()
                    .orElse(0);
            return maxId + 1;
        }
    }

    public StoredUser read(int id) {
        return users.stream().filter(u -> u.id == id).findFirst().orElse(null);
    }

    public boolean update(User user) {
        for (StoredUser storedUser : users) {
            if (storedUser.id == user.getId()) {
                storedUser.user = user;
                saveUsersToFile();
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        users.removeIf(u -> u.id == id);
        saveUsersToFile();
        return true;
    }

    public void close() {
        saveUsersToFile();
        users.clear();
    }

    public void dispose(){
        saveUsersToFile();
        users.clear();
    }
}
