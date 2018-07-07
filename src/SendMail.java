
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SendMail
{
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "pprovider92@gmail.com";
    private static final String SMTP_AUTH_PWD  = "passprovider";
	
    public SendMail(String msg,String sendTo) throws Exception
    {
       
            Properties properties = new Properties();
            properties.put("mail.transport.protocol","smtps");
            properties.put("mail.smtps.host", SMTP_HOST_NAME);
            properties.put("mail.smtps.auth", "true");
            Session mailSession = Session.getDefaultInstance(properties);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Password Recovery");
            message.setContent(msg, "text/plain");
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(sendTo));
            message.setFrom(new InternetAddress(SMTP_AUTH_USER,"Recovery"));
            transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
            transport.close();
        
        
    }
    
}