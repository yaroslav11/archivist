package ru.sberbank.archivist.util;

import com.uttesh.exude.ExudeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    private static String PATTERN = "\\w{4,}";

    public static List<String> getWordsFromText(String text) {
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            words.add(text.substring(matcher.start(), matcher.end()));
        }
        return words;
    }

    public static String filterStoppingWords(String rawText) {
        try {
            return ExudeData.getInstance().filterStoppingsKeepDuplicates(rawText);
        } catch (Exception e) {
            throw new RuntimeException("Cannot remove prepositions and articles");
        }
    }

    public static Map<String, Integer> mapStringList(List<String> src) {
        Map<String, Integer> dst = new HashMap<>();
        for (String key : src) {
            dst.put(key, dst.containsKey(key) ? dst.get(key) + 1 : 1);
        }
        return dst;
    }
}
