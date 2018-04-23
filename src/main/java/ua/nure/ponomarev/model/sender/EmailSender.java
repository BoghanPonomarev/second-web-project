package ua.nure.ponomarev.model.sender;

import ua.nure.ponomarev.model.exception.MailSenderException;

/**
 * @author Bogdan_Ponamarev.
 *
 * Functional interface that have sending email letter mathod
 */
public interface EmailSender {
    /**
     * Send email to particular user
     * @param email email in which letter will be sent
     * @param topic topic of the letter
     * @param massageHTML inner massage that have HTML format
     * @throws MailSenderException if there are some problems
     * with email sending module
     */
    void sendEmail(String email, String topic, String massageHTML) throws MailSenderException;
}
