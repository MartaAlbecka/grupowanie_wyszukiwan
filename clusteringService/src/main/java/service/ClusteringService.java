package service;

import algorithms.AlgorithmResult;
import algorithms.common.ClusteringAlgorithm;
import serialize.AllegroItem;
import words.WordDistance;

import java.util.List;
import java.util.Map;

/**
 * Created on 05-Dec-17.
 */
public interface ClusteringService {
    /**
     * Runs clustering algorithm and creates folders from allegro items.
     *
     * @param clusteringAlgorithm algorithm
     * @param allegroItems        list of auctions
     * @return algorithm result with a list of folders
     */
    AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm, List<AllegroItem> allegroItems);

    /**
     * Runs clustering algorithm and creates folders from allegro items and weight map for text algorithms.
     * @param clusteringAlgorithm algorithm
     * @param allegroItems list of auctions
     * @param algorithmWeightMap map of weights for text distance algorithms
     * @return algorithm result with a list of folders
     */
    AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm, List<AllegroItem> allegroItems,
                                  Map<WordDistance, Double> algorithmWeightMap);

    /**
     * Downloads allegro items from allegro API or deserializes from file based on serialized value, runs clustering
     * algorithm and creates folders based on search value.
     * @param clusteringAlgorithm algorithm
     * @param searchValue value that auctions will be searched by in Allegro
     * @param serialized indicated whether allegro items should be deserialized from file or downloaded from Allegro API
     * @return algorithm result with a list of folders
     */
    AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm, String searchValue,
                                  boolean serialized);

    /**
     * Downloads allegro items from allegro API or deserializes from file based on serialized value, runs clustering
     * algorithm and creates folders based on search value and weight map for text algorithms.
     * @param clusteringAlgorithm algorithm
     * @param searchValue value that auctions will be searched by in Allegro
     * @param serialized indicated whether allegro items should be deserialized from file or downloaded from Allegro API
     * @param algorithmWeightMap map of weights for text distance algorithms
     * @return algorithm result with a list of folders
     */
    AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm, String searchValue,
                                  boolean serialized, Map<WordDistance, Double> algorithmWeightMap);
}
