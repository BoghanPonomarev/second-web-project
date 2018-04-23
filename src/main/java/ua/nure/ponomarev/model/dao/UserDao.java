package ua.nure.ponomarev.model.dao;

import ua.nure.ponomarev.model.enity.User;

import javax.security.auth.login.CredentialException;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public interface UserDao {

    int insertUser(User user);

    User getByParameters( Map<String, Object> parametersMap) throws CredentialException;

    User getUser(int id);

    void deleteUser(User user);

    void updateUser(User user);

    List<User> getAllUsers();
}
