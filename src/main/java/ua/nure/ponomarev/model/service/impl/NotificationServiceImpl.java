package ua.nure.ponomarev.model.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ponomarev.model.enity.User;
import ua.nure.ponomarev.model.exception.MailSenderException;
import ua.nure.ponomarev.model.holder.RequestedAccountHolder;
import ua.nure.ponomarev.model.sender.EmailSender;
import ua.nure.ponomarev.model.service.NotificationService;

/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class NotificationServiceImpl implements NotificationService{

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private RequestedAccountHolder requestedAccountHolder;
    @Autowired
    private Environment environment;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendConfirmEmailToUser(User user,String linkForConfirmation) throws MailSenderException {
        int tokenId = requestedAccountHolder.addToRequested(user);
        String htmlMassage = environment.getProperty("mail.htmlMessage");
        if(htmlMassage == null){
            logger.error("There is no html massage for email confirmation");
        }
        emailSender.sendEmail(user.getEmail(), environment.getProperty("mail.topic")
                , String.format(htmlMassage, linkForConfirmation + "?id=" + tokenId));
    }

    @Override
    public User getUserByEmailToken(int idToken) {
        return requestedAccountHolder.getUserIfRequested(idToken);
    }
}
