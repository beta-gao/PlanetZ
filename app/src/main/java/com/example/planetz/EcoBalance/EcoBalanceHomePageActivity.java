package com.example.planetz.EcoBalance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;

public class EcoBalanceHomePageActivity extends AppCompatActivity {

    private Button kenyaReforestationButton;
    private Button brazilRainforestPreservationButton;
    private Button canadaSolarFarmsButton;
    private Button indiaRenewableEnergyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecobalance_homepage);

        // Initialize buttons
        kenyaReforestationButton = findViewById(R.id.kenyaReforestationButton);
        brazilRainforestPreservationButton = findViewById(R.id.brazilRainforestPreservationButton);
        canadaSolarFarmsButton = findViewById(R.id.canadaSolarFarmsButton);
        indiaRenewableEnergyButton = findViewById(R.id.indiaRenewableEnergyButton);

        // Set button click listeners
        kenyaReforestationButton.setOnClickListener(v -> navigateToProject("Kenya Reforestation"));
        brazilRainforestPreservationButton.setOnClickListener(v -> navigateToProject("Brazil Rainforest Preservation"));
        canadaSolarFarmsButton.setOnClickListener(v -> navigateToProject("Canada Solar Farms"));
        indiaRenewableEnergyButton.setOnClickListener(v -> navigateToProject("India Renewable Energy"));
    }

    /**
     * Navigates to the project detail page with the given project name.
     *
     * @param projectName The name of the project.
     */
    private void navigateToProject(String projectName) {
        Intent intent = new Intent(EcoBalanceHomePageActivity.this, ProjectDetailActivity.class);
        intent.putExtra("project_name", projectName);
        // Pass other project details (if needed) through extras
        startActivity(intent);
    }
}