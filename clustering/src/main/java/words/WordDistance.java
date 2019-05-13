package words;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marta on 2017-09-25
 **/
public interface WordDistance {

    List<String> unnecessaryWords =
            new ArrayList<>(Arrays.asList("nowy", "nowa", "nowe", "używany", "używana", "używane",
                    "super", "wyprzedaż", "szybko", "hit", "niska", "cena", "tanio", "gratis", "okazja", "uwaga", "promocja"));

    /**
     * Method counts the distance between sentences using specific algorithms and returns the similarity considered as the distance in the comparison to the longest sentence in %
     *
     * @param sentence1 first sentence to be compared
     * @param sentence2 second sentence to be compared
     * @return similarity between words in %
     */
    int wordsSimilarity(String sentence1, String sentence2);

    /**
     * Method remove unnecessary words and punctuation marks from the sentence
     *
     * @param sentence the title of the auction to remove the words from
     * @return the new title containing only important words
     */
    static String clearTitle(String sentence) {
        List<String> punctuationMarks = new ArrayList<>(Arrays.asList(".", ",", "!", "?", "-", "_"));

        String title = sentence.toLowerCase();
        for (String word : unnecessaryWords) {
            title = title.replace(word, " ");
        }
        for (String mark : punctuationMarks) {
            title = title.replace(mark, " ");
        }
        final String[] split = title.split("\\s+");
        return String.join(" ", split).trim();
    }
}
