package com.security;

//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import com.database.SQL_func;

public class Login {
    private Verify verify = new Verify();
    private HelpFunctions funct = new HelpFunctions();
    private SQL_func db = new SQL_func("/home/silviu/JavaProjects/BD_Gestiunea");

    public boolean login(String username, String pass) {
        if (username == null || pass == null || username.isEmpty() || pass.isEmpty()) return false;
        if (verify.verifyAplhaNumeric(username)) {
            if (db.countUsersByName(username) != 0) {
                String salt = db.getSalt(username); //comented to test
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

}
