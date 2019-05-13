package algorithms.kmeans;

import algorithms.common.Point;
import attributes.Attribute;
import attributes.StringAttribute;
import org.junit.Before;
import org.junit.Test;
import words.LevenshteinDistanceImpl;
import words.SentenceDistance;
import words.WordDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 13-Sep-17.
 */
public class KMeansImplTest {

    private String[] SENTENCES = new String[]{
            "test sentences",
            "test sentencess",
            "test testing",
            "sentences test",
            "test sentences try"
    };

    List<Point> points = new ArrayList<>();
    Map<WordDistance, Double> algorithmWeightMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        algorithmWeightMap.put(new SentenceDistance(), 1.);
        algorithmWeightMap.put(new LevenshteinDistanceImpl(), 1.);

        for (int i = 0; i < SENTENCES.length; i++) {
            StringAttribute attribute = new StringAttribute("title", SENTENCES[i], algorithmWeightMap);
            final ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(attribute);
            points.add(new Point(attributes));
        }
    }

    @Test
    public void calculate() throws Exception {
        KMeansImpl kMeansImpl = new KMeansImpl();
        kMeansImpl.createClusters(points);
    }

}
