package ua.nure.ponomarev.model.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ponomarev.model.dao.UserDao;
import ua.nure.ponomarev.model.enity.User;
import ua.nure.ponomarev.model.exception.MailSenderException;
import ua.nure.ponomarev.model.exception.RegistrationException;
import ua.nure.ponomarev.model.service.NotificationService;
import ua.nure.ponomarev.model.service.UserService;

import javax.security.auth.login.CredentialException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
@Service
@PropertySource("classpath:Notification.properties")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationService notificationService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendConfirmEmail(User user, String linkForConfirmation) throws MailSenderException, RegistrationException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", user.getEmail());
        paramMap.put("phoneNumber", user.getPhoneNumber());
        try {
            if (userDao.getByParameters(paramMap) != null) {
                logger.info("User "+user.getEmail()+" tried register twice");
                throw new RegistrationException("User already exist");
            }
            notificationService.sendConfirmEmailToUser(user, linkForConfirmation);
        } catch (CredentialException e) {
            logger.error("Can`t send mail cause of: " + e);
            throw new RegistrationException("There was credential exception during getting user from data storage");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void confirmAccount(int emailTokenId) throws RegistrationException {
        User user = notificationService.getUserByEmailToken(emailTokenId);
        if (user == null) {
            logger.info("User tried to register by invalid or outdated token");
            throw new RegistrationException("There is no such token");
        }
        userDao.insertUser(user);
    }
}
