package com.security;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import com.database.SQL_func;

/**
 * Clasa contine functii ce verifica corectitudinea datelor introduse de utilizator la login(respectand un anumit pattern matching)
 */
public class Login {
    private Verify verify = new Verify();
    private HelpFunctions funct = new HelpFunctions();
    private SQL_func db = new SQL_func("C:\\Users\\T\\IP-Securitate\\IP-Security\\Final\\BD_Gestiunea");

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
    public long hoursPassedSince(Date lastActivity){
            Date current=new Date();
            long diffMiliseconds=Math.abs(cur.getTime()-date.getTime());
            long diffHours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            return diffHours;
        }
    public Date stringToDate(String stringDate){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void createSession(String usernameParam){
        byte[] hashedSessionId;
        try {

            KeySpec spec = new PBEKeySpec(usernameParam.toCharArray(), db.getSalt(usernameParam).getBytes(), 65536, 128);
            do{
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                hashedSessionId = factory.generateSecret(spec).getEncoded();
            }while(db.countSession(hashedSessionId)!=0);
            db.addSession(usernameParam,hashedSessionId,currentTime());
            //return new String(hashedSessionId);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

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
