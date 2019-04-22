package com.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Register {
    private Verify verify = new Verify();
    private HelpFunctions funct = new HelpFunctions();

    private SQL_func db = new SQL_func();

//    public String generateAuthCode() {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            SecureRandom random = new SecureRandom();
//            byte[] code = new byte[16];
//            random.nextBytes(code);
//            md.update(code);
//            byte[] digest = md.digest();
//            return funct.byteToString(digest);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "0";
//    }

    public boolean register(String username, String mail, String pass) throws AddressException, MessagingException {
        if (username == null || mail == null || pass == null || username.isEmpty() || mail.isEmpty() || pass.isEmpty()) return false;
            if (verify.verifyMail(mail))
                if (verify.verifyAplhaNumeric(username)) {
                    SecureRandom random = new SecureRandom();
                    byte[] salt = new byte[16];
                    random.nextBytes(salt);
                    String hash = funct.encrypt(username, salt);
                    if (db.countUsersByName(username) != 0) {
                        System.out.println("Username already taken.");
                    } else {
                        if (db.countUsersByMail(mail) != 0) {
                            System.out.println("Mail was already used.");
                        } else {
                            String auth = funct.generateAuthCode();
                            db.addNewUser(String.valueOf(random.nextInt(100000)),username, mail,hash,funct.byteToString(salt),"","student",auth);
                            sendEmail(mail, auth);
                            return true;
                        }
                    }
                } else
                    System.out.println("Invalid username:must be alphanumeric");
            else {
                System.out.println("Invalid email:must be nume.prenume@info.uaic.ro");

            }
        return false;
    }

    public boolean activate(String username, String auth_code) {
        if (db.checkAuthCode(username, auth_code) == 0)
            return false;
        db.confirmUser(username);
        return true;
    }

    public void sendEmail(String to, String code) throws AddressException, javax.mail.MessagingException {
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
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Verification code for the catalog app");
        message.setText("The following is the verification code:" + code + "\n\nUse it in order to activate your account.");
        Transport.send(message, from, pass);
        System.out.println("Sent message successfully....");
    }

}
