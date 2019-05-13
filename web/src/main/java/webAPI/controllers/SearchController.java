package webAPI.controllers;

import algorithms.AlgorithmResult;
import algorithms.common.ClusteringAlgorithm;
import algorithms.kmeans.KMeansEpsilon;
import config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webAPI.models.Auction;
import webAPI.models.SearchForm;
import webAPI.services.Service;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private Service service;

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            container.setContextPath("");
            if (System.getenv("PORT") != null) {
                container.setPort(Integer.valueOf(System.getenv("PORT")));
            }
        });
    }

    @GetMapping("/")
    public String index(Model model) {
        SearchForm searchForm = new SearchForm();
        searchForm.setSimilarity("70");
        model.addAttribute("searchForm", searchForm);
        return "index";
    }

    @GetMapping("/clusters/{id}")
    @ResponseBody
    public List<Auction> clusterAuctions(@PathVariable String id, @RequestParam String searchValue, @RequestParam String similarity) {
        return service.getClusterAuctions(id, searchValue, similarity);
    }

    @GetMapping("/clusters")
    public String clusters(@ModelAttribute SearchForm searchForm, Model model) throws InstantiationException {
        String searchValue = searchForm.getValue().trim();
        int epsilon = 100 - Integer.parseInt(searchForm.getSimilarity());
        boolean finish = false;

        if (searchValue.isEmpty()) {
            model.addAttribute("wrongValue", true);
            finish = true;
        }
        if (epsilon < 0 || epsilon > 100) {
            model.addAttribute("wrongSimilarity", true);
            finish = true;
        }
        if (!finish) {
            ClusteringAlgorithm algorithm;

            if (Configuration.algorithm.isAssignableFrom(KMeansEpsilon.class)) {
                algorithm = new KMeansEpsilon(epsilon);
            } else {
                throw new InstantiationException("The algorithm couldn't be intantiated.");
            }

            AlgorithmResult algorithmResult = service.getClusters(algorithm, searchForm, Configuration.serialized);
            if (algorithmResult == null) {
                model.addAttribute("emptyClusters", true);
                return "clusters";
            }
            model.addAttribute("clusters", algorithmResult.getFolders());
        }
        return "clusters";
    }
}