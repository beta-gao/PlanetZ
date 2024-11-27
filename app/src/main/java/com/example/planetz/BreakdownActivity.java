package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BreakdownActivity extends AppCompatActivity {

    private TextView transportationView;
    private TextView housingView;
    private TextView foodView;
    private TextView consumptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown);

        // 初始化 UI 元素
        transportationView = findViewById(R.id.transportationView);
        housingView = findViewById(R.id.housingView);
        foodView = findViewById(R.id.foodView);
        consumptionView = findViewById(R.id.consumptionView);

        // 获取传递过来的数据
        double transportation = getIntent().getDoubleExtra("transportation", 0);
        double housing = getIntent().getDoubleExtra("housing", 0);
        double food = getIntent().getDoubleExtra("food", 0);
        double consumption = getIntent().getDoubleExtra("consumption", 0);

        // 计算总排放量
        double total = transportation + housing + food + consumption;

        // 显示数据
        updateTextViews(transportation, housing, food, consumption, total);
    }

    private void updateTextViews(double transportation, double housing, double food, double consumption, double total) {
        transportationView.setText(formatText("Transportation", transportation, total));
        housingView.setText(formatText("Housing", housing, total));
        foodView.setText(formatText("Food", food, total));
        consumptionView.setText(formatText("Consumption", consumption, total));
    }

    private String formatText(String category, double value, double total) {
        double percentage = (value / total) * 100;
        return String.format("%s: %.2f tons (%.2f%%)", category, value, percentage);
    }


}
