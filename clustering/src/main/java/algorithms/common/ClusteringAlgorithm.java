package algorithms.common;

import java.util.List;

/**
 * Created on 12-Sep-17.
 */
public interface ClusteringAlgorithm {

    ClusteringResult createClusters(List<Point> points);
}
