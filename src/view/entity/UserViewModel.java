package view.entity;
import service.entity.User;

public class UserViewModel {
    private User user;
    public String name;

    public UserViewModel(User user) {
        this.user = user;
        name = user.getName();
    }
    public User getUser() {
        return user;
    }


}
