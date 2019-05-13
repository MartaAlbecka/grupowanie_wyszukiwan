package attributes;

import words.LevenshteinDistanceImpl;
import words.SentenceDistance;
import words.WordDistance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 14-Oct-17.
 */
public class StringAttribute implements Attribute {

    private static final Map<WordDistance, Double> ALGORITHM_PRIORITY_MAP;

    private String name;
    private String value;
    private Map<WordDistance, Double> algorithmWeightMap;

    static {
        Map<WordDistance, Double> map = new HashMap<>();
        map.put(new SentenceDistance(), 1.);
        map.put(new LevenshteinDistanceImpl(), 1.);
        ALGORITHM_PRIORITY_MAP = Collections.unmodifiableMap(map);
    }

    public StringAttribute(String name, String value) {
        this.name = name;
        this.value = WordDistance.clearTitle(value);
        this.algorithmWeightMap = ALGORITHM_PRIORITY_MAP;
    }

    public StringAttribute(String name, String value, Map<WordDistance, Double> algorithmWeightMap) {
        this.name = name;
        this.value = WordDistance.clearTitle(value);
        this.algorithmWeightMap = algorithmWeightMap;
    }

    @Override
    public double distance(Attribute attribute) {
        if (attribute instanceof StringAttribute) {

            final String attributeValue = ((StringAttribute) attribute).getValue();
            double prioritySum = 0;
            for (Double priority : algorithmWeightMap.values()) {
                prioritySum += priority;
            }

            double distanceSum = 0;
            for (Map.Entry<WordDistance, Double> algorithmPriorityEntry : algorithmWeightMap.entrySet()) {
                final WordDistance algorithm = algorithmPriorityEntry.getKey();
                final Double priority = algorithmPriorityEntry.getValue();

                final int distance = 100 - algorithm.wordsSimilarity(this.value, attributeValue);
                distanceSum += distance * (priority / prioritySum);
            }

            return distanceSum;
        }

        throw new IllegalArgumentException("Distance between two different attribute types cannot be calculated.");
    }

    @Override
    public Attribute copy() {
        return new StringAttribute(getName(), getValue(), getAlgorithmWeightMap());
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public Map<WordDistance, Double> getAlgorithmWeightMap() {
        return algorithmWeightMap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
