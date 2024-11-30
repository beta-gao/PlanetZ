package com.example.planetz.EcoHub;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetz.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EcoHubActivity extends AppCompatActivity {

    private RecyclerView learningResourceRecyclerView;
    private RecyclerView marketTrendRecyclerView;
    private RecyclerView ecoProductRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_hub);

        // 初始化 RecyclerViews
        learningResourceRecyclerView = findViewById(R.id.learningResourceRecyclerView);
        marketTrendRecyclerView = findViewById(R.id.marketTrendRecyclerView);
        ecoProductRecyclerView = findViewById(R.id.ecoProductRecyclerView);

        setupRecyclerView(learningResourceRecyclerView);
        setupRecyclerView(marketTrendRecyclerView);
        setupRecyclerView(ecoProductRecyclerView);

        // 初始化 Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://your-api-base-url.com") // 替换为你的 API 基地址
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // 获取数据
        fetchEcoHubData(apiService);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void fetchEcoHubData(ApiService apiService) {
        apiService.getEcoHubData().enqueue(new Callback<EcoHubResponse>() {
            @Override
            public void onResponse(Call<EcoHubResponse> call, Response<EcoHubResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EcoHubResponse ecoHubResponse = response.body();

                    // 绑定数据到 RecyclerViews
                    bindDataToRecyclerView(learningResourceRecyclerView, ecoHubResponse.getLearningResources());
                    bindDataToRecyclerView(marketTrendRecyclerView, ecoHubResponse.getMarketTrends());
                    bindDataToRecyclerView(ecoProductRecyclerView, ecoHubResponse.getEcoProducts());
                } else {
                    Toast.makeText(EcoHubActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EcoHubResponse> call, Throwable t) {
                Toast.makeText(EcoHubActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindDataToRecyclerView(RecyclerView recyclerView, List<Resource> resources) {
        ResourceAdapter adapter = new ResourceAdapter(resources);
        recyclerView.setAdapter(adapter);
    }
}