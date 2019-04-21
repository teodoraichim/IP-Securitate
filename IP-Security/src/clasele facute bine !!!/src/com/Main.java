package com;

import com.security.HelpFunctions;
import com.security.Register;
import com.security.User;

public class Main {
    public static void main(String[] args) {
        User user1=new User("silviu.mariuta","password","silviu.mariuta@info.uaic.ro");
        System.out.println(user1.login());
        HelpFunctions func=new HelpFunctions();
        System.out.println(func.encrypt("password","1sfg6".getBytes()));
    }
}
