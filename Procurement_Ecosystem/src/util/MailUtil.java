package util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.FileInputStream;
import java.io.IOException;

public class MailUtil {
    public static String EMAIL;
    public static String APP_PASSWORD;

    static {
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("email.properties"); // project root
            props.load(in);
            EMAIL = props.getProperty("email");
            APP_PASSWORD = props.getProperty("app_password");
        } catch (IOException e) {
            System.err.println("⚠️ Failed to load email.properties");
            e.printStackTrace();
        }
    }

    public static void sendLogisticsStatusEmail(String to, String subject, String body) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL)); // sender = loaded email
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}