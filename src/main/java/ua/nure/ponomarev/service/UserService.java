package ua.nure.ponomarev.service;

import ua.nure.ponomarev.annotation.RoleAccess;
import ua.nure.ponomarev.enity.Role;
import ua.nure.ponomarev.enity.User;

/**
 * @author Bogdan_Ponamarev.
 */
public interface UserService {
    @RoleAccess(role = {Role.USER,Role.ADMIN})
    void method();
}
