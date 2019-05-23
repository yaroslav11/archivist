package ru.sberbank.archivist.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class PseudoRandomFunctionTest {

    @Test
    public void actAsPrf_testSimilarity() {
        List<String> vectorKey = Arrays.asList("1", "1", "1" );
        assertEquals(KeyGenerator.KEY_VECTOR_LEN, vectorKey.size());

        String wordFromDocument = "test_word";

        List<String> output = PseudoRandomFunction.actAsPrf(vectorKey, wordFromDocument);

        assertEquals(vectorKey.size(), output.size());

        assertEquals(1, output.stream()
                .filter(Objects::nonNull)
                .distinct()
                .count());
    }

    @Test
    public void actAsPrf_testDiference() {
        List<String> vectorKey = Arrays.asList("1", "2", "3" );
        assertEquals(KeyGenerator.KEY_VECTOR_LEN, vectorKey.size());

        String wordFromDocument = "test_word";

        List<String> output = PseudoRandomFunction.actAsPrf(vectorKey, wordFromDocument);

        assertEquals(vectorKey.size(), output.size());

        assertEquals(output.size(), output.stream()
                .filter(Objects::nonNull)
                .distinct()
                .count());
    }
}