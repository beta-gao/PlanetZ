package com.example.planetz.EcoHub;

import java.util.List;

public class EcoHubResponse {
    private List<Resource> learningResources;
    private List<Resource> marketTrends;
    private List<Resource> ecoProducts;

    public List<Resource> getLearningResources() {
        return learningResources;
    }

    public void setLearningResources(List<Resource> learningResources) {
        this.learningResources = learningResources;
    }

    public List<Resource> getMarketTrends() {
        return marketTrends;
    }

    public void setMarketTrends(List<Resource> marketTrends) {
        this.marketTrends = marketTrends;
    }

    public List<Resource> getEcoProducts() {
        return ecoProducts;
    }

    public void setEcoProducts(List<Resource> ecoProducts) {
        this.ecoProducts = ecoProducts;
    }
}