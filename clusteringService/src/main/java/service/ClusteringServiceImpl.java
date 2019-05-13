package service;

import algorithms.AlgorithmResult;
import algorithms.AlgorithmResultImpl;
import algorithms.Folder;
import algorithms.common.Cluster;
import algorithms.common.ClusteringAlgorithm;
import algorithms.common.Point;
import serialize.AllegroItem;
import serialize.SerializerImpl;
import soap.SoapClient;
import soap.SoapClientImpl;
import words.WordDistance;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created on 06-Dec-17.
 */
public class ClusteringServiceImpl implements ClusteringService {

    private SoapClient soapClient = new SoapClientImpl();

    public AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm,
                                         List<AllegroItem> allegroItems) {
        AllegroItemPointConverter converter = new AllegroItemPointConverterImpl();
        List<Point> points = converter.points(allegroItems);
        List<Cluster> clusters = clusteringAlgorithm.createClusters(points).getClusters();
        List<Folder> folders = converter.folders(clusters);
        return new AlgorithmResultImpl(folders);
    }

    public AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm,
                                         List<AllegroItem> allegroItems,
                                         Map<WordDistance, Double> algorithmWeightMap) {
        AllegroItemPointConverter converter = new AllegroItemPointConverterImpl();
        List<Point> points = converter.points(allegroItems, algorithmWeightMap);
        List<Cluster> clusters = clusteringAlgorithm.createClusters(points).getClusters();
        List<Folder> folders = converter.folders(clusters);
        return new AlgorithmResultImpl(folders);
    }

    public AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm, String searchValue,
                                         boolean serialized) {
        List<AllegroItem> allegroItems;
        if (serialized)
            allegroItems = getItemsAndSerialize(searchValue);
        else
            allegroItems = soapClient.getItemsByKeyWord(searchValue);

        if (allegroItems == null || allegroItems.isEmpty()) {
            return null;
        } else {
            return createFolders(clusteringAlgorithm, allegroItems);
        }
    }

    public AlgorithmResult createFolders(ClusteringAlgorithm clusteringAlgorithm, String searchValue,
                                         boolean serialized, Map<WordDistance, Double> algorithmWeightMap) {
        List<AllegroItem> allegroItems;
        if (serialized)
            allegroItems = getItemsAndSerialize(searchValue);
        else
            allegroItems = soapClient.getItemsByKeyWord(searchValue);

        if (allegroItems == null || allegroItems.isEmpty()) {
            return null;
        } else {
            return createFolders(clusteringAlgorithm, allegroItems, algorithmWeightMap);
        }
    }

    private List<AllegroItem> getItemsAndSerialize(String searchValue) {
        SerializerImpl serializer = new SerializerImpl();

        File file = new File(serializer.getFilePath(searchValue));

        if (file.exists()) {
            return serializer.deserialize(searchValue);
        } else {
            final List<AllegroItem> allegroItems = soapClient.getItemsByKeyWord(searchValue);
            serializer.serialize(allegroItems, searchValue);
            return allegroItems;
        }
    }

}
