package com.security;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
/**
* Clasa contine functii ajutatoare pentru criptare: generare de valori hash, folosind un salt si algoritmul PBKDF2
*/
public class HelpFunctions {
    /**
     * Functie care returneaza o valoare hash prin utilizrea algoritmului MD5
     * @return Returneaza un string cu message digest-ul(valoarea hash) , "0" altfel
     */
    public String generateAuthCode() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            SecureRandom random = new SecureRandom();
            byte[] code = new byte[16];
            /**
             * Generare numar random de 16 bytes
             */
            random.nextBytes(code);

            md.update(code);
            /**
             * Generare valoare hash(byte array)
             */
            byte[] digest = md.digest();
            /**
             * Convertire din byte array in string si returnare
             */
            return new String(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "0";
    }
    //ramas nefolosita
    public  String byteToString(byte[] byteCode) {
        StringBuffer stringCode = new StringBuffer();
        for (byte b : byteCode) {
            stringCode.append(String.format("%02x", b & 0xff));
        }
        return stringCode.toString();
    }

    /**
     * Functie care genereaza un string random de lungime data prin concatenari random repetate de litere si cifre din alfabet
     * @param length Parametrul reprezinta lungimea pentru stringul ce urmeaza sa fie generat
     * @return
     */
    public String generateRandomString(int length) {
        String randomString = "";

        final char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890".toCharArray();
        final SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            randomString = randomString + chars[random.nextInt(chars.length)];
        }

        return randomString;
    }

    /**
     * Functia cripteaza parola preluata de la utilizator folosind un salt  si algoritmul PBKDF2
     * @param password Parametrul reprezinta parola in plain-text
     * @param salt Parametrul reprezinta o multime de bytes random ce va fi adaugata la parola care urmeaza sa fie hash-uita
     * @return Returneaza un string cu parola hash-uita
     */
    public  String encrypt(String password, byte[] salt) {
        byte[] hashedPassword;
        byte[] err = new byte[1];
        err[0] = 0;

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPassword = factory.generateSecret(spec).getEncoded();
            return new String(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return "0";
    }
}
