import java.security.SecureRandom;

public class Singin {
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

}