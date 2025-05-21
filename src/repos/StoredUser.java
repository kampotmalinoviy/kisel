package repos;
import service.entity.User;

public class StoredUser {
    int id;
    User user;

    public StoredUser(int id, User user) {
        this.id = id;
        this.user = user;
    }
}
