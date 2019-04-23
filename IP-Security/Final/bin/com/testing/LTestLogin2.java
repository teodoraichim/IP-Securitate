package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.Login;

class LTestLogin2 {

	@Test
	void test() {
		Login test= new Login();
		assertEquals(test.login("rares.darabana", "BicpaiEa12"),true);	}

}
