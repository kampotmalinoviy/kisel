package service.model;
import service.repository.IUserRepository;
import service.entity.User;

public class AuthModel {

    IUserRepository userRepository;

    public AuthModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int authUser(String login, String password) {
        for (int id = 1; id <= 100; id++) {
            User user = userRepository.getById(id);

            if (user != null && user.getLogin().equals(login)) {
                if (user.getPassword().equals(password)) {
                    System.out.println("Успешный вход! ID пользователя: " + user.getId());
                    return user.getId();
                } else {
                    System.out.println("Ошибка входа: Неверный пароль.");
                    return -1;
                }
            }
        }

        System.out.println("Ошибка входа: Пользователь с логином '" + login + "' не найден.");
        return -1;
    }
}
