package service.repository;

import service.entity.BasicUser;
import service.entity.User;

public interface IUserRepository extends IRepository<User> {

    User getById(int id);
    int addUser(BasicUser user);
    int getNextUserId();
}
