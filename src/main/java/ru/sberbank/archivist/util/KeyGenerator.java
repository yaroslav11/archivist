package ru.sberbank.archivist.util;

import java.util.ArrayList;
import java.util.List;

public class KeyGenerator {
    public static final int KEY_VECTOR_LEN = 3;

    public static List<String> generateVectorKey(String seed) {

        List<String> keyVector = new ArrayList<>(KEY_VECTOR_LEN);

        keyVector.add(HmacSha1Signature.calculateRFC2104HMAC(seed, seed));
        for (int i = 1; i < KEY_VECTOR_LEN; i++) {
            keyVector.add(i, HmacSha1Signature.calculateRFC2104HMAC(seed, keyVector.get(i - 1)));
        }

        return keyVector;
    }
}
