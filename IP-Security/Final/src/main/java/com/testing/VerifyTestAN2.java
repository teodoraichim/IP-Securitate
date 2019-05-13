package com.testing;

import com.security.Verify;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerifyTestAN2 {

    @Test
    void verifyAplhaNumeric() {
        Verify testVerify =new Verify();
        assertEquals(testVerify.verifyAplhaNumeric("daniel.mihai"), true);
    }
}