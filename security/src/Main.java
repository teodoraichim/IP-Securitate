//crypt

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

//mail
import javax.mail.*;
import javax.mail.internet.*;
//import javax.activation.*;

//verify
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    /**
     * Helper function used for converting from an array of bytes to a string
     **/

    public static String byteToString(byte[] byteCode) {
        StringBuffer stringCode = new StringBuffer();
        for (byte b : byteCode) {
            stringCode.append(String.format("%02x", b & 0xff));
        }
        return stringCode.toString();

    }

    /**
     * Generates an authentification code that will be used for the email verification procedure
     **/
    public static String getAuthCode() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            SecureRandom random = new SecureRandom();
            byte[] code = new byte[16];
            random.nextBytes(code);

            md.update(code);

            byte[] digest = md.digest();
            return byteToString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * Verifies if a string is alfanumeric
     **/
    public static boolean verifyAplhaNumeric(String plainName) {
        //check if username is alphanumeric
        for (int i = 0; i < plainName.length(); i++) {
            char c = plainName.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }
        return true;
    }

    /**
     * Used for verifying if the email address given user input is of the correct format(nume.prenume@info.uaic.ro)
     **/
    public static boolean verifyMail(String plainMail) {
        String emailRegex = "^[a-zA-Z0-9]+(?:\\." +
                "[a-zA-Z0-9]+)*@" +
                "info\\.uaic\\.ro";

        Pattern pat = Pattern.compile(emailRegex);
        if (plainMail == null)
            return false;
        return pat.matcher(plainMail).matches();
    }

    /**
     * Encrypts a string using the PBKDF2 algorithm
     * Used for encrypting the password given as plain text
     **/
    public static String encrypt(String plain) {
        /**Generating a salt **/
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        byte[] hashedPassword;
        byte[] err = new byte[1];
        err[0] = 0;
        //Save the salt

        try {
            KeySpec spec = new PBEKeySpec(plain.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPassword = factory.generateSecret(spec).getEncoded();
            return byteToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * Used for sending the email containing the verification code
     **/
    public static void sendEmail(String to, String code) {
        // Recipient's email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned
        String from = "fiicatalog.verify@gmail.com";
        String pass = "1rtU7AIC";
        // Get system properties
        Properties props = System.getProperties();
//        properties.setProperty("mail.user","silviu.andrei53m");
//        properties.setProperty("mail.password","");

        String host = "smtp.gmail.com";

        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(props);

        try {
            // Create a default MimeMessage object.

            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Verification code for the catalog app");

            // Now set the actual message
            message.setText("The following is the verification code:" + code + "\n\nUse it in order to activate your account.");

            // Send message
            Transport.send(message, from, pass);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    public static void main(String[] args) {

        String result = encrypt("Ihaeasdfads4");
//        "u�zo��8\u0010\u0004�c_Ŝѷ";
        System.out.println(result);
        String authCode = getAuthCode();

        //sendEmail("silviu.mariuta@info.uaic.ro".toString(),authCode);
        System.out.println(verifyMail("duncan.tree@info.uaic.ro"));
    }
}
