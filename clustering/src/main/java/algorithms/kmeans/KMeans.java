package algorithms.kmeans;

import algorithms.common.Cluster;
import algorithms.common.ClusteringAlgorithm;

import java.util.List;

/**
 * Created on 06-Nov-17.
 */
public interface KMeans extends ClusteringAlgorithm {

    List<Cluster> getClusters();

}
