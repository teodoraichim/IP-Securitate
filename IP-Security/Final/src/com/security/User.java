package com.security;

import javax.mail.MessagingException;

public class User {
    private String username;
    private String pass;
    private String mail;
    private Login userLogin=new Login();
    private Register userRegister=new Register();
    public User(String name,String pass,String mail)
    {
        this.username=name;
        this.pass=pass;
        this.mail=mail;
    }
    public boolean login()
    {
     return userLogin.login(username,pass);
    }
    public boolean register() throws MessagingException {
        return userRegister.register(username,mail,pass);
    }
    public boolean activate(String auth_code)
    {
        return userRegister.activate(username,auth_code);
    }



}
