package attributes;

/**
 * Created on 13-Sep-17.
 */
public interface Attribute {

    /**
     * Calculates a distance to another attribute of the same type. Returns a value between 0 and 100 including
     * those threshold values. This opperation should be symmetrical.
     *
     * @param attribute attribute of the same type
     * @return distance from an attribute, a value from 0 to 100
     */
    double distance(Attribute attribute);

    /**
     * Creates a copy of the attribute.
     * @return a copy
     */
    Attribute copy();

    String getName();

    void setName(String name);
}
