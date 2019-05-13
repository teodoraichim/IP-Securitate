package com.testing;

import com.security.Register;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import static org.junit.jupiter.api.Assertions.*;

class RTestRegister2 {

    @Test
    void test() throws AddressException, MessagingException {
        Register test=new Register();
        assertEquals(test.register("SirC", "cristian.gatu@info.uaic.ro", "BicpaiEa12"),true);
    }
}