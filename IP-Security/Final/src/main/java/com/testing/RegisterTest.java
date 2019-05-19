package com.testing;

import com.security.HelpFunctions;
import com.security.Register;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.assertNotEquals;

class RegisterTest extends HelpFunctions {
    Register test=new Register();

    @Test
    void register() throws MessagingException {
        assertNotEquals(test.register("Yes", "daniel.mihai@ino.uaic.ro", "BicpaiEa12"),true);
        System.out.println("");
        assertNotEquals(test.register("SirC", "cristian.gatu@info.uaic.ro", "BicpaiEa12"),true);

    }}
