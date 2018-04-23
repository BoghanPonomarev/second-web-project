package ua.nure.ponomarev.model.service;


import ua.nure.ponomarev.model.enity.User;
import ua.nure.ponomarev.model.exception.MailSenderException;
import ua.nure.ponomarev.model.exception.RegistrationException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface UserService {

    void sendConfirmEmail(User user,String linkForConfirmation) throws MailSenderException, RegistrationException;

    void confirmAccount(int emailTokenId) throws RegistrationException;
}
