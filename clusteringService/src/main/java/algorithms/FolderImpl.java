package algorithms;

import serialize.AllegroItem;

import java.util.List;

/**
 * Created on 05-Dec-17.
 */
public class FolderImpl implements Folder {

    private int id;
    private AllegroItem centroid;
    private List<AllegroItem> allegroItems;
    private int numberOfItems;

    public FolderImpl(int id, AllegroItem centroid, List<AllegroItem> allegroItems) {
        this.id = id;
        this.centroid = centroid;
        this.allegroItems = allegroItems;
    }

    @Override
    public void plotCluster() {
        System.out.println("[Folder: " + getId() + "]\n" +
                "[Centroid: " + centroid + "]\n" +
                "[AllegroItems: ");
        for (AllegroItem p : allegroItems) {
            System.out.println(p.toString());
        }
        System.out.println("");
    }

    @Override
    public void clear() {
        allegroItems.clear();
    }

    @Override
    public AllegroItem getCentroid() {
        return centroid;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<AllegroItem> getAllegroItems() {
        numberOfItems = allegroItems.size();
        return allegroItems;
    }

    @Override
    public void setAllegroItems(List<AllegroItem> allegroItems) {
        this.allegroItems = allegroItems;
    }

    @Override
    public int getNumberOfItems() {
        return numberOfItems;
    }
}
