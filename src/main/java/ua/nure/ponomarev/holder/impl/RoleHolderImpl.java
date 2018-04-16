package ua.nure.ponomarev.holder.impl;

import org.springframework.stereotype.Component;
import ua.nure.ponomarev.enity.Role;
import ua.nure.ponomarev.holder.RoleHolder;

/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class RoleHolderImpl implements RoleHolder {
    private ThreadLocal<Role> roleThreadLocal = new ThreadLocal<>();

    @Override
    public Role getRole() {
        return roleThreadLocal.get();
    }

    @Override
    public void setRole(Role role) {
        roleThreadLocal.set(role);
    }
}
