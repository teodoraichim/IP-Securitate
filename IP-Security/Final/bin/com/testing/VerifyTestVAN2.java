package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.Verify;

class VerifyTestVAN2 {

	@Test
	void test() {
		Verify testVerify =new Verify();
		assertEquals(testVerify.verifyAplhaNumeric("prenume.nume"), true);
	}

}
