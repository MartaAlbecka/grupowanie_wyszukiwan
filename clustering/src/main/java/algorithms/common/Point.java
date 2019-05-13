package algorithms.common;

import attributes.Attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created on 12-Sep-17.
 */
public class Point {

    private static final Logger LOG = Logger.getLogger(Point.class.getName());

    private List<Attribute> attributes;
    private Map<Point, Double> savedDistances = new HashMap<>();

    public Point(List<Attribute> attributes) {
        this.attributes = attributes;
    }


    public double distance(Point point) {
        return distance(this, point);
    }

    //Calculates the distance between two points.
    public static double distance(Point point1, Point point2) {
        Double savedDistance = getSavedDistance(point1, point2);
        if (savedDistance != null)
            return savedDistance;

        Double sum = 0.0;
        List<Attribute> p1Attributes = point1.getAttributes();

        for (Attribute attribute : point1.getAttributes()) {
            // calculate distance to every other point in the cluster
            Attribute attribute2 = point2.getAttribute(attribute.getName());

            if (attribute2 != null) {
                double distance = attribute.distance(attribute2);
                sum += distance;
            } else {
                int differentAttributes = compareAttributes(point1, point2);
                sum += differentAttributes * 100;
            }
        }

        double distance = sum / p1Attributes.size();
        if (distance > 100)
            LOG.info("Distance > 100: " + distance);

        point1.savedDistances.put(point2, distance);

        return distance;
    }

    private static int compareAttributes(Point point1, Point point2) {
        int differentAttributes = 0;
        for (Attribute attribute : point1.getAttributes()) {
            if (point2.getAttribute(attribute.getName()) == null)
                differentAttributes++;
        }
        for (Attribute attribute : point2.getAttributes()) {
            if (point1.getAttribute(attribute.getName()) == null)
                differentAttributes++;
        }
        return differentAttributes;
    }

    public static Double getSavedDistance(Point point1, Point point2) {
        Double savedDistance = point1.savedDistances.get(point2);
        if (savedDistance != null)
            return savedDistance;
        else
            return point2.savedDistances.get(point1);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("(");
        for (Attribute entry : attributes) {
            builder.append(entry.getName()).append("=").append(entry.toString()).append(", ");
        }
        builder.delete(builder.length() - 2, builder.length()).append(")");
        return builder.toString();
    }

    public Attribute getAttribute(String attributeName) {
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
