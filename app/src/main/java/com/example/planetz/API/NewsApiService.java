package com.example.planetz.API;

import com.example.planetz.model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("v2/everything")  // NewsAPI 的端点，获取新闻数据
    Call<NewsApiResponse> getNews(
            @Query("q") String query,     // 搜索关键词
            @Query("apiKey") String apiKey // API 密钥
    );
}