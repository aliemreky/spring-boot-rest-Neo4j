package com.volaid.volaid.util;

public class ServiceUtils {

    public static String stringTransformForEncryptionOfSecretKey(String s, int i) {
        char[] chars = s.toCharArray();
        for (int j = 0; j < chars.length; j++)
            chars[j] = (char) (chars[j] ^ i);
        return String.valueOf(chars);
    }

}
