package ua.nure.ponomarev.model.service;


import ua.nure.ponomarev.model.enity.User;
import ua.nure.ponomarev.model.exception.MailSenderException;
import ua.nure.ponomarev.model.exception.RegistrationException;
import ua.nure.ponomarev.web.form.AuthorizationUserForm;
import ua.nure.ponomarev.web.key.AuthorizationUserKey;

import javax.security.auth.login.CredentialException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface UserService {

    void sendConfirmEmail(User user,String linkForConfirmation) throws MailSenderException, RegistrationException;

    void confirmAccount(int emailTokenId) throws RegistrationException;

    AuthorizationUserKey loginUser(String login,String password) throws CredentialException;
}
