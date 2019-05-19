package com.testing;

import com.security.HelpFunctions;
import com.security.Verify;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class VerifyTest extends HelpFunctions {
    Verify test= new Verify();
    @Test
    void verifyAplhaNumeric() {
        assertEquals(test.verifyAplhaNumeric("test123alphaASDAS"),true);
        assertNotEquals(test.verifyAplhaNumeric("notTRUε"),true);
    }

    @Test
    void verifyMail() {
        assertEquals(test.verifyMail("dsadasdasdsa@info.uaic.ro"),true);
        assertNotEquals(test.verifyMail("bipBεp@info.uaic.ro"),true);
        assertNotEquals(test.verifyMail("dan.mihai@gmail.com"),true);
    }
}