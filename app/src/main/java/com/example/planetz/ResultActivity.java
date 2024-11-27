package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.Calculator.ConsumptionCalculator;
import com.example.planetz.Calculator.FoodCalculator;
import com.example.planetz.Calculator.HousingCalculator;
import com.example.planetz.Calculator.TransportationCalculator;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.ConsumptionData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private TextView annualFootprintView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 初始化 UI 元素
        annualFootprintView = findViewById(R.id.annualFootprint);

        // 初始化 Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // 获取本地 CarbonFootprintData
        CarbonFootprintData carbonFootprintData = CarbonFootprintData.getInstance();

        // 创建 ConsumptionData 实例
        ConsumptionData consumptionData = calculateConsumptionData(carbonFootprintData);

        // 获取年度碳足迹
        double annualFootprint = consumptionData.getTotal(); // 转换为吨 CO2e

        // 更新 UI
        annualFootprintView.setText(String.format("Annual Footprint: %.2f tons CO2e", annualFootprint));

        // 上传年度碳足迹到 Firebase
        uploadFootprintToFirebase(annualFootprint);

        // 跳转到 BreakdownActivity 并传递数据
        navigateToBreakdownActivity(consumptionData);
    }

    private ConsumptionData calculateConsumptionData(CarbonFootprintData data) {
        double transportation = new TransportationCalculator().calculateTransportationEmission(data);
        double housing = new HousingCalculator().calculateHousingEmission(data);
        double food = new FoodCalculator().calculateFoodEmission(data);
        double consumption = new ConsumptionCalculator().calculateConsumptionEmission(
                data.getClothingPurchaseFrequency(),
                data.getSecondHandOrEcoFriendlyProducts(),
                Integer.parseInt(data.getElectronicDevicesPurchased()),
                data.getRecyclingFrequency()
        );

        return new ConsumptionData(transportation, housing, food, consumption);
    }

    private void uploadFootprintToFirebase(double annualFootprint) {
        Map<String, Object> data = new HashMap<>();
        data.put("annualCarbonFootprint", annualFootprint);

        db.collection("emissions").document("user001")
                .set(data)
                .addOnSuccessListener(aVoid -> Toast.makeText(ResultActivity.this, "Data uploaded successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ResultActivity.this, "Failed to upload data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void navigateToBreakdownActivity(ConsumptionData consumptionData) {
        Intent intent = new Intent(ResultActivity.this, BreakdownActivity.class);
        intent.putExtra("transportation", consumptionData.getTransportation());
        intent.putExtra("housing", consumptionData.getHousing());
        intent.putExtra("food", consumptionData.getFood());
        intent.putExtra("consumption", consumptionData.getConsumption());
        startActivity(intent);
    }
    private void navigateToComparisonActivity(double annualFootprint) {
        Intent intent = new Intent(ResultActivity.this, ComparisonActivity.class);
        intent.putExtra("userFootprint", annualFootprint);
        startActivity(intent);
    }
}
