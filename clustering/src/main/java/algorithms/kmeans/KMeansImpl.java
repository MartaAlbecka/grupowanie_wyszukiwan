package algorithms.kmeans;

import algorithms.common.Cluster;
import algorithms.common.ClusterImpl;
import algorithms.common.Point;

import java.util.*;

/**
 * Created on 12-Sep-17.
 */
public class KMeansImpl extends KMeansBase {

    private final static int NUM_CLUSTERS = 3;

    private int numClusters = NUM_CLUSTERS;

    public KMeansImpl() {
    }

    public KMeansImpl(int numClusters) {
        this.numClusters = numClusters;
    }

    @Override
    protected void init() {
        final Random random = new Random();

        if (numClusters > getPoints().size()) {
            numClusters = getPoints().size();
        }

        final List<Point> avaiableCentroids = new ArrayList<>(getPoints());
        List<Cluster> clusters = new ArrayList<>();

        //Create ClusterDims
        //Set Random Centroids
        for (int i = 0; i < numClusters; i++) {
            Cluster cluster = new ClusterImpl(i);
            final int centroidId = random.nextInt(Integer.MAX_VALUE) % avaiableCentroids.size();
            Point centroid = avaiableCentroids.get(centroidId);
            avaiableCentroids.remove(centroidId);
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }

        setClusters(clusters);

        //Print Initial state
        plotClusters();
    }

    @Override
    protected void assignCluster() {
        Cluster cluster;

        for (Point point : getPoints()) {
            // get cluster with the closest centroid
            final Optional<Cluster> clusterMin =
                    getClustersKMeans().stream()
                            .min(Comparator.comparingDouble(c -> Point.distance(point, c.getCentroid())));

            if (clusterMin.isPresent()) {
                cluster = clusterMin.get();
                cluster.addPoint(point);
            } else {
                throw new IllegalStateException("There's no cluster with minimal distance to the point.");
            }
        }
    }

    public int getNumClusters() {
        return numClusters;
    }

    public void setNumClusters(int numClusters) {
        this.numClusters = numClusters;
    }
}
