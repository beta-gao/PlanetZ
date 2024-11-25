package com.example.planetz;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.Calculator.ConsumptionCalculator;
import com.example.planetz.Calculator.FoodCalculator;
import com.example.planetz.Calculator.HousingCalculator;
import com.example.planetz.Calculator.TransportationCalculator;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.ConsumptionData;

public class ResultActivity extends AppCompatActivity {

    private TextView annualFootprintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 初始化 UI 元素
        annualFootprintView = findViewById(R.id.annualFootprint);

        // 获取本地 CarbonFootprintData
        CarbonFootprintData carbonFootprintData = CarbonFootprintData.getInstance();

        // 创建 ConsumptionData 实例
        ConsumptionData consumptionData = calculateConsumptionData(carbonFootprintData);

        // 获取年度碳足迹
        double annualFootprint = consumptionData.getTotal() ; // 转换为吨 CO2e

        // 更新 UI
        annualFootprintView.setText(String.format("Annual Footprint: %.2f tons CO2e", annualFootprint));
    }

    /**
     * 根据 CarbonFootprintData 创建 ConsumptionData 实例
     *
     * @param data CarbonFootprintData 数据对象
     * @return ConsumptionData 实例
     */
    private ConsumptionData calculateConsumptionData(CarbonFootprintData data) {
        // 计算各类别的碳排放
        double transportation = new TransportationCalculator().calculateTransportationEmission(data);
        double housing = new HousingCalculator().calculateHousingEmission(data);
        double food = new FoodCalculator().calculateFoodEmission(data);
        double consumption = new ConsumptionCalculator().calculateConsumptionEmission(
                data.getClothingPurchaseFrequency(),
                data.getSecondHandOrEcoFriendlyProducts(),
                Integer.parseInt(data.getElectronicDevicesPurchased()),
                data.getRecyclingFrequency()
        );

        // 创建 ConsumptionData 实例
        return new ConsumptionData(transportation, housing, food, consumption);
    }
}
