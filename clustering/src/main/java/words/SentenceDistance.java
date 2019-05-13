package words;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marta on 2017-09-26
 **/
public class SentenceDistance implements WordDistance {

    public int wordsSimilarity(String sentence1, String sentence2) {
        List<String> s1 = Arrays.asList(sentence1.split("\\s"));
        List<String> s2 = Arrays.asList(sentence2.split("\\s"));

        List<String> longerSentence = s1.size() > s2.size() ? s1 : s2;
        List<String> shorterSentence = s1.size() > s2.size() ? s2 : s1;

        int similarity = 0;
        for (String word : longerSentence) {
            if (shorterSentence.contains(word)) {
                similarity++;
            }
        }
        return 100 * similarity / longerSentence.size();
    }
}
