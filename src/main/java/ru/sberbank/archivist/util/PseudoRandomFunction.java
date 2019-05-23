package ru.sberbank.archivist.util;

import java.util.ArrayList;
import java.util.List;

public class PseudoRandomFunction {

    public static List<String> actAsPrf(List<String> key, String data) {
        final int length = key.size();
        List<String> output = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            output.add(i, HmacSha1Signature.calculateRFC2104HMAC(key.get(i), data));
        }

        return output;
    }
}
