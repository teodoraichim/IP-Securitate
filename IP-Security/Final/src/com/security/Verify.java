package com.security;
import java.util.regex.Pattern;

public class Verify {

    public boolean verifyAplhaNumeric(String plainName) {
        //check if username is alphanumeric
        for (int i = 0; i < plainName.length(); i++) {
            char c = plainName.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                if(c !='.' && c!='_')
                    return false;
        }
        return true;
    }

    /**
     * Used for verifying if the email address given user input is of the correct format(nume.prenume@info.uaic.ro)
     **/
    public boolean verifyMail(String plainMail) {
        String emailRegex = "^[a-zA-Z0-9-]+(?:\\." +
                "[a-zA-Z0-9]+)*@" +
                "info\\.uaic\\.ro";
        		
        Pattern pat = Pattern.compile(emailRegex);
        if (plainMail == null)
            return false;
//        System.out.println(emailRegex);
        return pat.matcher(plainMail).matches();
    }
}
