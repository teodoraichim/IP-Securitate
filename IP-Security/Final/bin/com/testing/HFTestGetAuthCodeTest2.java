package com.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.security.HelpFunctions;

class HFTestGetAuthCodeTest2 {

	@Test
	void test() {
HelpFunctions help=new HelpFunctions();
		
		String output=help.getAuthCode();//genereaza un cod random 
		String output2=help.getAuthCode();//genereaza un cod random 
		System.out.println(output);
		assertFalse(output==output2);
	}

}
