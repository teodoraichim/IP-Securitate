package com.security;

import com.database.SQL_func;

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

    private SQL_func db = new SQL_func("/home/silviu/JavaProjects/BD_Gestiunea");

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

    /**
     * Functia verifica ca username-ul, parola si email-ul introduse de catre utilizator sa respecte pattern-ul de logare
     * @param username Parametrul care reprezinta username-ul plain-text introdus de catre utilizator
     * @param mail  Parametrul care reprezinta mail-ul plain-text introdus de catre utilizator
     * @param pass Parametrul care reprezinta parola plain-text introdusa de catre utilizator
     * @return Returneaza true daca patternul este respectat, false altfel
     * @throws AddressException
     * @throws MessagingException
     */
    public boolean register(String username, String mail, String pass) throws AddressException, MessagingException {
        if (username == null || mail == null || pass == null || username.isEmpty() || mail.isEmpty() || pass.isEmpty()) return false;
            if (verify.verifyMail(mail))
                if (verify.verifyAplhaNumeric(username)) {
                    SecureRandom random = new SecureRandom();
                    byte[] salt = new byte[16];
                    String salt_string;
                    salt_string=funct.generateRandomString(16);
                    System.out.println("salt register:"+salt_string+":"+salt_string.length());
                    salt=salt_string.getBytes();
                    String hash = funct.encrypt(pass, salt);
                    if (db.countUsersByName(username) != 0) {
                        System.out.println("Username already taken.");
                    } else {
                        if (db.countUsersByMail(mail) != 0) {
                            System.out.println("Mail was already used.");
                        } else {
                            String auth = funct.generateAuthCode();
                            db.addNewUser(username, mail,hash,salt_string,auth);
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

    /**
     * Functie care verifica in baza de date daca unui username ii este asociata o functie hash
     * @param username Parametrul care reprezinta username-ul introdus de care utilizator
     * @param auth_code Parametrul care reprezinta codificarea hash pentru acel username
     * @return
     */
    public boolean activate(String username, String auth_code) {
        if (db.checkAuthCode(username, auth_code) == 0)
            return false;
        db.confirmUser(username);
        return true;
    }

    /**
     * Functie care trimite utilizatorului un email de verificare ca urmare a inregistratii in aplicatie
     * @param to Parametrul care reprezinta destinatarul email-ului
     * @param code Parametrul care reprezinta codul de activare al contului
     * @throws AddressException
     * @throws javax.mail.MessagingException
     */
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
        message.addRecipient(Message.RecipientType.TO, n    ew InternetAddress(to));
        message.setSubject("Verification code for the catalog app");
        message.setText("The following is the verification code:" + code + "\n\nUse it in order to activate your account.");
        Transport.send(message, from, pass);
        System.out.println("Sent message successfully....");
    }

}
