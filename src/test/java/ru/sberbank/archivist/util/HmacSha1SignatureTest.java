package ru.sberbank.archivist.util;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import static org.junit.Assert.*;

public class HmacSha1SignatureTest {

    @Test
    public void calculateRFC2104HMAC() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        String hmac = HmacSha1Signature.calculateRFC2104HMAC("data", "key");
        String hmacCopy = HmacSha1Signature.calculateRFC2104HMAC("data", "key");
        String hmacWrongKey = HmacSha1Signature.calculateRFC2104HMAC("data", "bad_key");
        String hmacWrongData = HmacSha1Signature.calculateRFC2104HMAC("bad_data", "key");

        System.out.println("hmac= \t\t\t" + hmac);
        System.out.println("hmacCopy= \t\t" + hmacCopy);
        System.out.println("hmacWrongKey= \t" + hmacWrongKey);
        System.out.println("hmacWrongData= \t" + hmacWrongData);

        assertEquals(hmac, hmacCopy);
        assertEquals("104152c5bfdca07bc633eebd46199f0255c9f49d", hmac);
        assertNotEquals(hmacWrongKey, hmac);
        assertNotEquals(hmacWrongData, hmac);
    }
}