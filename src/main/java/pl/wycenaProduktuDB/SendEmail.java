package pl.wycenaProduktuDB;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author m
 */
public class SendEmail {

    public void SendEmail(String subject, String text, String email) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.googlemail.com");
        props.put("mail.from", "inzmailserv@gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom();
            msg.setRecipients(Message.RecipientType.TO, email);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(text);

            try (Transport transport = session.getTransport("smtp")) {
                transport.connect("inzmailserv@gmail.com", "hvtswxzaovsgvlgg");
                transport.sendMessage(msg, msg.getAllRecipients());
            }

            Transport.send(msg);

        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }
    }
}
