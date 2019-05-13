package com.testing;

import com.security.Verify;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerifyTestMAIL2 {

    @Test
    void verifyMail() {
        Verify testVerify =new Verify();
        assertEquals(testVerify.verifyMail("daniel.mihai@info.uaic.ro"), true);
    }
}