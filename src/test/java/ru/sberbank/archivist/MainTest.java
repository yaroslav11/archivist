package ru.sberbank.archivist;

import org.junit.Test;
import ru.sberbank.archivist.reader.PdfReader;
import ru.sberbank.archivist.reader.Reader;
import ru.sberbank.archivist.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainTest {

    private Reader reader = new PdfReader();

    //    private String pdf1 = "/Users/yaroslav_zhukov/Desktop/Multi-Key Searchable Encryption Revisited.pdf";
    //    private String pdf2 = "Multi-Key_Searchable_Encryption_Revisited.pdf";
    //    private String pdf3 = "ru_Invisible_hand_Wikipedia.pdf";
    private String pdf4 = "Invisible_hand_Wikipedia.pdf";
    private String pdf5 = "Danaus_melanippus_Wikipedia.pdf";
    private String pdf6 = "Plant_Wikipedia.pdf";


    @Test
    public void testPdf4(){
        this.main(pdf4);
    }

    @Test
    public void testPdf5(){
        this.main(pdf5);
    }

    @Test
    public void testPdf6(){
        this.main(pdf6);
    }


    public void main(String pdf) {
        String rawText = reader.getTextFromDocument(pdf);
        String cleanText = StringUtil.filterStoppingWords(rawText);
        List<String> words = StringUtil.getWordsFromText(cleanText);
        Map<String, Integer> wordsWithFrequency = mapStringList(words);
        System.out.println(wordsWithFrequency
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .skip(wordsWithFrequency.size() - 10)
                .collect(Collectors.toList())
        );
    }

    private static Map<String, Integer> mapStringList(List<String> src) {
        Map<String, Integer> dst = new HashMap<>();
        for (String key : src) {
            dst.put(key, dst.containsKey(key) ? dst.get(key) + 1 : 1);
        }
        return dst;
    }

}