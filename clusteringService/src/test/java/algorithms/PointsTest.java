package algorithms;

import algorithms.common.Point;
import org.junit.Test;
import serialize.AllegroItem;
import service.AllegroItemPointConverterImpl;

import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static soap.SoapClientImplTest.getAllegroItemsFor;

/**
 * Created on 05-Dec-17.
 */
public class PointsTest {
    @Test
    public void serializedItemsSame() throws Exception {
        List<AllegroItem> allegroItems = getAllegroItemsFor("golarka elektryczna");

        List<Point> points = new AllegroItemPointConverterImpl().points(allegroItems);

        for (Point point : points) {
            double distance = point.distance(point);
            assertThat(distance, closeTo(0.0, 0.3));
        }
    }

    @Test
    public void serializedItemsDistanceToItself() throws Exception {
        List<AllegroItem> allegroItems = getAllegroItemsFor("golarka elektryczna");

        List<Point> points = new AllegroItemPointConverterImpl().points(allegroItems);

        for (int i = 0; i < points.size(); i++) {
            Point point1 = points.get(i);
            double distanceToItself = point1.distance(point1);
            for (int j = i + 1; j < points.size(); j++) {
                Point point2 = points.get(j);
                double distance = point1.distance(point2);
                assertThat(distanceToItself, lessThanOrEqualTo(distance));
            }
        }
    }
}
