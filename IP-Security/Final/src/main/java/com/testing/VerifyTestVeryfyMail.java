package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.Verify;

class VerifyTestVeryfyMail {

	@Test
	void test() {

		Verify testVerify =new Verify();
		assertEquals(testVerify.verifyMail("cristian.gatu@info.uaic.ro"), true);
		//verify the email adress to be correct 
	}


}
