import java.security.SecureRandom;
import javax.mail.*;
import javax.mail.internet.*;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import sun.plugin2.message.Message;
import sun.plugin2.message.transport.Transport;
import java.util.Properties;
import java.util.regex.Pattern;


public class Signin {
    private Verify verify=new Verify();
    private HelpFunctions funct=new HelpFunctions();
    private DatabaseOperations db=new DatabaseOperations();
    public  boolean login(String username,String pass)
    {
        if(verify.verifyAplhaNumeric(username))
        {
            String salt=db.getSalt(username);

            String hash=funct.encrypt(pass,salt.getBytes());
            if(db.countUsersByUsernamePass(username,hash)!=0)
                return true;
        }
        else
        {
            System.out.println("Username must be alphanumeric.");
        }
        return false;
    }
    public  void  sendEmail(String to, String code) {
        String from = "fiicatalog.verify@gmail.com";
        String pass = "1rtU7AIC";
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Verification code for the catalog app");
            message.setText("The following is the verification code:" + code + "\n\nUse it in order to activate your account.");
            Transport.send(message, from, pass);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
