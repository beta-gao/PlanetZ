package com.example.planetz.EcoGaugh;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.data.DailyData;
import com.example.planetz.data.UserEmissionData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmissionsDashboard extends AppCompatActivity {

    private static EmissionsDashboard instance;
    private UserEmissionData userEmissionData;

    private static final String TAG = "EmissionsDashboard";

    private EmissionsDashboard() {
        Log.d(TAG, "EmissionsDashboard: Singleton instance created");
    }

    public static synchronized EmissionsDashboard getInstance() {
        if (instance == null) {
            instance = new EmissionsDashboard();
        }
        return instance;
    }

    public void setUserEmissionData(UserEmissionData userEmissionData) {
        if (userEmissionData == null) {
            Log.w(TAG, "setUserEmissionData: Received null userEmissionData.");
        } else {
            Log.d(TAG, "setUserEmissionData: UserEmissionData has been set.");
        }
        this.userEmissionData = userEmissionData;
    }

    public List<String> getDailyOptions() {
        Log.d(TAG, "getDailyOptions: Fetching daily options");
        List<String> dates = new ArrayList<>();
        if (userEmissionData != null) {
            dates.addAll(userEmissionData.getDailyData().keySet());
        } else {
            Log.w(TAG, "getDailyOptions: userEmissionData is null");
        }
        Log.d(TAG, "getDailyOptions: Retrieved daily options: " + dates);
        return dates;
    }


    public List<String> getWeeklyOptions() {
        Log.d(TAG, "getWeeklyOptions: Fetching weekly options");
        Set<String> weeks = new HashSet<>();
        if (userEmissionData != null) {
            Map<String, DailyData> dailyData = userEmissionData.getDailyData();
            for (DailyData data : dailyData.values()) {
                try {
                    String year = String.valueOf(data.getDate().getYear());
                    int week = data.getDate().getWeek();
                    String weekString = String.format("%s-W%d", year, week);
                    weeks.add(weekString);
                } catch (Exception e) {
                    Log.e(TAG, "getWeeklyOptions: Error processing data for weekly options", e);
                }
            }
        } else {
            Log.w(TAG, "getWeeklyOptions: userEmissionData is null");
        }
        List<String> weekList = new ArrayList<>(weeks);
        Collections.sort(weekList);
        Log.d(TAG, "getWeeklyOptions: Retrieved weekly options: " + weekList);
        return weekList;
    }

    public List<String> getMonthlyOptions() {
        Log.d(TAG, "getMonthlyOptions: Fetching monthly options");
        Set<String> months = new HashSet<>();
        if (userEmissionData != null) {
            Map<String, DailyData> dailyData = userEmissionData.getDailyData();
            for (DailyData data : dailyData.values()) {
                try {
                    String year = String.valueOf(data.getDate().getYear());
                    int month = data.getDate().getMonth();
                    String monthString = String.format("%s-%02d", year, month);
                    months.add(monthString);
                } catch (Exception e) {
                    Log.e(TAG, "getMonthlyOptions: Error processing data for monthly options", e);
                }
            }
        } else {
            Log.w(TAG, "getMonthlyOptions: userEmissionData is null");
        }
        List<String> monthList = new ArrayList<>(months);
        Collections.sort(monthList);
        Log.d(TAG, "getMonthlyOptions: Retrieved monthly options: " + monthList);
        return monthList;
    }

    // Function A: Display total emissions
    public String getTotalEmissionsOverview(String timePeriod, String filterValue) {
        Log.d(TAG, "getTotalEmissionsOverview: Calculating total emissions for " + timePeriod + " " + filterValue);
        Map<String, DailyData> filteredData = userEmissionData.filterByTimePeriod(timePeriod, filterValue);
        double totalEmissions = EmissionUtils.calculateTotalEmissions(filteredData);
        String result = String.format("You’ve emitted %.2f kg CO2e on %s.", totalEmissions, filterValue);
        Log.d(TAG, "getTotalEmissionsOverview: Result - " + result);
        return result;
    }

    public String getAverageEmissionsOverview(String timePeriod) {
        Log.d(TAG, "getAverageEmissionsOverview: Calculating average emissions for " + timePeriod);
        Map<String, DailyData> allData = userEmissionData.getDailyData();
        Map<String, Map<String, DailyData>> groupedData = EmissionUtils.groupDataByPeriod(allData, timePeriod);
        double totalEmissions = 0.0;
        int count = 0;

        for (Map<String, DailyData> periodData : groupedData.values()) {
            double periodTotal = EmissionUtils.calculateTotalEmissions(periodData);
            totalEmissions += periodTotal;
            count++;
        }

        double averageEmissions = (count > 0) ? totalEmissions / count : 0.0;
        String result = String.format("Your average %s emissions per instance are %.2f kg CO2.", timePeriod.toLowerCase(), averageEmissions);
        Log.d(TAG, "getAverageEmissionsOverview: Result - " + result);
        return result;
    }

    // Function B: Display emissions breakdown by category
    public Map<String, Double> getEmissionsBreakdown(String timePeriod, String filterValue) {
        Log.d(TAG, "getEmissionsBreakdown: Getting emissions breakdown for " + timePeriod + " " + filterValue);
        if (userEmissionData == null) {
            Log.w(TAG, "getEmissionsBreakdown: UserEmissionData is null. Cannot calculate breakdown.");
            return Collections.emptyMap();
        }

        Map<String, DailyData> filteredData = userEmissionData.filterByTimePeriod(timePeriod, filterValue);
        if (filteredData == null || filteredData.isEmpty()) {
            Log.w(TAG, "getEmissionsBreakdown: No data available after filtering by " + timePeriod + ": " + filterValue);
            return Collections.emptyMap();
        }
        Map<String, Double> breakdown = EmissionUtils.calculateEmissionsBreakdown(filteredData);
        Log.d(TAG, "getEmissionsBreakdown: Breakdown calculated - " + breakdown);
        return breakdown;
    }

    // Function C: Generate trend data
    public Map<String, Double> getEmissionsTrend(String timePeriod, String filterValue) {
        Log.d(TAG, "getEmissionsTrend: Getting emissions trend for " + timePeriod + " " + filterValue);
        Map<String, DailyData> filteredData = userEmissionData.filterByTimePeriod(timePeriod, filterValue);
        Map<String, Double> trendData = EmissionUtils.calculateEmissionsTrend(filteredData, timePeriod);
        Log.d(TAG, "getEmissionsTrend: Trend data calculated - " + trendData);
        return trendData;
    }

    // Function E: Compare to average
    public String compareToCountryAverage(String selectedCountry, Map<String, Double> countryEmissions, String timePeriod, String filterValue) {
        Log.d(TAG, "compareToCountryAverage: Comparing to country average for " + selectedCountry);
        double countryAverage = countryEmissions.getOrDefault(selectedCountry, 0.0);

        Map<String, DailyData> filteredData = userEmissionData.filterByTimePeriod(timePeriod, filterValue);
        double userTotalEmissions = EmissionUtils.calculateTotalEmissions(filteredData);

        double difference = userTotalEmissions - countryAverage;
        String result;
        if (difference > 0) {
            result = String.format("Your emissions are %.2f kg CO2e above the average emissions of %s.", difference, selectedCountry);
        } else {
            result = String.format("Your emissions are %.2f kg CO2e below the average emissions of %s.", Math.abs(difference), selectedCountry);
        }
        Log.d(TAG, "compareToCountryAverage: Result - " + result);
        return result;
    }
}
