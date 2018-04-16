package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.enity.User;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface UserDao {

    int insertUser(User user);

    User getUser(int id);

    void deleteUser(User user);

    void updateUser(User user);

    List<User> getAllUsers();
}
