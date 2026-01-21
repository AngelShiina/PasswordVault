package de.angelshiina.passwords;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

    public static String generate(int len) {
        SecureRandom r = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<len;i++)
            sb.append(CHARS.charAt(r.nextInt(CHARS.length())));
        return sb.toString();
    }
}