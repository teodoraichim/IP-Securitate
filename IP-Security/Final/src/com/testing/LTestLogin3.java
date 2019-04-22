package com.testing;

import com.security.Login;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LTestLogin3 {

    @Test
    void login() {
        Login test= new Login();
        assertEquals(test.login("danielmihai", "dsads%&$#RQWEDS"),true);
    }
}