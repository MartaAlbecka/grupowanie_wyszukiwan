package words;

/**
 * Created by Marta on 2017-09-25
 **/
public class HammingDistanceImpl implements WordDistance {

    @Override
    public int wordsSimilarity(String sentence1, String sentence2) {
        int distance=0;
        int longer = Math.max(sentence1.length(), sentence2.length());
        int shorter = Math.min(sentence1.length(), sentence2.length());
        for(int i=0; i<shorter; i++){
            if(sentence1.charAt(i) != sentence2.charAt(i)){
                distance++;
            }
        }
        distance = distance + longer - shorter;
        return (100*(longer-distance)/longer);
    }
}
