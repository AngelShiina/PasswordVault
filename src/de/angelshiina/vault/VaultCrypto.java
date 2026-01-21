package de.angelshiina.vault;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

public class VaultCrypto {

    private static final int SALT = 16, IV = 12, ITER = 310_000;

    private static SecretKey key(char[] pw, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(pw, salt, ITER, 256);
        return new SecretKeySpec(
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(spec).getEncoded(), "AES");
    }

    public static byte[] encrypt(byte[] data, char[] pw) throws Exception {
        byte[] salt = SecureRandom.getInstanceStrong().generateSeed(SALT);
        byte[] iv = SecureRandom.getInstanceStrong().generateSeed(IV);
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.ENCRYPT_MODE, key(pw, salt), new GCMParameterSpec(128, iv));
        byte[] enc = c.doFinal(data);
        return concat(salt, iv, enc);
    }

    public static byte[] decrypt(byte[] data, char[] pw) throws Exception {
        byte[] salt = Arrays.copyOfRange(data,0,SALT);
        byte[] iv = Arrays.copyOfRange(data,SALT,SALT+IV);
        byte[] enc = Arrays.copyOfRange(data,SALT+IV,data.length);
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.DECRYPT_MODE, key(pw, salt), new GCMParameterSpec(128, iv));
        return c.doFinal(enc);
    }

    private static byte[] concat(byte[] a, byte[] b, byte[] c) {
        byte[] r = new byte[a.length+b.length+c.length];
        System.arraycopy(a,0,r,0,a.length);
        System.arraycopy(b,0,r,a.length,b.length);
        System.arraycopy(c,0,r,a.length+b.length,c.length);
        return r;
    }

}
