package ru.sberbank.archivist.persistense;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DocumentsTable {
    private Map<Integer, String> documents = new HashMap<>();

}
