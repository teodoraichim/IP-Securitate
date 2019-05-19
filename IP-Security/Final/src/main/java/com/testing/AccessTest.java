package com.testing;

import com.security.Access;
import com.security.HelpFunctions;
import com.security.Login;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccessTest extends HelpFunctions {
    Login user = new Login();

    Access acces = new Access();
    @Test
    void isStudent() throws MessagingException {
        user.login("teodora.ichim","parola123");
        assertEquals(acces.isStudent("teodora.ichim"), true);
        System.out.println("");
    }

    @Test
    void isProfesor() {
        user.login("teodora.ichim","parola123");
        assertEquals(acces.isProfesor("teodora.ichim"), false);
        System.out.println("");
    }

    @Test
    void isSecretar() {
        user.login("teodora.ichim","parola123");
        assertEquals(acces.isSecretar("teodora.ichim"), false);
        System.out.println("");
    }

    @Test
    void isAdministrator() {
        user.login("teodora.ichim","parola123");
        assertEquals(acces.isAdministrator("teodora.ichim"), false);
        System.out.println("");
    }
}