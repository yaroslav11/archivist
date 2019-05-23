package ru.sberbank.archivist.util;

import com.uttesh.exude.ExudeData;

import java.util.ArrayList;
import java.util.List;
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
}
