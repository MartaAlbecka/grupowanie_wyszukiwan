package service;

import algorithms.Folder;
import algorithms.FolderImpl;
import algorithms.common.Cluster;
import algorithms.common.Point;
import attributes.Attribute;
import attributes.GraphicAttribute;
import attributes.StringAttribute;
import serialize.AllegroItem;
import words.WordDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 05-Dec-17.
 */
public class AllegroItemPointConverterImpl implements AllegroItemPointConverter {

    private Map<Point, AllegroItem> converted;

    public Point point(AllegroItem allegroItem, Map<WordDistance, Double> algorithmWeightMap) {

        List<Attribute> attributes = new ArrayList<>();
        if (algorithmWeightMap != null)
            attributes.add(new StringAttribute("title", allegroItem.getTitle(), algorithmWeightMap));
        else
            attributes.add(new StringAttribute("title", allegroItem.getTitle()));

        if (allegroItem.getImage() != null)
            attributes.add(new GraphicAttribute("photo", allegroItem.getImage()));

        Point point = new Point(attributes);
        converted.put(point, allegroItem);
        return point;
    }

    public Point point(AllegroItem allegroItem) {
        return point(allegroItem, null);
    }

    public List<Point> points(List<AllegroItem> allegroItems, Map<WordDistance, Double> algorithmWeightMap) {
        converted = new HashMap<>(allegroItems.size());
        return allegroItems.parallelStream()
                .map(allegroItem -> point(allegroItem, algorithmWeightMap))
                .collect(Collectors.toList());
    }

    public List<Point> points(List<AllegroItem> allegroItems) {
        converted = new HashMap<>(allegroItems.size());
        return allegroItems.parallelStream()
                .map(this::point)
                .collect(Collectors.toList());
    }


    public AllegroItem allegroItem(Point point) {
        return converted.get(point);
    }

    public List<AllegroItem> allegroItems(List<Point> points) {
        return points.parallelStream().map(this::allegroItem).collect(Collectors.toList());
    }

    public Folder folder(Cluster cluster) {
        List<AllegroItem> allegroItems = allegroItems(cluster.getPoints());
        AllegroItem centroid = allegroItem(cluster.getCentroid());

        return new FolderImpl(cluster.getId(), centroid, allegroItems);
    }

    public List<Folder> folders(List<Cluster> clusters) {
        return clusters.parallelStream().map(this::folder).collect(Collectors.toList());
    }
}
