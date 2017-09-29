package mail_dispatch;

import model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by AZagorskyi on 13.09.2017.
 */
public class EmailDispatcher {
    private boolean isErrorEmail = true;

    public boolean sendEmail(String emailFrom, String emailTo, String host, User user){
//        Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);

        String gmail_host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", gmail_host);
        properties.put("mail.smtp.port", "587");

        final String username = "zagorskij.andrey@gmail.com";
        final String password = "Pr3Zl511";

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            //message.setFrom(emailFrom);
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
