import java.security.SecureRandom;

public class Singout {
    private Verify verify=new Verify();
    private HelpFunctions funct=new HelpFunctions();
    private MAIL newmail=new MAIL();
    private DatabaseOperations db=new DatabaseOperations();
    public  boolean register(String username, String mail, String pass) {
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
                        String auth = funct.getAuthCode();
                        db.addNewUser(username, mail, funct.byteToString(salt), hash, auth);
                        newmail.sendEmail(mail, auth);
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
    public boolean activate(String username,String auth_code)
    {
        if(db.checkAuthCode(username,auth_code)==0)
            return false;
        else return true;
    }

}
