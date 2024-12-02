package com.example.planetz.model;

public class MarketTrend {
    private String title;
    private String description;

    public MarketTrend(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
