package algorithms.common;

import java.util.List;

/**
 * Created on 05-Dec-17.
 *
 * Cluster containing a set of points, one of which is a centroid.
 */
public interface Cluster {

    void plotCluster();

    void clear();

    Point getCentroid();

    void setCentroid(Point point);

    int getId();

    void addPoint(Point point);

    List<Point> getPoints();
}
