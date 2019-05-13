package algorithms.kmeans;

import algorithms.common.Cluster;
import algorithms.common.ClusterImpl;
import algorithms.common.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created on 12-Sep-17.
 */
public class KMeansEpsilon extends KMeansBase {

    private double epsilon;

    public KMeansEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    protected void init() {
        //Print Initial state
        plotClusters();
    }

    @Override
    protected void assignCluster() {
        final List<Cluster> clusters = getClustersKMeans();

        Integer clusterId = null;

        List<Point> points = new ArrayList<>(getPoints());

        assignClusterCentroids(clusters, points);

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            Cluster bestCluster = null;
            double minDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                final double distance = Point.distance(point, cluster.getCentroid());
                if (distance < minDistance) {
                    bestCluster = cluster;
                    minDistance = distance;
                }
            }

            // the point is within the cluster minimum range
            if (bestCluster != null && minDistance <= epsilon) {
                bestCluster.addPoint(point);
            } else {
                if (clusterId == null)
                    clusterId = getLastClusterId();

                final Cluster cluster = new ClusterImpl(++clusterId);
                cluster.addPoint(point);
                cluster.setCentroid(point);

                clusters.add(cluster);
            }

        }

        setClusters(clusters);
    }

    private void assignClusterCentroids(List<Cluster> clusters, List<Point> points) {
        for (Cluster cluster : clusters) {
            Point centroid = cluster.getCentroid();
            cluster.addPoint(centroid);
            points.remove(centroid);
        }
    }

    private Integer getLastClusterId() {
        final Optional<Cluster> lastCluster = getClusters().stream().max(Comparator.comparingInt(Cluster::getId));
        if (lastCluster.isPresent())
            return lastCluster.get().getId();
        else
            return 0;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }
}
