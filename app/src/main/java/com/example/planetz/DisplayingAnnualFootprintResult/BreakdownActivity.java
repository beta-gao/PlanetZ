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
    private Button comparisonButton; // 跳转按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown);

        breakdownTextView = findViewById(R.id.breakdownTextView);
        comparisonButton = findViewById(R.id.buttonComparison); // 绑定按钮

        // 获取全局单例的 AnnualFootprintData
        AnnualFootprintData annualFootprintData = AnnualFootprintData.getInstance();

        if (annualFootprintData == null) {
            Toast.makeText(this, "AnnualFootprintData is not initialized!", Toast.LENGTH_SHORT).show();
            breakdownTextView.setText("No data available. Please calculate your footprint first.");
            return;
        }

        // 提取各类别的碳足迹
        double transportation = annualFootprintData.getTransportation();
        double housing = annualFootprintData.getHousing();
        double food = annualFootprintData.getFood();
        double consumption = annualFootprintData.getConsumption();
        double total = annualFootprintData.getTotal();

        // 计算各类别的贡献比例
        double transportationPercentage = (transportation / total) * 100;
        double housingPercentage = (housing / total) * 100;
        double foodPercentage = (food / total) * 100;
        double consumptionPercentage = (consumption / total) * 100;

        // 构建显示文本
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

        // 显示结果
        breakdownTextView.setText(breakdownText);

        // 设置按钮点击监听
        comparisonButton.setOnClickListener(v -> navigateToComparisonActivity());
    }

    /**
     * 跳转到 ComparisonActivity
     */
    private void navigateToComparisonActivity() {
        Intent intent = new Intent(BreakdownActivity.this, ComparisonActivity.class);
        startActivity(intent);
    }
}
