package ru.sberbank.archivist.util;

import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class KeyGeneratorTest {

    @Test
    public void generateVectorKey() {
        List<String> vectorKey = KeyGenerator.generateVectorKey("seed");

        System.out.println("vectorKey = " + vectorKey);

        assertEquals(KeyGenerator.KEY_VECTOR_LEN, vectorKey.size());

        assertEquals(vectorKey.size(), vectorKey.stream()
                .filter(Objects::nonNull)
                .distinct()
                .count());
    }
}