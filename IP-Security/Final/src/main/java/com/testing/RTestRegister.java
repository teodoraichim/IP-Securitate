package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.jupiter.api.Test;

import com.security.Register;

class RTestRegister {

	@Test
	void test() throws AddressException, MessagingException {
		Register test=new Register();
		assertEquals(test.register("SirC", "cristian.gatu@info.uaic.ro", "BicpaiEa12"),true);
	}

}
