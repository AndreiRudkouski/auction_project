package by.rudkouski.auction.mail;

import by.rudkouski.auction.command.impl.PwdForgotCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static by.rudkouski.auction.constant.ConstantName.*;

/**
 * This class is for sending email
 */

public class MailSender {
    private static final Logger LOGGER = LogManager.getLogger(PwdForgotCommand.class);
    private static final Properties PROP = new Properties();
    private static final MailSender INSTANCE = new MailSender();

    private String username;
    private String password;

    private MailSender() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(MAIL_PROPERTIES);
        try {
            PROP.load(stream);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Wrong initialization mail sender", e);
            throw new RuntimeException("Error of mail sender initialization", e);
        }
        username = PROP.getProperty(MAIL_USER);
        password = PROP.getProperty(MAIL_PWD);
    }

    public static MailSender getInstance() {
        return INSTANCE;
    }


    /**
     * Sends mail to any post box
     *
     * @param theme theme of mail
     * @param text    content of mail
     * @param toEmail email for sending
     * @throws MailSenderException if MessagingException is thrown
     */
    public void send(String theme, String text, String toEmail) throws MailSenderException {
        Session session = Session.getDefaultInstance(PROP, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(theme);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new MailSenderException("Error during email sending", e);
        }
    }
}