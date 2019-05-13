package algorithms;

import algorithms.common.Point;
import algorithms.kmeans.KMeansEpsilon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import serialize.AllegroItem;
import serialize.PriceSerializable;
import service.AllegroItemPointConverterImpl;
import service.ClusteringServiceImpl;
import words.WordDistance;

import java.util.*;

import static soap.SoapClientImplTest.getAllegroItemsFor;

/**
 * Created on 13-Sep-17.
 */
public class KMeansEpsilonTest {

    private String[] SENTENCES = new String[]{
            "test sentences",
            "test sentencess",
            "test testing",
            "sentences test",
            "test sentences try"
    };

    private Map<WordDistance, Double> algorithmWeightMap = new HashMap<>();
    private List<AllegroItem> allegroItems;
    private final String searchValue = "rozowy konik";

    @Before
    public void setUp() throws Exception {
        allegroItems = getAllegroItemsFor(searchValue);

        Assert.assertTrue(allegroItems.size() > 0);
    }

    @Test
    public void calculate() throws Exception {


    }


    @Test
    public void mockedItems() {
        List<AllegroItem> allegroItems = new ArrayList<>();
        List<PriceSerializable> prices = new ArrayList<>();
        prices.add(new PriceSerializable("type", 10));

        for (int i = 0; i < SENTENCES.length; i++) {
            allegroItems.add(new AllegroItem(SENTENCES[i], 1, 1, "asd", "asd",
                    new Date(), "https://redir-img10.allegroimg.com/photos/64x48/70/23/62/29/7023622969", prices));
        }

        KMeansEpsilon kMeansEpsilon = new KMeansEpsilon(70);

        final AlgorithmResult clusteringResult =
                new ClusteringServiceImpl().createFolders(kMeansEpsilon, allegroItems, algorithmWeightMap);
        final List<Folder> clusters = clusteringResult.getFolders();

        System.out.println("##########################");
        for (Folder folder : clusters) {
            folder.plotCluster();
        }

    }

    @Test
    public void getItemsByKeyWord() {
        final long start = System.currentTimeMillis();

        KMeansEpsilon kMeans = new KMeansEpsilon(20);
        final AlgorithmResult clusteringResult =
                new ClusteringServiceImpl().createFolders(kMeans, allegroItems, algorithmWeightMap);
        final List<Folder> folders = clusteringResult.getFolders();

        System.out.println(System.currentTimeMillis() - start + "ms for the algorithm");

        Assert.assertTrue(folders.size() > 0);
    }

    @Test
    public void serializedItems() throws Exception {
        List<AllegroItem> allegroItems = getAllegroItemsFor("golarka elektryczna");
        System.out.println(allegroItems.size() + " items in the structure");

        List<Point> points = new AllegroItemPointConverterImpl().points(allegroItems);

        KMeansEpsilon kMeansEpsilon = new KMeansEpsilon(0);

        AlgorithmResult clustersResult = new ClusteringServiceImpl().createFolders(kMeansEpsilon, allegroItems, algorithmWeightMap);
        List<Folder> folders = clustersResult.getFolders();


    }
}
