package attributes;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created on 13-Sep-17.
 */
public class DoubleAttributeTest {

    @Test
    public void distance() throws Exception {
        DoubleAttribute attribute1 = new DoubleAttribute("a");
        DoubleAttribute attribute2 = new DoubleAttribute("b");

        double distance;

        for (int i = 0; i < 100; i++) {
            attribute1.setValue(randomDoubleValue(0, 1));
            attribute2.setValue(randomDoubleValue(0, 1));

            distance = attribute1.distance(attribute2);

            Assert.assertThat(distance, Matchers.greaterThanOrEqualTo(0.0));
        }

    }


    @Test
    public void setRandomValue() throws Exception {
        double prevValue = 0.0;
        DoubleAttribute attribute = new DoubleAttribute("name", prevValue);

        for (int i = 0; i < 100; i++) {
            prevValue = attribute.getValue();

            attribute.setValue(randomDoubleValue(0, 1));

            Assert.assertNotEquals(prevValue, attribute.getValue());
        }
    }

    @Test
    public void setBoundariesRandomValue() throws Exception {
        double min, max;
        DoubleAttribute attribute = new DoubleAttribute("");

        for (double i = -10; i < 10; i += 0.1) {
            min = i;
            max = i * 1.1 + 5;

            Assert.assertTrue(min < max);

            attribute.setValue(randomDoubleValue(min, max));

            Assert.assertThat(attribute.getValue(), Matchers.greaterThanOrEqualTo(min));
            Assert.assertThat(attribute.getValue(), Matchers.lessThanOrEqualTo(max));
        }
    }

    private static Double randomDoubleValue(double min, double max) {
        return new Random().nextDouble() * (max - min) + min;
    }
}
