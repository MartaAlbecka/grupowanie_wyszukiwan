package algorithms.common;

/**
 * Created on 13-Sep-17.
 */
public class PointTest {




/*
    @Test
    public void distance() throws Exception {
        List<Attribute> attributes1 = new ArrayList<>();
        List<Attribute> attributes2 = new ArrayList<>();
        double sum = 0;

        for (int i = 0; i < 5; i++) {
            final DoubleAttribute attribute1 = createRandomAttribute();
            attributes1.add(attribute1);

            final DoubleAttribute attribute2 = attribute1.copy();
            attribute2.setValue(randomDoubleValue(0, 1));
            attributes2.add(attribute2);

            sum += Math.pow(attribute1.getValue() - attribute2.getValue(), 2);
        }

        Assert.assertEquals(attributes1.size(), 5);
        Assert.assertEquals(attributes2.size(), 5);

        Point p1 = new Point(attributes1);
        Point p2 = new Point(attributes2);

        final double distance = Point.distance(p1, p2);

        // TODO: 14-Sep-17 sum always the same - 1.0

        Assert.assertThat(distance, Matchers.greaterThan(0.0));
        Assert.assertEquals(distance, Math.pow(sum, 1 / 5), 0.000001);
    }


    @Test
    public void distance2() throws Exception {
        List<Attribute> attributes1 = new ArrayList<>();
        List<Attribute> attributes2 = new ArrayList<>();

        DoubleAttribute attribute1 = createRandomAttribute();
        DoubleAttribute attribute2 = attribute1.copy();

        Assert.assertEquals(attribute1.getValue(), attribute2.getValue());
        Assert.assertEquals(attribute1.getName(), attribute2.getName());

        attributes1.add(attribute1);
        attributes2.add(attribute2);

        Point p1 = new Point(attributes1);
        Point p2 = new Point(attributes2);

        for (int i = 0; i < 100; i++) {
            attribute1.setValue(randomDoubleValue(0, 1));
            attribute2.setValue(attribute1.getValue());

            Assert.assertEquals(attribute1.getValue(), attribute2.getValue());
            Assert.assertEquals(attributes1.size(), 1);
            Assert.assertEquals(attributes2.size(), 1);
            Assert.assertEquals(attributes1.get(0), attribute1);
            Assert.assertEquals(attributes2.get(0), attribute2);
            Assert.assertEquals(attributes1, p1.getAttributes());
            Assert.assertEquals(attributes2, p2.getAttributes());
            Assert.assertEquals(Point.distance(p1, p2), 0.0, 0.000001);
        }

    }
*/

}
