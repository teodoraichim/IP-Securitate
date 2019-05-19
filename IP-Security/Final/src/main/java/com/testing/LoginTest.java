package com.testing;

import com.security.HelpFunctions;
import com.security.Login;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LoginTest extends HelpFunctions {

    Login user = new Login();
    @Test
    void getError() {
    }

    @Test
    void login() {

        assertEquals(user.login("teodora.ichim","parola123"),true);
        System.out.println("");
        assertNotEquals(user.login("daniel.mihai","parola123"),true);
        System.out.println("");
        assertNotEquals(user.login("blalal","pass"),true);
        System.out.println("");
        assertNotEquals(user.login("teodora.ichim","paro2la123"),true);
        System.out.println("");

    }

    @Test
    void stringToDate() {
        assertNotEquals(user.stringToDate("1999-12-22 12:33"), user.stringToDate("1999-12-22 12:32"));
    }

    @Test
    void getUsername() {

        assertEquals(user.getUsername(user.createSession("damnSon")),"damnSon\t"); //username-ul pe baza hash-ului de sesiune
        System.out.println("");

    }

    @Test
    void createSession() {

        assertNotEquals(user.createSession("daniel.mihai"),user.createSession("daniel.miha")); // verifica daca sesiunea
        System.out.println("");                                                                                 // este diferita fata de a altui user
    }


    @Test
    void checkSession() {
        assertEquals(user.checkSession(user.createSession("daniel.mihai")),true); // verifica daca sesiunea a fost creata
    }
}