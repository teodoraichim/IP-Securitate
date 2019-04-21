package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.Login;
;
class LTestLogin {

	@Test
	void test() {
		Login test= new Login();
		assertEquals(test.login("cristian.gatu", "BicpaiEa12"),true);

	}

}
