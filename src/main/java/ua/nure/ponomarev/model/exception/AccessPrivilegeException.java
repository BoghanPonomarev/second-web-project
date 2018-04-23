package ua.nure.ponomarev.model.exception;

import lombok.Getter;

/**
 * @author Bogdan_Ponamarev.
 *
 * Exception class that occurs when user try to use some
 * method without appropriate role
 */
@Getter
public class AccessPrivilegeException extends Exception {
    private String massage;
    public AccessPrivilegeException(String massage){
        this.massage = massage;
    }
}
