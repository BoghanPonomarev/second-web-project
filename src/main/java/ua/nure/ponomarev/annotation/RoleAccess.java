package ua.nure.ponomarev.annotation;

import ua.nure.ponomarev.enity.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Bogdan_Ponamarev.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface RoleAccess {

    public Role[] role();
}
