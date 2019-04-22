package com;

import com.security.HelpFunctions;
import com.security.Register;
import com.security.User;

import javax.mail.MessagingException;

public class Main {
    public static void main(String[] args) throws MessagingException {
        User user1=new User("silviu.mariuta","password","silviu.mariuta@info.uaic.ro");
        System.out.println(user1.register());

    }
}
