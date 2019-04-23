package com.testing;
import com.security.*;
//import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HFTestGetAuthCode {

	@Test
	void test() {
		HelpFunctions help=new HelpFunctions();
		
		String output=help.getAuthCode();//genereaza un cod random 
		String output2=help.getAuthCode();//genereaza un cod random 
		System.out.println(output);
		assertFalse(output==output2);//this should fail to be true
	}

}
