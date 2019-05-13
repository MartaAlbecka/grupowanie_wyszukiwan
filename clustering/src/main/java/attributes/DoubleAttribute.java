package attributes;

/**
 * Created on 13-Sep-17.
 */
public class DoubleAttribute implements Attribute {

    private String name;
    private double value;

    public DoubleAttribute(String name) {
        this.name = name;
    }

    public DoubleAttribute(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public double distance(Attribute attribute) {
        if (attribute instanceof DoubleAttribute) {
            double distance = Math.pow(value - ((DoubleAttribute) attribute).getValue(), 2);
            return distance;
        }

        throw new IllegalArgumentException("Distance between two different attribute types cannot be calculated.");
    }

    @Override
    public DoubleAttribute copy() {
        return new DoubleAttribute(getName(), getValue());
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
