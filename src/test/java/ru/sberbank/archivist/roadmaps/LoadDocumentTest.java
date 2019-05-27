package ru.sberbank.archivist.roadmaps;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import ru.sberbank.archivist.persistense.BloomFilter;
import ru.sberbank.archivist.persistense.Index;

import java.util.UUID;

import static org.junit.Assert.*;

public class LoadDocumentTest {
    Index index = new Index();
    String privateKeySeed;
    private String pdf6 = "Plant_Wikipedia.pdf";

    @Test
    public void testUuidGeneration() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println("uuid = " + uuid);
        assertEquals(32, uuid.length());
    }

    @Test
    public void testAct() {
        LoadDocument loadDocument = new LoadDocument(index);
//        LoadDocument loadDocument = new LoadDocument();
        privateKeySeed = loadDocument.act(pdf6);
        System.out.println("index.keys = " + index.keySet());
        System.out.println("index.pdf6" + index.get(pdf6));
    }

    @Test
    public void testFind() {
        testAct();
        FindDocument findDocument = new FindDocument(index);
        boolean contains = findDocument.act(pdf6, privateKeySeed, "tb01558");
        System.out.println("contains = " + contains);
    }
}