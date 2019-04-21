package com.security;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

public class Login {
    private Verify verify=new Verify();
    private HelpFunctions funct=new HelpFunctions();
    private SQL_func db=new SQL_func();
   
    public  boolean login(String username,String pass)
    {
        if(username==null||pass==null||username.isEmpty()||pass.isEmpty()) return false;
        if(verify.verifyAplhaNumeric(username))
        {
            String salt=db.getSalt(username); //comented to test
//        	byte[] salt = {(byte)0x10};//made for test
//        	String hash= funct.encrypt(pass, salt);//made for test
            String hash=funct.encrypt(pass,salt.getBytes());// comented to test
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
