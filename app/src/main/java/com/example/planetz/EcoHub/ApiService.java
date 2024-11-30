package com.example.planetz.EcoHub;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/eco-hub/resources") // 替换为实际的 API 路径
    Call<EcoHubResponse> getEcoHubData();
}