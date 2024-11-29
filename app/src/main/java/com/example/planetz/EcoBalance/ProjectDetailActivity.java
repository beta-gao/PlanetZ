package com.example.planetz.EcoBalance;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;

public class ProjectDetailActivity extends AppCompatActivity {

    private TextView projectTitle;
    private TextView projectDescription;
    private TextView projectCost;
    private TextView projectImpact;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        // Initialize views
        projectTitle = findViewById(R.id.projectTitle);
        projectDescription = findViewById(R.id.projectDescription);
        projectCost = findViewById(R.id.projectCost);
        projectImpact = findViewById(R.id.projectImpact);
        backButton = findViewById(R.id.backToEcoBalanceButton);

        // Get data from intent
        String title = getIntent().getStringExtra("project_name");
        String description = getIntent().getStringExtra("project_description");
        String cost = getIntent().getStringExtra("project_cost");
        String impact = getIntent().getStringExtra("project_impact");

        // Set data to views
        projectTitle.setText(title);
        projectDescription.setText(description);
        projectCost.setText(cost);
        projectImpact.setText(impact);

        // Set back button listener
        backButton.setOnClickListener(v -> finish());
    }
}