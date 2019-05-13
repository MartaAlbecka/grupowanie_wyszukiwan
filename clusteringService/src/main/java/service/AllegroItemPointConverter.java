package service;

import algorithms.Folder;
import algorithms.common.Cluster;
import algorithms.common.Point;
import serialize.AllegroItem;
import words.WordDistance;

import java.util.List;
import java.util.Map;

/**
 * Created on 10-Dec-17.
 *
 * Converts allegro items into points or the other way around.
 * Can also convert clusters with points into folders with allegro items.
 */
public interface AllegroItemPointConverter {

    Point point(AllegroItem allegroItem);

    Point point(AllegroItem allegroItem, Map<WordDistance, Double> algorithmWeightMap);

    List<Point> points(List<AllegroItem> allegroItems);

    List<Point> points(List<AllegroItem> allegroItems, Map<WordDistance, Double> algorithmWeightMap);

    AllegroItem allegroItem(Point point);

    List<AllegroItem> allegroItems(List<Point> points);

    Folder folder(Cluster cluster);

    List<Folder> folders(List<Cluster> clusters);
}
