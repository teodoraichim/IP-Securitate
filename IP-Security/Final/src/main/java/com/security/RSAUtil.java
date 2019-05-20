package com.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.StringReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    public PublicKey publicKey;
    public PrivateKey privateKey;

    public static byte[] fromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    public static String toHexString(byte[] fieldData) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldData.length; i++) {
            int v = (fieldData[i] & 0xFF);
            if (v <= 0xF) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

        private final static String PUBLIC_KEY=
                "-----BEGIN PUBLIC KEY-----\n"
                        +"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd8I5SyQR2S5K+K3umU2LNRCGDddbjx9UApfxF\n" +
                        "VXPvQRUfANteA8je4FHJyX3aE8qu30ODlX9ESPkZcsD6jRzsGH6/3NMnw70CyDEsQeK00HfNKb6J\n" +
                        "zF6TLw9bZwcbb03DUxLeLfRZhlPjfbcbqxIp5ePmxsExDqm/JLSW7RSrDQIDAQAB"
                        + "-----END PUBLIC KEY-----";

    private final static String PRIVATE_KEY =
            "-----BEGIN PRIVATE KEY-----\n" +
                    "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3wjlLJBHZLkr4re6ZTYs1EIYN1\n" +
                    "1uPH1QCl/EVVc+9BFR8A214DyN7gUcnJfdoTyq7fQ4OVf0RI+RlywPqNHOwYfr/c0yfDvQLIMSxB\n" +
                    "4rTQd80pvonMXpMvD1tnBxtvTcNTEt4t9FmGU+N9txurEinl4+bGwTEOqb8ktJbtFKsNAgMBAAEC\n" +
                    "gYAvlEtb1yDX/jZLwc+zMIWgLrkl4DcHUajyVA6mWHLfjayuNVTggqVdtxgMgP2/15yEh8/P8RZw\n" +
                    "3MbmCrLmB+LBJrEynHwTjYoU5oMxSWap2Ft0cD6e9StY2/Xfru7ZfQbGqmXt4uXKDj/Z60Ut/j/P\n" +
                    "xjL3FMbvrNYYnKL56WyNIQJBAOmJ/ALNIKPO/URDLH9YxS8sDmTvZ/bfMLv26MWx6vvKlGfsBEmI\n" +
                    "dN9/BPwukw71EACZy8y68T/qV35y37uvmNkCQQCtITlBbGYY5MVIsmuH4OMT/qX3sHn4h4pKwoJ3\n" +
                    "B1uu/KVGjwjE03VLOTqHTV0P9cKXXQvFKMYD3KbaKtx+I2NVAkBA5654gNX/cLmGzTyCsAMtVwsx\n" +
                    "FjBItkLvGnIa75WlIOoZ78hJP3lr2/tgsvkAAS/PWu7/GT2PS+vBIt/3Y5gRAkAbjZQNI3FEOPRk\n" +
                    "03BlmZbKEdbcCov+z6NkrnwQ8WzX9oFfL1hX6sdaB2uXKOrMpXIooLhE0m5hIvay9etNb9hZAkEA\n" +
                    "5T9q+ufBlbH30Sh7gbt7MJTFROC7Jves7yGsyvxTh+l9DxdvitU197JLD245KtfKUUpcKQ17vefj\n" +
                    "W1qR1vRD9g=="
                    + "-----END PRIVATE KEY-----";

    public void getPrivateKey() throws Exception {
        // Read in the key into a String
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(PRIVATE_KEY));
        String line;
        while ((line = rdr.readLine()) != null) {
            pkcs8Lines.append(line);
        }

        // Remove the "BEGIN" and "END" lines, as well as any whitespace

        String pkcs8Pem = pkcs8Lines.toString();
        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");
        System.out.println(pkcs8Pem);
        // Base64 decode the result

        byte[] pkcs8EncodedBytes = Base64.getMimeDecoder().decode(pkcs8Pem);

        // extract the private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PrivateKey privKey = kf.generatePrivate(keySpec);
        System.out.println(privKey);
        privateKey = privKey;
    }

    public void getPublicKey() throws Exception {
        // Read in the key into a String
        StringBuilder pkcs8Lines = new StringBuilder();
        BufferedReader rdr = new BufferedReader(new StringReader(PUBLIC_KEY));
        String line;
        while ((line = rdr.readLine()) != null) {
            pkcs8Lines.append(line);
        }

        // Remove the "BEGIN" and "END" lines, as well as any whitespace

        String pkcs8Pem = pkcs8Lines.toString();
        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PUBLIC KEY-----", "");
        pkcs8Pem = pkcs8Pem.replace("-----END PUBLIC KEY-----", "");
        pkcs8Pem = pkcs8Pem.replaceAll("\\s+", "");
        System.out.println(pkcs8Pem);
        // Base64 decode the result

        byte[] pkcs8EncodedBytes = Base64.getMimeDecoder().decode(pkcs8Pem);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PublicKey pubKey = kf.generatePublic(keySpec);
        System.out.println(pubKey);
        publicKey=pubKey;
    }

    public byte[] encrypt(String data) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        return cipher.doFinal(data.getBytes());
    }

    public String decrypt(byte[] cipherTextArray) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA");
        //setPrivateKey();
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);

        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);

        return new String(decryptedTextArray);
    }

    public void generate() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();
        String publicKeyString = Base64.getMimeEncoder().encodeToString(pub.getEncoded());
        String privateKeyString = Base64.getMimeEncoder().encodeToString(pvt.getEncoded());

        System.out.println("Public:" + publicKeyString);
        System.out.println("Private:" + privateKeyString);
    }

    public static void main(String[] args) {
        String text = "Hi man";

//        byte[] cipherTextArray = RSAUtil.encrypt(plainText, publicKey);
//        String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
        RSAUtil r = new RSAUtil();
        // Decryption
        String decryptedText = null;
        try {
//            r.generate();
            r.getPrivateKey();
            r.getPublicKey();
            String encryptedText=toHexString(r.encrypt(text));
            System.out.println("Encrypted Text : " + encryptedText);

            decryptedText = r.decrypt(fromHexString(encryptedText));
            System.out.println("DeCrypted Text : " + decryptedText);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}