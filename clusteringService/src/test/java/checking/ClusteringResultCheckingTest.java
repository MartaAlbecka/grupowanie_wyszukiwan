package checking;

import algorithms.AlgorithmResult;
import algorithms.kmeans.KMeansEpsilon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import serialize.AllegroItem;
import serialize.Serializer;
import serialize.SerializerImpl;
import service.ClusteringService;
import service.ClusteringServiceImpl;
import soap.SoapClientImpl;
import words.LevenshteinDistanceImpl;
import words.SentenceDistance;
import words.WordDistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Marta on 2017-11-18
 **/
public class ClusteringResultCheckingTest {
    private static final Logger LOG = Logger.getLogger(ClusteringResultCheckingTest.class.getName());

    private Map<WordDistance, Double> algorithmWeightMap = new HashMap<>();
    private String SEARCHVALUE1 = "fc barcelona 2017";
    private String SEARCHVALUE2 = "golarka elektryczna męska";
    private String SEARCHVALUE3 = "Camp Nou";
    private String SEARCHVALUE4 = "poduszka litera";

    private List<AllegroItem> allegroItems1;
    private List<AllegroItem> allegroItems2;
    private List<AllegroItem> allegroItems3;
    private List<AllegroItem> allegroItems4;

    private Serializer serializer;
    private KMeansEpsilon kMeans;
    private String filePath = "../serialize/serializeAuctions/%s.ser";
    private ClusteringService clusteringService = new ClusteringServiceImpl();

    @Before
    public void setUp() throws Exception {
        kMeans = new KMeansEpsilon(5);
        algorithmWeightMap.put(new SentenceDistance(), 1.);
        algorithmWeightMap.put(new LevenshteinDistanceImpl(), 1.);

        Serializer serializer = new SerializerImpl();
        serializer.setFilePath(filePath);
        allegroItems1 = serializer.deserialize(SEARCHVALUE1);
        allegroItems2 = serializer.deserialize(SEARCHVALUE2);
        allegroItems3 = serializer.deserialize(SEARCHVALUE3);
        allegroItems4 = serializer.deserialize(SEARCHVALUE4);


/*
        List<String> list = new ArrayList<>();
        list.add(SEARCHVALUE2);
        list.add(SEARCHVALUE1);
        list.add(SEARCHVALUE3);
        list.add(SEARCHVALUE4);
        prepareDataSets(list);

        AlgorithmResult algorithmResult = clusteringService.createFolders(kMeans, allegroItems1, algorithmWeightMap);
        algorithmResult.serializeFolders(SEARCHVALUE1);
        AlgorithmResult algorithmResult2 = clusteringService.createFolders(kMeans, allegroItems2, algorithmWeightMap);
        algorithmResult2.serializeFolders(SEARCHVALUE2);
        AlgorithmResult algorithmResult3 = clusteringService.createFolders(kMeans, allegroItems3, algorithmWeightMap);
        algorithmResult3.serializeFolders(SEARCHVALUE3);
        AlgorithmResult algorithmResult4 = clusteringService.createFolders(kMeans, allegroItems4, algorithmWeightMap);
        algorithmResult4.serializeFolders(SEARCHVALUE4);
*/


    }


    @Test
    public void compareClusteringResults() throws Exception {
        AlgorithmResult algorithmResult1 =
                clusteringService.createFolders(kMeans, allegroItems1, algorithmWeightMap);
        AlgorithmResult algorithmResult2 =
                clusteringService.createFolders(kMeans, allegroItems2, algorithmWeightMap);
        AlgorithmResult algorithmResult3 =
                clusteringService.createFolders(kMeans, allegroItems3, algorithmWeightMap);
        AlgorithmResult algorithmResult4 =
                clusteringService.createFolders(kMeans, allegroItems4, algorithmWeightMap);

        int differences1 = ClusteringResultChecking.compareClusters(algorithmResult1, SEARCHVALUE1);
        LOG.info("Number of differences for " + SEARCHVALUE1 + ": " + differences1);
        int differences2 = ClusteringResultChecking.compareClusters(algorithmResult2, SEARCHVALUE2);
        LOG.info("Number of differences for " + SEARCHVALUE2 + ": " + differences2);
        int differences3 = ClusteringResultChecking.compareClusters(algorithmResult3, SEARCHVALUE3);
        LOG.info("Number of differences for " + SEARCHVALUE3 + ": " + differences3);
        int differences4 = ClusteringResultChecking.compareClusters(algorithmResult4, SEARCHVALUE4);
        LOG.info("Number of differences for " + SEARCHVALUE4 + ": " + differences4);

        Assert.assertEquals(differences1, 0);
        Assert.assertEquals(differences2, 0);
        Assert.assertEquals(differences3, 0);
        Assert.assertEquals(differences4, 0);

    }

    public void prepareDataSets(List<String> values) {
        for (String searchvalue : values) {
            serializer = new SerializerImpl();
            serializer.setFilePath(filePath);
            SoapClientImpl soapClient = new SoapClientImpl();
            final List<AllegroItem> allegroItems = soapClient.getItemsByKeyWord(searchvalue);
            serializer.serialize(allegroItems, searchvalue);
        }
    }


}