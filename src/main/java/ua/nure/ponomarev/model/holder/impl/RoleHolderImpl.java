package ua.nure.ponomarev.model.holder.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.nure.ponomarev.model.enity.Role;
import ua.nure.ponomarev.model.holder.RoleHolder;

/**
 * @author Bogdan_Ponamarev.
 *
 * Class that holds user role during request.
 * Gets role from {@link ua.nure.ponomarev.web.filter.SecurityFilter}
 */
@NoArgsConstructor
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
