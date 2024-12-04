package com.example.planetz.EcoGaugh;

import com.example.planetz.Data.DailyData;
import com.example.planetz.Data.Date;

import java.util.HashMap;
import java.util.Map;

public class EmissionUtils {

    private static final String TAG = "EmissionUtils";

    public static double calculateTotalEmissions(Map<String, DailyData> data) {
        double totalEmissions = 0.0;
        int i = 0;
        for (DailyData dailyData : data.values()) {
            totalEmissions += dailyData.getConsumption().getTotalEmissions();
            i++;
        }
        return totalEmissions/i;
    }

    public static Map<String, Double> calculateEmissionsBreakdown(Map<String, DailyData> data) {
        double transportationTotal = 0.0;
        double energyUseTotal = 0.0;
        double foodConsumptionTotal = 0.0;
        double shoppingAndConsumptionTotal = 0.0;
        int i = 0;

        for (DailyData dailyData : data.values()) {
            transportationTotal += dailyData.getConsumption().getTransportation();
            energyUseTotal += dailyData.getConsumption().getEnergyUse();
            foodConsumptionTotal += dailyData.getConsumption().getFoodConsumption();
            shoppingAndConsumptionTotal += dailyData.getConsumption().getShoppingAndConsumption();
            i++;
        }

        Map<String, Double> breakdown = new HashMap<>();
        breakdown.put("Transportation", transportationTotal/i);
        breakdown.put("Energy Use", energyUseTotal/i);
        breakdown.put("Food Consumption", foodConsumptionTotal/i);
        breakdown.put("Shopping and Consumption", shoppingAndConsumptionTotal/i);

        return breakdown;
    }

    public static Map<String, Map<String, DailyData>> groupDataByPeriod(Map<String, DailyData> data, String timePeriod) {
        Map<String, Map<String, DailyData>> groupedData = new HashMap<>();

        for (Map.Entry<String, DailyData> entry : data.entrySet()) {
            String dateKey = entry.getKey();
            DailyData dailyData = entry.getValue();
            Date date = dailyData.getDate();

            String periodKey;
            if (timePeriod.equals("Weekly")) {
                int year = date.getYear();
                int week = date.getWeek();
                periodKey = String.format("%d-W%d", year, week);
            } else if (timePeriod.equals("Monthly")) {
                int year = date.getYear();
                int month = date.getMonth();
                periodKey = String.format("%d-%02d", year, month);
            } else {
                periodKey = dateKey;
            }

            if (!groupedData.containsKey(periodKey)) {
                groupedData.put(periodKey, new HashMap<>());
            }

            groupedData.get(periodKey).put(dateKey, dailyData);
        }

        return groupedData;
    }

    public static Map<String, Double> calculateEmissionsTrend(Map<String, DailyData> data, String timePeriod) {
        Map<String, Double> trendData = new HashMap<>();

        for (Map.Entry<String, DailyData> entry : data.entrySet()) {
            String dateKey = entry.getKey();
            DailyData dailyData = entry.getValue();
            double totalEmissions = dailyData.getConsumption().getTotalEmissions();

            trendData.put(dateKey, totalEmissions);
        }

        return trendData;
    }
}
