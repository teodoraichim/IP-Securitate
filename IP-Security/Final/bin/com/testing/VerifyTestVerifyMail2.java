package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.Verify;

class VerifyTestVerifyMail2 {

	@Test
	void test() {
		Verify testVerify =new Verify();
		assertEquals(testVerify.verifyMail("mail.test@info.uaic.ro"), true);
	}

}
