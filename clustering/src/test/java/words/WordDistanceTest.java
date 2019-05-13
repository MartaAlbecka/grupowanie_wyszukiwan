package words;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


/**
 * Created by Marta on 2017-09-25
 **/
public class WordDistanceTest {
    private String TITLE_LONG = "!!?_   tytul super,__, pierwszy !!! ,okazja ";
    private String TITLE_SHORT = "tytul pierwszy";
    private String SENTENCE1 = "test sentences";
    private String SENTENCE2 = "test sentencess";
    private String SENTENCE3 = "test testing";
    private String SENTENCE4 = "sentences test";
    private String SENTENCE5 = "test sentences try";
    private String WORD1 = "flaw";
    private String WORD2 = "lawn";

    WordDistance wordsDistance = new LevenshteinDistanceImpl();
    WordDistance hammingDistance = new HammingDistanceImpl();
    WordDistance sentenceDistance = new SentenceDistance();

    @Test
    public void clearTitle() throws Exception {
        MatcherAssert.assertThat(WordDistance.clearTitle(TITLE_LONG), equalTo(TITLE_SHORT));
    }

    @Test
    public void wordsSimilarityLevenshtein() throws Exception {
        MatcherAssert.assertThat(wordsDistance.wordsSimilarity(SENTENCE1, SENTENCE1), equalTo(100));
        MatcherAssert.assertThat(wordsDistance.wordsSimilarity(SENTENCE1, SENTENCE2), equalTo(93));
        MatcherAssert.assertThat(wordsDistance.wordsSimilarity(SENTENCE1, SENTENCE3), equalTo(57));
        MatcherAssert.assertThat(wordsDistance.wordsSimilarity(SENTENCE1, SENTENCE4), equalTo(35));
        MatcherAssert.assertThat(wordsDistance.wordsSimilarity(WORD1, WORD2), equalTo(50));
        MatcherAssert.assertThat(wordsDistance.wordsSimilarity(WORD1, WORD1), equalTo(100));
    }

    @Test
    public void wordsSimilarityHamming() throws Exception {
        MatcherAssert.assertThat(hammingDistance.wordsSimilarity(SENTENCE1, SENTENCE1), equalTo(100));
        MatcherAssert.assertThat(hammingDistance.wordsSimilarity(SENTENCE1, SENTENCE2), equalTo(93));
        MatcherAssert.assertThat(hammingDistance.wordsSimilarity(SENTENCE1, SENTENCE3), equalTo(57));
        MatcherAssert.assertThat(hammingDistance.wordsSimilarity(SENTENCE1, SENTENCE4), equalTo(14));
        MatcherAssert.assertThat(hammingDistance.wordsSimilarity(WORD1, WORD2), equalTo(0));
        MatcherAssert.assertThat(hammingDistance.wordsSimilarity(WORD1, WORD1), equalTo(100));
    }

    @Test
    public void sentenceSimilarity() throws Exception {
        MatcherAssert.assertThat(sentenceDistance.wordsSimilarity(SENTENCE1, SENTENCE1), equalTo(100));
        MatcherAssert.assertThat(sentenceDistance.wordsSimilarity(SENTENCE1, SENTENCE2), equalTo(50));
        MatcherAssert.assertThat(sentenceDistance.wordsSimilarity(SENTENCE1, SENTENCE3), equalTo(50));
        MatcherAssert.assertThat(sentenceDistance.wordsSimilarity(SENTENCE1, SENTENCE4), equalTo(100));
        MatcherAssert.assertThat(sentenceDistance.wordsSimilarity(SENTENCE1, SENTENCE5), equalTo(66));
        MatcherAssert.assertThat(sentenceDistance.wordsSimilarity(SENTENCE1, SENTENCE1), equalTo(100));
    }

}