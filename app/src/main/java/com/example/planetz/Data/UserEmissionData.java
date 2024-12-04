package com.example.planetz.Data;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * The UserEmissionData class is used to store user carbon emission data.
 *
 * Data Structure:
 * UserEmissionData
 * │
 * └── dailyData
 *        ├── "2024-11-16"
 *        │       ├── consumption
 *        │       │       ├── "energyUse" : 1200.0
 *        │       │       ├── "foodConsumption" : 600.0
 *        │       │       ├── "shoppingAndConsumption" : 1500.0
 *        │       │       ├── "totalEmissions" : 3700.0
 *        │       │       └── "transportation" : 400.0
 *        │       └── date
 *        │               ├── "day" : 16
 *        │               ├── "month" : 11
 *        │               ├── "week" : 46  // the first week of a year contains at least 4 days
 *        │               └── "year" : 2024
 *        │
 *        ├── "2024-11-17"
 *                ├── consumption
 *                │       ├── "energyUse" : 1000.0
 *                │       ├── "foodConsumption" : 800.0
 *                │       ├── "shoppingAndConsumption" : 400.0
 *                │       ├── "totalEmissions" : 5400.0
 *                │       └── "transportation" : 3200.0
 *                └── date
 *                        ├── "day" : 17
 *                        ├── "month" : 11
 *                        ├── "week" : 47
 *                        └── "year" : 2024
 *
 */

public class UserEmissionData {
    // AnnualCarbonFootprint map, the key is date YYYY-MM-DD
    private double annualCarbonFootprint;

    // dailyData map, the key is date YYYY-MM-DD
    private Map<String, DailyData> dailyData;

    // method to set and get annualCarbonFootprint
    public UserEmissionData() {
        Log.d(TAG, "UserEmissionData: Default constructor called");
    }

    public UserEmissionData(double annualCarbonFootprint, Map<String, DailyData> dailyData) {
        Log.d(TAG, "UserEmissionData: Constructor with parameters called");
        setAnnualCarbonFootprint(annualCarbonFootprint);
        setDailyData(dailyData, true);
    }

    public UserEmissionData getUserEmissionData() {
        Log.d(TAG, "getUserEmissionData: Returning this instance");
        return this;
    }

    public void setAnnualCarbonFootprint(double annualCarbonFootprint) {
        Log.d(TAG, "setAnnualCarbonFootprint: Setting annualCarbonFootprint to " + annualCarbonFootprint);
        this.annualCarbonFootprint = annualCarbonFootprint;
    }

    public double getAnnualCarbonFootprint() {
        Log.d(TAG, "getAnnualCarbonFootprint: Returning annualCarbonFootprint " + annualCarbonFootprint);
        return annualCarbonFootprint;
    }

    // method to set and get dailyData
    public void setDailyData(Map<String, DailyData> dailyData, boolean needDeepCopy) {
        Log.d(TAG, "setDailyData: Setting dailyData with needDeepCopy = " + needDeepCopy);
        if (needDeepCopy) {
            this.dailyData = deepCopyDailyData(dailyData);
        } else {
            this.dailyData = dailyData;
        }
    }

    private Map<String, DailyData> deepCopyDailyData(Map<String, DailyData> originalMap) {
        Log.d(TAG, "deepCopyDailyData: Performing deep copy of dailyData");
        Map<String, DailyData> copiedMap = new HashMap<>();
        for (Map.Entry<String, DailyData> entry : originalMap.entrySet()) {
            String key = entry.getKey();
            DailyData originalDailyData = entry.getValue();

            Consumption originalConsumption = originalDailyData.getConsumption();
            Date originalDate = originalDailyData.getDate();

            Consumption copiedConsumption = new Consumption(
                    originalConsumption.getEnergyUse(),
                    originalConsumption.getFoodConsumption(),
                    originalConsumption.getShoppingAndConsumption(),
                    originalConsumption.getTransportation()
            );

            Date copiedDate = new Date(
                    originalDate.getDay(),
                    originalDate.getMonth(),
                    originalDate.getWeek(),
                    originalDate.getYear()
            );

            DailyData copiedDailyData = new DailyData(copiedConsumption, copiedDate);

            copiedMap.put(key, copiedDailyData);
        }
        Log.d(TAG, "deepCopyDailyData: Deep copy completed");
        return copiedMap;
    }

    public Map<String, DailyData> getDailyData() {
        Log.d(TAG, "getDailyData: Returning dailyData");
        return dailyData;
    }

    public Map<String, DailyData> filterByTimePeriod(String timePeriod, String filterValue) {
        Log.d(TAG, "filterByTimePeriod: Filtering by timePeriod = " + timePeriod + ", filterValue = " + filterValue);
        Map<String, DailyData> filteredData = new HashMap<>();
        if (dailyData == null) {
            Log.w(TAG, "filterByTimePeriod: dailyData is null");
            return filteredData;
        }

        for (Map.Entry<String, DailyData> entry : dailyData.entrySet()) {
            String dateKey = entry.getKey();
            DailyData data = entry.getValue();

            try {
                switch (timePeriod.toLowerCase()) {
                    case "daily":
                        if (dateKey.equals(filterValue)) {
                            filteredData.put(dateKey, data);
                        }
                        break;
                    case "weekly":
                        String[] parts = filterValue.split("-W");
                        if (parts.length == 2) {
                            int week = Integer.parseInt(parts[1]);
                            if (data.getDate().getWeek() == week) {
                                filteredData.put(dateKey, data);
                            }
                        } else {
                            Log.w(TAG, "filterByTimePeriod: Invalid weekly filter format: " + filterValue);
                        }
                        break;
                    case "monthly":
                        String[] monthlyParts = filterValue.split("-");
                        if (monthlyParts.length == 2) {
                            int month = Integer.parseInt(monthlyParts[1]);
                            if (data.getDate().getMonth() == month) {
                                filteredData.put(dateKey, data);
                            }
                        } else {
                            Log.w(TAG, "filterByTimePeriod: Invalid monthly filter format: " + filterValue);
                        }
                        break;
                    default:
                        Log.e(TAG, "filterByTimePeriod: Unsupported time period: " + timePeriod);
                        throw new IllegalArgumentException("Unsupported time period: " + timePeriod);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "filterByTimePeriod: NumberFormatException while parsing filterValue: " + filterValue, e);
            }
        }
        Log.d(TAG, "filterByTimePeriod: Filtered data size: " + filteredData.size());
        return filteredData;
    }


    public List<String> getWeeklyKeys() {
        Log.d(TAG, "getWeeklyKeys: Generating weekly keys");
        Set<String> weeklyKeys = new HashSet<>();

        for (DailyData data : dailyData.values()) {
            int week = data.getDate().getWeek();
            int year = data.getDate().getYear();
            weeklyKeys.add(year + "-W" + week); // Format: YYYY-WW
        }
        Log.d(TAG, "getWeeklyKeys: Weekly keys generated: " + weeklyKeys);
        return new ArrayList<>(weeklyKeys);
    }

    public List<String> getMonthlyKeys() {
        Log.d(TAG, "getMonthlyKeys: Generating monthly keys");
        Set<String> monthlyKeys = new HashSet<>();

        for (DailyData data : dailyData.values()) {
            int month = data.getDate().getMonth();
            int year = data.getDate().getYear();
            monthlyKeys.add(year + "-" + (month < 10 ? "0" : "") + month); // Format: YYYY-MM
        }
        Log.d(TAG, "getMonthlyKeys: Monthly keys generated: " + monthlyKeys);
        return new ArrayList<>(monthlyKeys);
    }
}
