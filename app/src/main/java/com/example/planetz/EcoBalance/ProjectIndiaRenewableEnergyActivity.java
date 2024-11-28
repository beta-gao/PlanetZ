package com.example.planetz.EcoBalance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;

public class ProjectIndiaRenewableEnergyActivity extends AppCompatActivity {

    private TextView projectTitle;
    private TextView projectDescription;
    private TextView projectCost;
    private TextView projectImpact;
    private EditText inputCO2eAmount;
    private Button payButton;
    private Button backToEcoBalanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_india_renewable_energy);

        // Initialize views
        projectTitle = findViewById(R.id.projectTitle);
        projectDescription = findViewById(R.id.projectDescription);
        projectCost = findViewById(R.id.projectCost);
        projectImpact = findViewById(R.id.projectImpact);
        inputCO2eAmount = findViewById(R.id.inputCO2eAmount);
        payButton = findViewById(R.id.payButton);
        backToEcoBalanceButton = findViewById(R.id.backToEcoBalanceButton);

        // Set project details
        projectTitle.setText("India Renewable Energy");
        projectDescription.setText("This renewable energy project in India provides clean electricity to rural communities.");
        projectCost.setText("Cost per ton: $30.00");
        projectImpact.setText("Impact: Offsets 1 ton of CO2e for every $30 contributed.");

        // Pay button logic
        payButton.setOnClickListener(v -> {
            String co2eAmountText = inputCO2eAmount.getText().toString();
            if (co2eAmountText.isEmpty()) {
                Toast.makeText(this, "Please enter the amount of CO2e to offset.", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double co2eAmount = Double.parseDouble(co2eAmountText);
                double cost = co2eAmount * 30; // Calculate the total cost
                Toast.makeText(this, String.format("Payment of $%.2f successful!", cost), Toast.LENGTH_SHORT).show();

                // Navigate to payment success screen
                Intent intent = new Intent(this, EcoBalancePay.class);
                startActivity(intent);
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid input. Please enter a valid number.", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button logic
        backToEcoBalanceButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, EcoBalanceHomePageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}