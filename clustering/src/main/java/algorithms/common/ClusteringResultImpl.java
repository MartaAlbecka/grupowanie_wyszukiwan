package algorithms.common;

import java.util.List;

/**
 * Created on 05-Dec-17.
 */
public class ClusteringResultImpl implements ClusteringResult {

    List<Cluster> clusters;

    @Override
    public List<Cluster> getClusters() {
        return clusters;
    }

    public ClusteringResultImpl(List<Cluster> clusters) {
        this.clusters = clusters;
    }
}
