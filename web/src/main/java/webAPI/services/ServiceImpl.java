package webAPI.services;

import algorithms.AlgorithmResult;
import algorithms.Folder;
import algorithms.common.ClusteringAlgorithm;
import org.springframework.stereotype.Service;
import serialize.AllegroItem;
import service.ClusteringServiceImpl;
import webAPI.models.Auction;
import webAPI.models.ClusterAuctions;
import webAPI.models.SearchForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceImpl implements webAPI.services.Service {

    private Map<SearchForm, List<ClusterAuctions>> responses = new HashMap<>();

    @Override
    public AlgorithmResult getClusters(ClusteringAlgorithm clusteringAlgorithm, SearchForm searchForm, boolean serialized) {
        String searchValue = searchForm.getValue().trim();
        AlgorithmResult algorithmResult =
                new ClusteringServiceImpl()
                        .createFolders(clusteringAlgorithm, searchValue, serialized);
        responses.put(searchForm, allegroItemsToAuctions(algorithmResult));
        return algorithmResult;
    }

    public List<Auction> getClusterAuctions(String id, String searchValue, String similarity) {
        int idInt = Integer.parseInt(id);
        SearchForm searchForm = new SearchForm(searchValue, similarity);
        for (ClusterAuctions clusterAuctions : responses.get(searchForm)) {
            if (clusterAuctions.getClusterId() == idInt) {
                return clusterAuctions.getAuctions();
            }
        }
        return null;
    }

    private List<ClusterAuctions> allegroItemsToAuctions(AlgorithmResult algorithmResult) {
        List<ClusterAuctions> auctionsToSend = new ArrayList<>();
        List<Folder> folders = algorithmResult.getFolders();
        folders.parallelStream().forEach(folder -> {
            List<AllegroItem> allegroItems = folder.getAllegroItems();
            List<Auction> auctions = new ArrayList<>();
            for (AllegroItem allegroItem : allegroItems) {
                Auction auction = new Auction(allegroItem);
                auctions.add(auction);
            }
            ClusterAuctions clusterAuctions = new ClusterAuctions(folder.getId(), auctions);
            auctionsToSend.add(clusterAuctions);
            folder.clear();
        });
        return auctionsToSend;
    }

}
