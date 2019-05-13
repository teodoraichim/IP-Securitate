package com.security;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import com.database.SQL_func;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Clasa contine functii ce verifica corectitudinea datelor introduse de utilizator la login(respectand un anumit pattern matching)
 */
public class Login {
    private Verify verify = new Verify();
    private HelpFunctions funct = new HelpFunctions();
    private SQL_func db = new SQL_func("/home/silviu/JavaProjects/securitate_new2/IP-Securitate/IP-Security/Final/BD_Gestiunea");

    /**
     * Functia verifica ca username-ul si parola introduse de catre utilizator sa respecte pattern-ul de logare
     * @param username Parametrul reprezinta username-ul plain-text introdus de utilizator
     * @param pass Parametrul reprezinta username-ul plain-text introdus de utilizator
     * @return Returneaza true daca patternul este respectat, false altfel
     */
    public boolean login(String username, String pass) {
        if (username == null || pass == null || username.isEmpty() || pass.isEmpty()) return false;
        if (verify.verifyAplhaNumeric(username)) {
            if (db.countUsersByName(username) != 0) {
                String salt = db.getSalt(username);
//        	byte[] salt = {(byte)0x10};//made for test
//        	String hash= funct.encrypt(pass, salt);//made for test
                System.out.println("salt din db:"+salt+":"+salt.length());
                System.out.println(pass);
                String hash = funct.encrypt(pass, salt.getBytes());// comented to test
//                System.out.println(hash);
                if (db.countUsersByUsernamePass(username, hash) != 0) {
                    if (db.verificareLogIn(username))
                        return true;
                    else System.out.println("Account not activated");
                } else {
                    System.out.println("Username/pass invalid");
                }
            }

        } else {
            System.out.println("Username must be alphanumeric.");
        }
        return false;
    }

    public Date stringToDate(String stringDate){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String currentTime() { //conversia date->string
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format;
        Date date;
        Date current;
        try {
            date = sd.parse("1999-09-02 10:22");
            current = new Date();
            format = sd.format(current);
            return format;
        } catch (Exception e) {
            return "error" + e;
        }
    }
    public String createSession(String usernameParam){
            String hash;
            do{
                byte[] salt;
                String salt_string;
                salt_string=funct.generateRandomString(16);
                salt=salt_string.getBytes();
                 hash = funct.encrypt(usernameParam, salt);
            }while(db.countSession(hash)!=0);

            db.addSession(usernameParam,hash,currentTime());
            return hash;


    }
    public long hoursPassedSince(Date lastActivity){
        Date current=new Date();
        long diffMiliseconds=Math.abs(current.getTime()-lastActivity.getTime());
        long diffHours = TimeUnit.HOURS.convert(diffMiliseconds, TimeUnit.MILLISECONDS);
        return diffHours;
    }
    public boolean checkSession(String hashedSessionIdParam){

            if(db.countSession(hashedSessionIdParam)!=0){
                if(hoursPassedSince(stringToDate(db.getTime(hashedSessionIdParam)))<2){
                    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date currentDate=new Date();
                    //conversia date->string
                    String currentDateString=sd.format(currentDate);
                    db.updateSessionActivity(hashedSessionIdParam,currentDateString);
                    return true;
                }else{
                    db.deleteSession(hashedSessionIdParam);
                    return false;
                }
            }else return false;
    }

}
