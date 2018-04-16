package ua.nure.ponomarev.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
public class AccessPrivilegeException extends Exception {
    private String massage;
    public AccessPrivilegeException(String massage){
        this.massage = massage;
    }
}
