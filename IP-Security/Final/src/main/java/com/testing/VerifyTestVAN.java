package com.testing;
import com.security.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VerifyTestVAN {

	@Test
	void test() {

		Verify testVerify =new Verify();
		assertEquals(testVerify.verifyAplhaNumeric("cristian._.gatu"), true);//verify if username is correct 
	}

}
