package algorithms.common;

import java.util.List;

/**
 * Created on 05-Dec-17.
 *
 * Result of the clustering algorithm, which contains a set of clusters.
 */
public interface ClusteringResult {

    List<Cluster> getClusters();
}
