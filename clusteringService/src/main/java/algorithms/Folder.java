package algorithms;

import serialize.AllegroItem;

import java.util.List;

/**
 * Created on 25-Oct-17.
 */
public interface Folder {

    /**
     * Plots clusters onto a standard output.
     */
    void plotCluster();

    /**
     * Clears allegro items from the folder.
     */
    void clear();

    /**
     * Gets the centroid of the folder, which is the representative allegro item.
     *
     * @return centroid of the folder
     */
    AllegroItem getCentroid();

    /**
     * Gets id of the folder.
     * @return id of the folder
     */
    int getId();

    /**
     * Gets allegro items contained in the folder.
     * @return allegro items in the folder
     */
    List<AllegroItem> getAllegroItems();

    /**
     * Sets allegro items in the folder.
     * @param allegroItems allegro items to be set
     */
    void setAllegroItems(List<AllegroItem> allegroItems);

    /**
     * Gets number of allegro items in the folder.
     * @return number of items in the folder
     */
    int getNumberOfItems();
}
