package ua.nure.ponomarev.model.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.nure.ponomarev.model.exception.MailSenderException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class EmailSenderImpl implements EmailSender {
    private static Logger logger = LoggerFactory.getLogger(EmailSenderImpl.class);
    private Properties properties;

    /**
     * Constructor that read properties file
     */
    public EmailSenderImpl() {
        properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("Notification.properties");
            properties.load(input);
        } catch (IOException e) {
            logger.error("Could not get the settings for working with mail sender", e);
        }
    }

    /**
     * Send email to particular user , with using properties file
     *
     * @param recipientEmail email in which letter will be sent
     * @param topic          topic of the letter
     * @param massageHTML    inner massage that have HTML format
     * @throws MailSenderException if there are some problems
     *                             with email sending module
     */
    @Override
    public void sendEmail(String recipientEmail, String topic, String massageHTML) throws MailSenderException {
        Session session = Session.getInstance(properties);

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(properties.getProperty("mail.login")));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            msg.setSubject(topic);
            msg.setText(massageHTML, properties.getProperty("mail.mime.charset"), "html");
            Transport.send(msg, properties.getProperty("mail.login"), properties.getProperty("mail.password"));

        } catch (MessagingException e) {
            logger.error("Could not send mail notification", e);
            throw new MailSenderException("Could not sent email cause by: "+e);
        }
    }

}
