package com.example.planetz.DailyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetz.Data.EmissionManager;
import com.example.planetz.EcoTrackerMainActivity;
import com.example.planetz.LoginandRegister.UserManager;
import com.example.planetz.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DailyEmissionActivity extends AppCompatActivity implements EmissionManager.EmissionDataListener {

    private TextView totalEmissionView, emptyTextView;
    private Button activitiesButton;
    private RecyclerView activityRecyclerView;
    private String selectedDate;
    private String userId;
    private double totalEmission = 0.0;
    private ActivityLogAdapter adapter;
    private List<ActivityLog> activityLogs = new ArrayList<>();
    private static final String TAG = "DailyEmissionActivity";

    private EmissionManager emissionManager;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_emissions);

        Log.d(TAG, "onCreate called");

        totalEmissionView = findViewById(R.id.emissionTitle);
        activitiesButton = findViewById(R.id.activitiesButton);
        activityRecyclerView = findViewById(R.id.activityRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);
        Button backButton = findViewById(R.id.backButton);

        selectedDate = getIntent().getStringExtra("selectedDate");
        Log.d(TAG, "Selected date: " + selectedDate);

        if (selectedDate == null) {
            Log.e(TAG, "Selected date is null");
            finish();
            return;
        }

        userId = UserManager.getInstance(this).getUserId();
        Log.d(TAG, "User ID: " + userId);

        if (userId == null) {
            Log.e(TAG, "User not logged in");
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();
        updateDateData(selectedDate);

        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityLogAdapter(activityLogs);
        activityRecyclerView.setAdapter(adapter);
        Log.d(TAG, "RecyclerView set up");

        // Initialize EmissionManager and set listener
        emissionManager = EmissionManager.getInstance(this);
        emissionManager.initDailyData(selectedDate);
        emissionManager.addListener(this);

        activitiesButton.setOnClickListener(v -> {
            Log.d(TAG, "Activities button clicked");
            Intent intent = new Intent(this, ActivityCategoryActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> {
            Log.d(TAG, "Back button clicked");
            Intent intent = new Intent(DailyEmissionActivity.this, EcoTrackerMainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onDataChanged(Map<String, Object> dailyData) {
        Log.d(TAG, "Data changed, updating UI");
        if (dailyData != null && dailyData.containsKey("consumption")) {
            Map<String, Object> consumption = (Map<String, Object>) dailyData.get("consumption");

            double totalEmissions = getDoubleFromMap(consumption, "totalEmissions");
            totalEmissionView.setText(String.format("Total Emission: %.2f kg CO2e", totalEmissions));

            // Update activity logs
            activityLogs.clear();
            for (String category : consumption.keySet()) {
                if (!category.equals("totalEmissions")) {
                    Object categoryData = consumption.get(category);
                    if (categoryData instanceof Map) {
                        Map<String, Object> subcategories = (Map<String, Object>) categoryData;
                        for (String subcategory : subcategories.keySet()) {
                            if (!subcategory.equals("total")) {
                                Object value = subcategories.get(subcategory);
                                if (value instanceof Number) {
                                    double emissionValue = ((Number) value).doubleValue();
                                    String activityName = category + " - " + subcategory;
                                    activityLogs.add(new ActivityLog(activityName, emissionValue));
                                }
                            }
                        }
                    }
                }
            }
            if (activityLogs.isEmpty()) {
                emptyTextView.setVisibility(View.VISIBLE);
                activityRecyclerView.setVisibility(View.GONE);
            } else {
                emptyTextView.setVisibility(View.GONE);
                activityRecyclerView.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        } else {
            // Handle the case where 'consumption' is not present
            totalEmissionView.setText("Total Emission: 0.00 kg CO2e");
            activityLogs.clear();
            emptyTextView.setVisibility(View.VISIBLE);
            activityRecyclerView.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            Log.d(TAG, "No data for the selected date");
        }
    }

    private double getDoubleFromMap(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            return 0.0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emissionManager.removeListeners();
    }

    @Override
    public void onBackPressed() {
        // Perform any necessary actions before navigating back
        Log.d(TAG, "Back button pressed, performing cleanup");
        super.onBackPressed();
    }

    private void updateDateData(String selectedDate) {
        // 解析 selectedDate 字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = sdf.parse(selectedDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH) + 1; // 月份从 0 开始，需要加 1
            int year = cal.get(Calendar.YEAR);
            int week = cal.get(Calendar.WEEK_OF_YEAR);

            Map<String, Object> dateData = new HashMap<>();
            dateData.put("day", day);
            dateData.put("month", month);
            dateData.put("year", year);
            dateData.put("week", week);

            // 创建或更新数据，逐级构建 Map
            Map<String, Object> dateMap = new HashMap<>();
            dateMap.put("date", dateData);

            Map<String, Object> dailyDataMap = new HashMap<>();
            dailyDataMap.put(selectedDate, dateMap);

            Map<String, Object> rootData = new HashMap<>();
            rootData.put("dailyData", dailyDataMap);

            // 更新到 Firebase
            DocumentReference docRef = db.collection("emissions").document(userId);
            docRef.set(rootData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Date data updated successfully."))
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating date data", e));

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "Error parsing selected date", e);
        }
    }



}
