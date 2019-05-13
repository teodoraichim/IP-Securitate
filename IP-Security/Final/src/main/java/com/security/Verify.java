package com.security;
import java.util.regex.Pattern;

/**
 * Clasa care verifica ca unele date introduse de catre client sa respecte un anumit pattern
 */
public class Verify {
    /**
     * Functie care verifica daca un string este alphaNumeric si daca contine " . " sau " _ "
     * @param plainName Parametrul care identifica stringul ce urmeaza sa fie verificat
     * @return Returneaza true daca parametrul este format din numere,majuscule,minuscule," . "," _ " , false altfel
     */
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
     * Functie care verifica daca un string respecta formatul (nume.prenume@info.uaic.ro)
     * @param plainMail Parametrul care identifica stringul ce urmeaza sa fie verificat
     * @return Returneaza true daca parametrul respecta formatul, false altfel
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
