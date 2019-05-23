package ru.sberbank.archivist.persistense;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class BloomFilter extends HashMap<String, Boolean> {
}
