package com.security;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HelpFunctions {
    public String getAuthCode() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            SecureRandom random = new SecureRandom();
            byte[] code = new byte[16];
            random.nextBytes(code);
            md.update(code);
            byte[] digest = md.digest();
            return byteToString(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "0";
    }
    public  String byteToString(byte[] byteCode) {
        StringBuffer stringCode = new StringBuffer();
        for (byte b : byteCode) {
            stringCode.append(String.format("%02x", b & 0xff));
        }
        return stringCode.toString();
    }
    public  String encrypt(String password, byte[] salt) {
        /** Generating a salt **/
        byte[] hashedPassword;
        byte[] err = new byte[1];
        err[0] = 0;

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPassword = factory.generateSecret(spec).getEncoded();
            return byteToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return "0";
    }
}
