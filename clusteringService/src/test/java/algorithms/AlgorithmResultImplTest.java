package algorithms;

import algorithms.kmeans.KMeansEpsilon;
import algorithms.kmeans.KMeansImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import serialize.*;
import service.ClusteringServiceImpl;
import soap.SoapClientImpl;
import words.LevenshteinDistanceImpl;
import words.SentenceDistance;
import words.WordDistance;

import java.util.*;

/**
 * Created by Marta on 2017-11-30
 **/
public class AlgorithmResultImplTest {
    private List<AllegroItem> allegroItems;
    private Map<WordDistance, Double> algorithmWeightMap = new HashMap<>();
    private final String SEARCHVALUE = "fc barcelona 2017";
    String filePath = "../serialize/serializeClusters/%s.ser";

    private String[] SENTENCES = new String[]{
            "test sentences",
            "test sentencess",
            "test testing",
            "sentences test",
            "test sentences try"
    };

    @Before
    public void setUp() throws Exception {
        Serializer serializer = new SerializerImpl();
        serializer.setFilePath(filePath);
        allegroItems = serializer.deserialize(SEARCHVALUE);
        algorithmWeightMap.put(new SentenceDistance(), 1.);
        algorithmWeightMap.put(new LevenshteinDistanceImpl(), 1.);
    }

    @Test
    public void serializeClusters() throws Exception {
        KMeansEpsilon kMeans = new KMeansEpsilon(5);
        final AlgorithmResult clusteringResult =
                new ClusteringServiceImpl().createFolders(kMeans, allegroItems, algorithmWeightMap);
        List<AllegroFolderItem> itemsClusterMapping = clusteringResult.getItemsClusterMapping();
        clusteringResult.serializeFolders(SEARCHVALUE);
        List<AllegroFolderItem> itemsClusterMappingDeserialized = AlgorithmResult.deserializeFolders(SEARCHVALUE);
        boolean equals = true;
        for (int i = 0; i < itemsClusterMapping.size(); i++) {
            if (!itemsClusterMapping.get(i).equals(itemsClusterMappingDeserialized.get(i))) {
                equals = false;
                break;
            }
        }
        Assert.assertTrue(equals);
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

        KMeansImpl kMeans = new KMeansImpl();

        final AlgorithmResult clusteringResult =
                new ClusteringServiceImpl().createFolders(kMeans, allegroItems, algorithmWeightMap);
        final List<Folder> clusters = clusteringResult.getFolders();

        System.out.println("##########################");
        for (Folder folder : clusters) {
            folder.plotCluster();
        }

    }

    @Test
    public void getItemsByKeyWord() {
        final long start = System.currentTimeMillis();
        SoapClientImpl soapClient = new SoapClientImpl();
        final List<AllegroItem> allegroItems = soapClient.getItemsByKeyWord("golarka elektryczna");

        Assert.assertTrue(allegroItems.size() > 0);

        final long getItemsTime = System.currentTimeMillis();
        System.out.println(allegroItems.size() + " items");
        System.out.println(getItemsTime - start + " ms to get product list");

        KMeansImpl kMeans = new KMeansImpl();

        final AlgorithmResult clusteringResult =
                new ClusteringServiceImpl().createFolders(kMeans, allegroItems, algorithmWeightMap);
        final List<Folder> folders = clusteringResult.getFolders();

        Assert.assertTrue(folders.size() > 0);

        System.out.println(System.currentTimeMillis() - getItemsTime + " ms for the clustering");
    }


}