package ru.sberbank.archivist.roadmaps;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.archivist.persistense.BloomFilter;
import ru.sberbank.archivist.persistense.Index;
import ru.sberbank.archivist.reader.PdfReader;
import ru.sberbank.archivist.reader.Reader;
import ru.sberbank.archivist.util.KeyGenerator;
import ru.sberbank.archivist.util.PseudoRandomFunction;
import ru.sberbank.archivist.util.StringUtil;

import java.util.*;

/**
 * This class joins the components to act as the whole scheme to process one document
 * 1) load document
 * 1.1) get id
 * 1.2) get words W
 * <p>
 * 2) generate key
 * <p>
 * 3) get 1st trapdor Tr1(K, w)
 * <p>
 * 4) get 2nd trapdor Tr2(Tr1, id)
 * <p>
 * 5) add Tr2 to BloomFilter BF
 * <p>
 * 6) add pair (id, BF) to Index
 */
public class LoadDocument {

    private Reader reader = new PdfReader();


    private String documentUuid;
    private String documentName;
    private String privateKeySeed;
    private Set<String> allWordsSet;
    private List<String> vectorKey;

//    @Autowired
    public Index index;
    private BloomFilter bloomFilter;

    @Autowired
    public LoadDocument(Index index) {
        this.index = index;
    }

    public String act(String documentName) {
        this.documentName = documentName;

        createDocumentUuid();
        createPrivateKeySeed();
        createAllWordsSet();
        createVectorKey();
        createBloomFilter();
        for (String word: allWordsSet) {
            List<String> trapdoor1 = createTrapdoor1(word);
            List<String> trapdoor2 = createTrapdoor2(trapdoor1);
            updateBloomFilter(trapdoor2);
            System.out.println(String.format("keyword for %s = %s", documentName, word));
            break; // to act for single key word
        }
        updateIndex();

        return privateKeySeed;
    }


    private void createDocumentUuid() {
//        documentUuid = UUID.randomUUID().toString().replaceAll("-", "");
        documentUuid = documentName;
    }

    private void createPrivateKeySeed() {
        privateKeySeed = UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void createAllWordsSet() {
        String rawText = reader.getTextFromDocument(documentName);
        String cleanText = StringUtil.filterStoppingWords(rawText);
        List<String> words = StringUtil.getWordsFromText(cleanText);
        Map<String, Integer> wordsWithFrequency = StringUtil.mapStringList(words);
        allWordsSet = wordsWithFrequency.keySet();
    }

    private void createVectorKey() {
        vectorKey = KeyGenerator.generateVectorKey(privateKeySeed);
    }

    private List<String> createTrapdoor1(String word) {
        return PseudoRandomFunction.actAsPrf(vectorKey, word);
    }

    private List<String> createTrapdoor2(List<String> trapdor1) {
        return PseudoRandomFunction.actAsPrf(trapdor1, documentUuid);
    }

    private void createBloomFilter() {
        bloomFilter = new BloomFilter();
    }

    private void updateBloomFilter(List<String> elementsToBeInsertedToBF) {
        for (String element: elementsToBeInsertedToBF) {
            bloomFilter.put(element, true);
        }
    }

    private void updateIndex() {
        index.put(documentUuid, bloomFilter);
    }
}
