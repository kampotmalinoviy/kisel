package service.model;

import service.entity.User;
import service.entity.BasicUser;
import service.repository.IUserRepository;

public class UserModel {
    private final IUserRepository repository;

    public UserModel(IUserRepository repository){
        this.repository = repository;
    }

    public int addUser(BasicUser userdata) {
        if (userdata.getLogin().isEmpty() || userdata.getPassword().isEmpty() || userdata.getName().isEmpty()) {
            System.out.println("Ошибка: Логин, пароль или имя пустые!");
            return -1;
        }

        int newUserId = repository.getNextUserId();
        User newUser = new User(newUserId, userdata.getLogin(), userdata.getPassword(), userdata.getName());

        repository.addUser(newUser);
        System.out.println("Пользователь '" + newUser.getLogin() + "' успешно добавлен! ID: " + newUserId);
        return newUserId;
    }

    public User getUser(int id) {
        User user = repository.getById(id);

        if (user == null) {
            System.out.println("Ошибка: Пользователь с id " + id + " не найден!");
            return null;
        }
        return user;
    }

}
