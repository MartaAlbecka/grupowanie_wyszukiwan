package algorithms.common;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created on 12-Sep-17.
 */
public class ClusterImpl implements Cluster {

    private static final Logger LOG = Logger.getLogger(ClusterImpl.class.getName());

    private int id;

    private List<Point> points = new ArrayList<>();
    private Point centroid;

    public ClusterImpl(int id) {
        this.id = id;
        this.centroid = null;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
    public void clear() {
        points.clear();
    }

    @Override
    public void plotCluster() {
        System.out.println("[Cluster: " + getId() + "]\n" +
                "[Centroid: " + centroid + "]\n" +
                "[Points: ");
        for (Point p : points) {
            System.out.println(p.toString());
        }
        System.out.println("");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @Override
    public Point getCentroid() {
        return centroid;
    }

    @Override
    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

}
