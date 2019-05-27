package ru.sberbank.archivist.roadmaps;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sberbank.archivist.persistense.BloomFilter;
import ru.sberbank.archivist.persistense.Index;
import ru.sberbank.archivist.util.KeyGenerator;
import ru.sberbank.archivist.util.PseudoRandomFunction;

import java.util.List;
import java.util.Set;

public class FindDocument {

    private String documentUuid;
    private String documentName;
    private String privateKeySeed;
//    private Set<String> allWordsSet;
    private List<String> vectorKey;

    public Index index;
    private BloomFilter bloomFilter;

    @Autowired
    public FindDocument(Index index) {
        this.index = index;
    }

    /**
     *
     * @param documentUuid
     * @param privateKeySeed
     * @param word
     * @return true if document contains the word
     */
    public boolean act(String documentUuid, String privateKeySeed, String word) {

        this.documentUuid = documentUuid;

        this.bloomFilter = index.get(documentUuid);
        if (bloomFilter == null) return false;

        this.privateKeySeed = privateKeySeed;
        createVectorKey();
        List<String> trapdoor1 = createTrapdoor1(word);
        List<String> trapdoor2 = createTrapdoor2(trapdoor1);

        return checkBloomFilter(trapdoor2);
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

    private boolean checkBloomFilter(List<String> elementsToBeFoundInBF) {
        for (String element: elementsToBeFoundInBF) {
            if (bloomFilter.get(element) != true) {
                return false;
            }
        }
        return true;
    }

}
