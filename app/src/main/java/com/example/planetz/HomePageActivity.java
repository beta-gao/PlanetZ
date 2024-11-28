package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.DisplayingAnnualFootprintResult.BreakdownActivity;

public class HomePageActivity extends AppCompatActivity {

    private Button buttonFirstAnnualCarbonFootprint, buttonEcoTracker, buttonEcoGauge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Bind buttons
        buttonFirstAnnualCarbonFootprint = findViewById(R.id.buttonFirstAnnualCarbonFootprint);
        buttonEcoTracker = findViewById(R.id.buttonEcoTracker);
        buttonEcoGauge = findViewById(R.id.buttonEcoGauge);

        // Set click listener for First Annual Carbon Footprint button
        buttonFirstAnnualCarbonFootprint.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, BreakdownActivity.class);
            startActivity(intent);
        });

        // Additional listeners for Eco Tracker and Eco Gauge can be added later
    }
}