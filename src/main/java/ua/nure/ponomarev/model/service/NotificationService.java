package ua.nure.ponomarev.model.service;

import ua.nure.ponomarev.model.enity.User;
import ua.nure.ponomarev.model.exception.MailSenderException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface NotificationService {

    void sendConfirmEmailToUser(User user,String linkForConfirmation) throws MailSenderException;

    User getUserByEmailToken(int idToken);
}
