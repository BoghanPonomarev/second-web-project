package ua.nure.ponomarev.model.exception;

import lombok.Getter;

/**
 * @author Bogdan_Ponamarev.
 *
 * Eception class that must be thrown when there are some problem with registration
 * like invalid credention,already exist user etc.
 */

@Getter
public class RegistrationException extends Throwable {
    private String message;
    public RegistrationException(String message) {
        this.message = message;
    }
}
