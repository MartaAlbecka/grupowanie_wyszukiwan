package algorithms.kmeans;

import algorithms.common.Cluster;
import algorithms.common.ClusteringResult;
import algorithms.common.ClusteringResultImpl;
import algorithms.common.Point;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created on 12-Sep-17.
 */
public abstract class KMeansBase implements KMeans {

    private static final Logger LOG = Logger.getLogger(KMeansBase.class.getName());
    private List<Point> points = new ArrayList<>();
    private List<Cluster> clusters = new ArrayList<>();

    protected abstract void init();

    @Override
    public ClusteringResult createClusters(List<Point> points) {
        this.points = points;
        clusters.clear();

        init();
        calculate();
        return new ClusteringResultImpl(new ArrayList<>(clusters));
    }

    protected void plotClusters() {
        for (Cluster cluster : clusters) {
            cluster.plotCluster();
        }
    }

    /**
     * The process to calculate the K Means, with iterating method.
     */
    protected void calculate() {
        int iteration = 0;

        Map<Cluster, Point> currentCentroids = getCentroids();
        Map<Cluster, Point> lastCentroids = null;

        while (!Objects.equals(lastCentroids, currentCentroids) && iteration < 10) {
            clusters.forEach(Cluster::clear);

            lastCentroids = currentCentroids;

            System.out.println("Iteration " + iteration + " start");

            assignCluster();

            calculateCentroids();

            currentCentroids = getCentroids();

            System.out.println(currentCentroids.size() + " clusters\n");

            iteration++;
        }

        System.out.println("End of clustering.");
    }

    protected Map<Cluster, Point> getCentroids() {
        Map<Cluster, Point> centroids = new HashMap<>();
        for (Cluster cluster : clusters) {
            centroids.put(cluster, cluster.getCentroid());
        }
        return centroids;
    }

    /**
     * Assign points to the closer cluster
     */
    protected abstract void assignCluster();

    /**
     * Calculate new centroids.
     */
    protected void calculateCentroids() {
        for (Cluster cluster : clusters) {
            final List<Point> clusterPoints = cluster.getPoints();
            if (clusterPoints.size() == 0) {
                LOG.info("Folder is empty:\n");
                cluster.plotCluster();
                continue;
            }

            Point centroid = calculateCentroid(cluster);

            cluster.setCentroid(centroid);
        }
    }

    /**
     * Gets a centroid of a cluster. A centroid is a point that has the lowest sum of distances to other points in the
     * cluster.
     */
    private Point calculateCentroid(Cluster cluster) {
        List<Point> clusterPoints = cluster.getPoints();
        Map<Point, Double> distances = new HashMap<>();

        for (Point point : clusterPoints) {
            distances.put(point, 0.0);
        }

        Double distanceMin = Double.MAX_VALUE;

        Point centroid = null;

        for (int i = 0; i < clusterPoints.size(); i++) {
            Point point1 = clusterPoints.get(i);

            for (int j = i + 1; j < clusterPoints.size(); j++) {
                Point point2 = clusterPoints.get(j);

                double distance = Point.distance(point1, point2);

                distances.put(point1, distances.get(point1) + distance);
                distances.put(point2, distances.get(point2) + distance);
            }

            // centroid always has a priority, so that if there are points with the same distances,
            // it chooses the previous centroid
            boolean isPreviousCentroid = point1.equals(cluster.getCentroid());
            Double pointDistance = distances.get(point1);
            if ((isPreviousCentroid && pointDistance <= distanceMin) ||
                    (!isPreviousCentroid && pointDistance < distanceMin)) {
                centroid = point1;
                distanceMin = pointDistance;
            }
        }
        return centroid;
    }

    @Override
    public List<Cluster> getClusters() {
        return clusters;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public List<Cluster> getClustersKMeans() {
        return clusters;
    }

    public void setClusters(List<Cluster> cluster) {
        this.clusters = cluster;
    }
}
