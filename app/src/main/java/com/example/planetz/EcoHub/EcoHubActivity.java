package com.example.planetz.EcoHub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.HomePageActivity;
import com.example.planetz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EcoHubActivity extends AppCompatActivity {

    private TextView learningResourceTextView;
    private TextView marketTrendTextView;
    private TextView ecoProductTextView;
    private Button learningResourceButton;

    private final List<String[]> learningResources = new ArrayList<>();
    private final List<String> marketTrends = new ArrayList<>();
    private final List<String> ecoProducts = new ArrayList<>();
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_hub);

        // Initialize views
        learningResourceTextView = findViewById(R.id.learningResourceTextView);
        marketTrendTextView = findViewById(R.id.marketTrendTextView);
        ecoProductTextView = findViewById(R.id.ecoProductTextView);
        learningResourceButton = findViewById(R.id.learningResourceButton);

        // Initialize static data
        initializeStaticData();

        // Randomly select and display content
        displayRandomContent();

        // Set click listener for the Learning Resource button
        learningResourceButton.setOnClickListener(v -> {
            // Open the URL of the selected learning resource
            String url = learningResources.get(random.nextInt(learningResources.size()))[1];
            openUrl(url);
        });

        // Enable back navigation to the homepage
        enableBackToHomepage();
    }

    private void initializeStaticData() {
        // Add learning resources (Title, URL)
        learningResources.add(new String[]{"The Future of Renewable Energy", "https://education.nationalgeographic.org/resource/renewable-energy/"});
        learningResources.add(new String[]{"Understanding Carbon Footprints", "https://www.youtube.com/watch?v=8q7_aV8eLUE"});
        learningResources.add(new String[]{"Top 10 Sustainable Living Tips", "https://www.outdoors.org/resources/amc-outdoors/conservation-and-climate/go-green-10-tips-for-a-more-sustainable-lifestyle/"});
        learningResources.add(new String[]{"Smart Home Technologies", "https://www.youtube.com/watch?v=WHXYlEB_QmY"});

        // Add market trends
        marketTrends.add("Renewable energy investments increased by 30% in 2024.");
        marketTrends.add("Sustainable fashion brands see 50% growth in sales.");
        marketTrends.add("Global carbon offset purchases reach a record high.");

        // Add eco-friendly products
        ecoProducts.add("Smart Thermostat: Reduce energy bills by up to 20%.");
        ecoProducts.add("Bamboo Toothbrush: Biodegradable and eco-friendly.");
        ecoProducts.add("Solar Panel Kit: Affordable renewable energy for your home.");
    }

    private void displayRandomContent() {
        // Randomly pick a learning resource
        String[] learningResource = learningResources.get(random.nextInt(learningResources.size()));
        learningResourceTextView.setText("Latest Resource: " + learningResource[0]);

        // Randomly pick a market trend
        String marketTrend = marketTrends.get(random.nextInt(marketTrends.size()));
        marketTrendTextView.setText("Latest Trend: " + marketTrend);

        // Randomly pick an eco-friendly product
        String ecoProduct = ecoProducts.get(random.nextInt(ecoProducts.size()));
        ecoProductTextView.setText("Product Highlight: " + ecoProduct);
    }

    private void openUrl(String url) {
        // Open the provided URL in a browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void enableBackToHomepage() {
        // Add a back button at the top of the activity (optional)
        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(EcoHubActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}