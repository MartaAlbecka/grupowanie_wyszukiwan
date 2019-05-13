package words;

/**
 * Created by Marta on 2017-09-21
 **/
public class LevenshteinDistanceImpl implements WordDistance {

    public int wordsSimilarity(String sentence1, String sentence2) {
        int[][] dist = new int[sentence1.length() + 1][sentence2.length() + 1];

        for (int i = 0; i <= sentence1.length(); i++) {
            dist[i][0] = i;
        }

        for (int i = 0; i <= sentence2.length(); i++) {
            dist[0][i] = i;
        }

        for (int i = 1; i <= sentence1.length(); i++) {
            for (int j = 1; j <= sentence2.length(); j++) {
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1),
                        dist[i - 1][j - 1] + ((sentence1.charAt(i - 1) == sentence2.charAt(j - 1)) ? 0 : 1));
            }
        }

        String longerSentence = sentence1.length() > sentence2.length() ? sentence1 : sentence2;

        return 100 * (longerSentence.length() - dist[sentence1.length()][sentence2.length()]) / longerSentence.length();
    }
}
