package com.example.planetz.Calculator;

import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;

public class FoodCalculator {

    public static double calculate(CarbonFootprintData data) {
        double totalEmission = 0.0;

        // 1. 饮食类型
        String dietType = data.getDietType();
        if (dietType != null) {
            switch (dietType) {
                case "Vegetarian":
                    totalEmission += 1000; // 单位：kg CO2e
                    break;
                case "Vegan":
                    totalEmission += 500;
                    break;
                case "Pescatarian":
                    totalEmission += 1500;
                    break;
                case "Meat-based":
                    totalEmission += 2500;
                    break;
            }
        }

        // 如果选择了 "Meat-based"，计算额外的问题
        if ("Meat-based".equals(dietType)) {
            totalEmission += calculateMeatConsumption(data);
        }

        // 2. 食物浪费频率
        String foodWasteFrequency = data.getFoodWasteFrequency();
        if (foodWasteFrequency != null) {
            switch (foodWasteFrequency) {
                case "Never":
                    totalEmission += 0;
                    break;
                case "Rarely":
                    totalEmission += 23.4;
                    break;
                case "Occasionally":
                    totalEmission += 70.2;
                    break;
                case "Frequently":
                    totalEmission += 140.4;
                    break;
            }
        }

        return totalEmission;
    }

    private static double calculateMeatConsumption(CarbonFootprintData data) {
        double meatEmission = 0.0;

        // 吃猪肉的频率
        String porkFrequency = data.getPorkFrequency();
        if (porkFrequency != null) {
            switch (porkFrequency) {
                case "Daily":
                    meatEmission += 1450;
                    break;
                case "Frequently (3-5 times/week)":
                    meatEmission += 860;
                    break;
                case "Occasionally (1-2 times/week)":
                    meatEmission += 450;
                    break;
                case "Never":
                    meatEmission += 0;
                    break;
            }
        }

        // 吃鸡肉的频率
        String chickenFrequency = data.getChickenFrequency();
        if (chickenFrequency != null) {
            switch (chickenFrequency) {
                case "Daily":
                    meatEmission += 950;
                    break;
                case "Frequently (3-5 times/week)":
                    meatEmission += 600;
                    break;
                case "Occasionally (1-2 times/week)":
                    meatEmission += 200;
                    break;
                case "Never":
                    meatEmission += 0;
                    break;
            }
        }

        // 吃鱼的频率
        String fishFrequency = data.getFishFrequency();
        if (fishFrequency != null) {
            switch (fishFrequency) {
                case "Daily":
                    meatEmission += 800;
                    break;
                case "Frequently (3-5 times/week)":
                    meatEmission += 500;
                    break;
                case "Occasionally (1-2 times/week)":
                    meatEmission += 150;
                    break;
                case "Never":
                    meatEmission += 0;
                    break;
            }
        }

        return meatEmission;
    }
}
