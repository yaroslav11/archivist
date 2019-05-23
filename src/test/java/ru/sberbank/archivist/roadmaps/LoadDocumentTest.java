package ru.sberbank.archivist.roadmaps;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class LoadDocumentTest {
    @Test
    public void testUuidGeneration() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println("uuid = " + uuid);
        assertEquals(32, uuid.length());
    }

}