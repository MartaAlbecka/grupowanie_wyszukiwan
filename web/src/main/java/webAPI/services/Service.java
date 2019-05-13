package webAPI.services;

import algorithms.AlgorithmResult;
import algorithms.common.ClusteringAlgorithm;
import webAPI.models.Auction;
import webAPI.models.SearchForm;

import java.util.List;

public interface Service {
    AlgorithmResult getClusters(ClusteringAlgorithm clusteringAlgorithm, SearchForm searchForm, boolean serialized);

    List<Auction> getClusterAuctions(String id, String searchValue, String similarity);
}
