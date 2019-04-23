package com.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.HelpFunctions;

class HFTestEncrypt2 {

	@Test
	void test() {
		HelpFunctions help=new HelpFunctions(); 
		byte[] b= {(byte)0x13};
		String output=help.encrypt("Sn$2uK", b); 
		System.out.println(output); 
		assertEquals("792abffd0ea7ec9b520d5b1eec69c015", output);
	}

}
