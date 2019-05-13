package com.security;

import com.database.SQL_func;

public class Access {
    public SQL_func funct=new SQL_func("C:\\Users\\T\\IP-Securitate\\IP-Security\\Final\\BD_Gestiunea");
    public boolean isStudent(String username){
        return funct.getAccessLevel(username).equals("Student");


    }
    public boolean isProfesor(String username){
        return funct.getAccessLevel(username).equals("Profesor");


    }
    public boolean isSecretar(String username){
        return funct.getAccessLevel(username).equals("Secretar");


    }
    public boolean isAdministrator(String username){
        return funct.getAccessLevel(username).equals("Administrator");

    }

}
