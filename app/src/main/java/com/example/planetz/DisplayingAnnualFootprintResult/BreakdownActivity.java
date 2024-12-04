package com.example.planetz.DisplayingAnnualFootprintResult;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.AnnualFootprintData;

public class BreakdownActivity extends AppCompatActivity {

    private TextView breakdownTextView;
    private Button comparisonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown);

        breakdownTextView = findViewById(R.id.breakdownTextView);
        comparisonButton = findViewById(R.id.buttonComparison);

        AnnualFootprintData annualFootprintData = AnnualFootprintData.getInstance();

        if (annualFootprintData == null) {
            Toast.makeText(this, "AnnualFootprintData is not initialized!", Toast.LENGTH_SHORT).show();
            breakdownTextView.setText("No data available. Please calculate your footprint first.");
            return;
        }

        double transportation = annualFootprintData.getTransportation();
        double housing = annualFootprintData.getHousing();
        double food = annualFootprintData.getFood();
        double consumption = annualFootprintData.getConsumption();
        double total = annualFootprintData.getTotal();

        double transportationPercentage = (transportation / total) * 100;
        double housingPercentage = (housing / total) * 100;
        double foodPercentage = (food / total) * 100;
        double consumptionPercentage = (consumption / total) * 100;

        String breakdownText = String.format(
                "Your Total Annual Carbon Footprint: %.2f tons CO2e\n\n" +
                        "Breakdown by Category:\n" +
                        "Transportation: %.2f tons CO2e (%.2f%%)\n" +
                        "Housing: %.2f tons CO2e (%.2f%%)\n" +
                        "Food: %.2f tons CO2e (%.2f%%)\n" +
                        "Consumption: %.2f tons CO2e (%.2f%%)",
                total,
                transportation, transportationPercentage,
                housing, housingPercentage,
                food, foodPercentage,
                consumption, consumptionPercentage
        );

        breakdownTextView.setText(breakdownText);

        comparisonButton.setOnClickListener(v -> navigateToComparisonActivity());
    }

    private void navigateToComparisonActivity() {
        Intent intent = new Intent(BreakdownActivity.this, ComparisonActivity.class);
        startActivity(intent);
    }
}
