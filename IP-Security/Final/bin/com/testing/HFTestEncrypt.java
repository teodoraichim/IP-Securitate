package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.HelpFunctions;
//import com.security.*;
class HFTestEncrypt {

	@Test
	void test() {
		HelpFunctions help=new HelpFunctions(); // Create a instance of HelpFunctions class 
		byte[] b= {(byte)0x10};// create a byte array that represent 16 (0x10 in hex converted with (byte)  
		String output=help.encrypt("Sn$2uK", b); //output gets the output from funtion encrypt with params "Sn$2uK" and salt
//		System.out.println(output);//to get the hash code to compare 
		assertEquals("e1ca9bbd64e679a06041e7a615e6be45", output);//comparing output wuth what we expected  
	}

}
//https://bluesock.org/~willg/dev/ascii.html 
// de aici sunt codurile pentru cifrele in ascii