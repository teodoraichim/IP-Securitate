package com.testing;

import com.security.HelpFunctions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

class HelpFunctionsTest extends HelpFunctions {
    HelpFunctions test= new HelpFunctions();
    @Test
    void generateAuthCode1() {
        String output=test.generateAuthCode();//genereaza un cod random
        String output2=test.generateAuthCode();//genereaza un cod random

        assertNotEquals(output,output2);
    }

    @Test
    void generateRandomString1() {
        String output1 = test.generateRandomString(10); // siruri random de lungime 10
        String output2 = test.generateRandomString(10);
        assertNotEquals(output1,output2);
    }

    @Test
    void encrypt1() {
        byte[] b= {(byte)0x10};// create a byte array that represent 16 (0x10 in hex converted with (byte)
        String output=test.encrypt("Sn$2uK", b); //output gets the output from funtion encrypt with params "Sn$2uK" and salt
//		System.out.println(output);//to get the hash code to compare
        assertEquals("e1ca9bbd64e679a06041e7a615e6be45", output);//comparing output wuth what we expected
        assertNotEquals("False", output);

    }
}