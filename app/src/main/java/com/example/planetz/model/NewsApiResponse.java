package com.example.planetz.model;

import java.util.List;

public class NewsApiResponse {
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public static class Article {
        private String title;
        private String description;
        private String url;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }
    }
}
