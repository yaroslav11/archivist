package ru.sberbank.archivist.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class HmacSha1Signature {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public static String calculateRFC2104HMAC(String key, String data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            return toHexString(mac.doFinal(data.getBytes()));

        } catch (NoSuchAlgorithmException|InvalidKeyException e) {
            throw new RuntimeException("Cannot generate RFC2104HMAC");
        }
    }

}