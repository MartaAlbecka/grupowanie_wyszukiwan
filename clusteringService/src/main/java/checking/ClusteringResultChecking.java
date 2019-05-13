package checking;

import algorithms.AlgorithmResult;
import javafx.util.Pair;
import serialize.AllegroFolderItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marta on 2017-11-18
 **/
public class ClusteringResultChecking {

    public static int compareClusters(AlgorithmResult clusteringResult, String fileName) {
        List<AllegroFolderItem> itemsClusterMapping = clusteringResult.getItemsClusterMapping();
        List<AllegroFolderItem> properMapping = AlgorithmResult.deserializeFolders(fileName);
        LinkedList<Pair<Long, Integer>> clusteredProper = new LinkedList<>();
        LinkedList<Pair<Long, Integer>> clusteredByProgram = new LinkedList<>();

        for (int i = 0; i < properMapping.size(); i++) {
            clusteredProper.add(new Pair(properMapping.get(i).getItemId(), properMapping.get(i).getFolderId()));
            clusteredByProgram.add(new Pair(itemsClusterMapping.get(i).getItemId(), itemsClusterMapping.get(i).getFolderId()));
        }

        int previousCluster = clusteredByProgram.get(0).getValue();
        int previousCluster2 = clusteredByProgram.get(1).getValue();
        for (int i = 0; i < clusteredByProgram.size(); i++) {
            Pair<Long, Integer> entry = clusteredByProgram.get(i);
            int cluster = entry.getValue();

            if (previousCluster != previousCluster2 && previousCluster2 != cluster)
                break;

            int properCluster = getClusterForKey(clusteredProper, entry.getKey());
            if (properCluster != cluster) {
                while (clusteredByProgram.get(i).getValue() == cluster) {
                    clusteredByProgram.set(i, new Pair(clusteredByProgram.get(i).getKey(), properCluster));
                    i++;
                }
                i--;
            }
            if (i > 0)
                previousCluster = clusteredByProgram.get(i - 1).getValue();
            previousCluster2 = clusteredByProgram.get(i).getValue();
        }

        previousCluster = clusteredByProgram.get(0).getValue();
        previousCluster2 = clusteredByProgram.get(1).getValue();
        int differences = 0;
        for (int i = 0; i < clusteredProper.size(); i++) {
            if (previousCluster != previousCluster2 && previousCluster2 != clusteredByProgram.get(i).getValue())
                break;

            Pair<Long, Integer> proper = clusteredProper.get(i);
            Integer clusterForKey = getClusterForKey(clusteredByProgram, proper.getKey());

            if (!proper.getValue().equals(clusterForKey)) {
                differences++;
            }
            if (i > 0)
                previousCluster = clusteredByProgram.get(i - 1).getValue();
            previousCluster2 = clusteredByProgram.get(i).getValue();
        }
        return differences;
    }

    public static Integer getClusterForKey(LinkedList<Pair<Long, Integer>> map, Long item) {
        for (Pair<Long, Integer> pair : map) {
            if (pair.getKey().equals(item))
                return pair.getValue();
        }
        return null;
    }
}
