package com.security;

import javax.mail.MessagingException;
/**
* Clasa care concretizeaza user-ul (username,password,mail) cu posibilitate de logare si inregistrare in sistem
*/
public class User {
    private String username;
    private String pass;
    private String mail;
    private Login userLogin=new Login();
    private Register userRegister=new Register();
    /**
    * Constructorul clasei, (3 parametri)initializeaza datele membre ale clasei cu nume, parola si email
    */
    public User(String name,String pass,String mail)
    {
        this.username=name;
        this.pass=pass;
        this.mail=mail;
    }
    /**
    * Functia apeleaza functia de login din clasa Login (pentru a testa logarea unui utilizator )
    *@return Returneaza true daca s-a realizat loginul cu succes, false altfel
    */
    public boolean login()
    {
     return userLogin.login(username,pass);
    }
    /**
    * Functia apeleaza functia de register din clasa Register (pentru a testa inregistrarea in sistem a unui utilizator )
    *@return Returneaza true daca inregistraera s-a realizat cu succes, false altfel
    */
    public boolean register() throws MessagingException {
        return userRegister.register(username,mail,pass);
    }
    /**
    * Functia apeleaza functia activate() din clasa Register (pentru a testa daca unui username ii este asociata o functie hash din baza de date)
    *@return Returneaza true daca username-ul este asociat, false altfel
    */
    public boolean activate(String auth_code)
    {
        return userRegister.activate(username,auth_code);
    }



}
