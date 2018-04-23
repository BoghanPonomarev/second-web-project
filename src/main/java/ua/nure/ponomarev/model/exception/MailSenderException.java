package ua.nure.ponomarev.model.exception;

import lombok.Getter;

/**
 * @author Bogdan_Ponamarev.
 *
 * Exception class that may be occurs when there is problem with
 * {@link ua.nure.ponomarev.model.sender.EmailSender}
 */
@Getter
public class MailSenderException extends Throwable {
    private String message;
    public MailSenderException(String message) {
    this.message = message;
    }
}
