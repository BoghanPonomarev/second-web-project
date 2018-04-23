package ua.nure.ponomarev.model.annotation;

import ua.nure.ponomarev.model.enity.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Bogdan_Ponamarev.
 *Custom annotation for user role distinction.
 *Uses class {@link Role} for definition of what
 * roles can use a method.It partly similar with function of
 * Spring security
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoleAccess {
    public Role[] role();
}
