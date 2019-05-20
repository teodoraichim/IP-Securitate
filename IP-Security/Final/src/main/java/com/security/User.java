package com.security;

import javax.mail.MessagingException;
/**
 * Clasa care concretizeaza user-ul (username,password,mail) cu posibilitate de logare si inregistrare in sistem
 */
public class User {
    private String username;
    private String pass;
    private String mail;
    private String accessLevel;
    private Login userLogin=new Login();
    private int user_id;
    private Register userRegister=new Register();
    private Access userAccess=new Access();
    /**
     * Constructorul clasei, (3 parametri)initializeaza datele membre ale clasei cu nume, parola si email
     */
    public String getAccessLevel()
    {
        return this.accessLevel;
    }

    public int getUserID()
    {
        return this.user_id;
    }
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
        boolean ret=userLogin.login(username,pass);
        if(ret) {
            user_id = userLogin.getUserID();
            if (userAccess.isAdministrator(username)) accessLevel = "Administrator";
            else if (userAccess.isProfesor(username)) accessLevel = "Profesor";
            else if (userAccess.isSecretar(username)) accessLevel = "Secretar";
            else if (userAccess.isStudent(username)) accessLevel = "Student";
        }
        return ret;
    }
    public String getLoginError()
    {
        return userLogin.getError();
    }
    public String getRegisterError()
    {
        return userRegister.getError();
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


    public String createSession() {
        return userLogin.createSession(username);
    }
}
