package mail_dispatch;

import model.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by AZagorskyi on 13.09.2017.
 */
public class EmailDispatcher {
    private boolean isErrorEmail = true;

    public boolean sendEmail(String emailFrom, String emailTo, String host, User user){
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(emailFrom);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            message.setSubject("Восстановление пользователя.");
            message.setText("Ваш логин: " + user.getUsername());
            message.setText("Ваш пароль: " + user.getPassword());
            Transport.send(message);
            isErrorEmail = false;
        } catch (MessagingException e) {
            e.printStackTrace();
            isErrorEmail = true;
        }
        return isErrorEmail;
    }
}
