package com.testing;

import com.security.Login;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LTestLogin4 {

    @Test
    void login() {
        Login test= new Login();
        assertEquals(test.login("TrololoiAlexandru", "d213WEDS"),true);


    }
}