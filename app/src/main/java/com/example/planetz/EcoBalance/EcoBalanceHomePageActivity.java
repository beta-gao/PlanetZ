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
//    private void navigateToProject(String projectName) {
//        Intent intent = new Intent(EcoBalanceHomePageActivity.this, ProjectDetailActivity.class);
//        intent.putExtra("project_name", projectName);
//        // Pass other project details (if needed) through extras
//        startActivity(intent);
//    }
    private void navigateToProject(String projectName) {
        Intent intent = new Intent(EcoBalanceHomePageActivity.this, ProjectDetailActivity.class);

        if ("Kenya Reforestation".equals(projectName)) {
            intent.putExtra("project_name", "Kenya Reforestation");
            intent.putExtra("project_description", "This reforestation project in Kenya has planted 10,000 trees.");
            intent.putExtra("project_cost", "Cost per ton: $25.00");
            intent.putExtra("project_impact", "Impact: Offsets 1 ton of CO2e for every $25 contributed.");
        } else if ("Brazil Rainforest Preservation".equals(projectName)) {
            intent.putExtra("project_name", "Brazil Rainforest Preservation");
            intent.putExtra("project_description", "This project in Brazil protects the Amazon rainforest, conserving biodiversity and reducing deforestation.");
            intent.putExtra("project_cost", "Cost per ton: $20.00");
            intent.putExtra("project_impact", "Impact: Protects 1 hectare of rainforest for every $20 contributed.");
        } else if ("Canada Solar Farms".equals(projectName)) {
            intent.putExtra("project_name", "Canada Solar Farms");
            intent.putExtra("project_description", "This solar farm project in Canada generates clean energy to power 50,000 homes.");
            intent.putExtra("project_cost", "Cost per ton: $30.00");
            intent.putExtra("project_impact", "Impact: Offsets 1 ton of CO2e for every $30 contributed.");
        } else if ("India Renewable Energy".equals(projectName)) {
            intent.putExtra("project_name", "India Renewable Energy");
            intent.putExtra("project_description", "This renewable energy project in India provides clean electricity to rural communities.");
            intent.putExtra("project_cost", "Cost per ton: $30.00");
            intent.putExtra("project_impact", "Impact: Offsets 1 ton of CO2e for every $30 contributed.");
        }

        // 启动目标 Activity
        startActivity(intent);
    }

}