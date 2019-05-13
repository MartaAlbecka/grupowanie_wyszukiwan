package performance;

import algorithms.common.ClusteringAlgorithm;
import algorithms.kmeans.KMeansEpsilon;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import serialize.AllegroItem;
import service.ClusteringServiceImpl;
import soap.SoapClientImplTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created on 06-Dec-17.
 */
public class PerformanceTest {

    final static String searchValue = "koń na biegunach";
    static String logFile = "../performanceLog.log";
    static List<AllegroItem> allegroItems;
    static BufferedWriter writer;

    @Before
    public void prepareData() throws Exception {
        List<AllegroItem> items = SoapClientImplTest.getAllegroItemsFor(searchValue);

        allegroItems = shuffle(items);

        writer = new BufferedWriter(new FileWriter(logFile));
    }

    private List<AllegroItem> shuffle(List<AllegroItem> allegroItems) {
        Random random = new Random();
        List<AllegroItem> copy = new ArrayList<>(allegroItems);

        List<AllegroItem> ret = new ArrayList<>(allegroItems.size());
        while (copy.size() > 0) {
            int index = random.nextInt(Integer.MAX_VALUE) % copy.size();
            ret.add(copy.get(index));
            copy.remove(index);
        }
        return ret;
    }

    @Test
    public void performance() throws Exception {
        final int step = 100;
        int maxSize = 1700;

        for (int i = 1; i <= maxSize / step; i++) {
            writer.write("\t" + i * step);
        }

        for (int i = 0; i <= 10; i++) {
            int epsilon = 100 - i * 10;
            ClusteringAlgorithm algorithm = new KMeansEpsilon(epsilon);
            writer.write("\n" + (100 - epsilon));
            for (int j = 1; j < allegroItems.size() / step && j <= maxSize / step; j++) {
                int itemsSize = j * step;
                List<AllegroItem> itemsToBeGrouped = allegroItems.subList(0, itemsSize);

                long start = System.currentTimeMillis();
                new ClusteringServiceImpl().createFolders(algorithm, itemsToBeGrouped);
                long time = System.currentTimeMillis() - start;
                System.out.println("CLUSTERING: " + (100 - epsilon) + "% " + itemsSize + " " + time + "\n\n");
                writer.write("\t" + time);
                writer.flush();
            }
        }
    }

    @Test
    public void performanceForEpsilon() throws Exception {
        int epsilon = 70;
        final int step = 100;
        int maxSize = 1700;

        ClusteringAlgorithm algorithm = new KMeansEpsilon(epsilon);
        writer.write((100 - epsilon) + "%\n");
        for (int j = 1; j < allegroItems.size() / step && j <= maxSize / step; j++) {
            int itemsSize = j * step;
            List<AllegroItem> itemsToBeGrouped = allegroItems.subList(0, itemsSize);

            long start = System.currentTimeMillis();
            new ClusteringServiceImpl().createFolders(algorithm, itemsToBeGrouped);
            long time = System.currentTimeMillis() - start;
            System.out.println("CLUSTERING: " + (100 - epsilon) + "% " + itemsSize + " " + time + "\n\n");
            writer.write(itemsSize + "\t" + time + "\n");
            writer.flush();
        }
    }

    @After
    public void finish() throws IOException {
        writer.close();
    }
}
