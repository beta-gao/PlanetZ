package com.example.planetz.DailyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetz.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.planetz.LoginandRegister.UserManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DailyEmissionActivity extends AppCompatActivity {

    private TextView totalEmissionView, emptyTextView;
    private Button activitiesButton;
    private RecyclerView activityRecyclerView;
    private String selectedDate;
    private String userId;
    private double totalEmission = 0.0;
    private ActivityLogAdapter adapter;
    private List<ActivityLog> activityLogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_emissions);

        totalEmissionView = findViewById(R.id.emissionTitle);
        activitiesButton = findViewById(R.id.activitiesButton);
        activityRecyclerView = findViewById(R.id.activityRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);

        selectedDate = getIntent().getStringExtra("selectedDate");
        userId = UserManager.getInstance(this).getUserId();

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set up RecyclerView
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityLogAdapter(activityLogs);
        activityRecyclerView.setAdapter(adapter);

        // Fetch daily data
        fetchDailyData();

        activitiesButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityCategoryActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });
    }

    private void fetchDailyData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        totalEmission = documentSnapshot.getDouble("totalEmissions") != null ? documentSnapshot.getDouble("totalEmissions") : 0.0;
                        updateTotalEmission();

                        activityLogs.clear();

                        // Fetching individual activity emissions
                        for (String key : documentSnapshot.getData().keySet()) {
                            if (!key.equals("totalEmissions") && !key.equals("date")) {
                                double emissionValue = documentSnapshot.getDouble(key);
                                activityLogs.add(new ActivityLog(key, emissionValue));
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
                        // If no data exists for the day, create it
                        createDailyData();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show());
    }

    private void updateTotalEmission() {
        totalEmissionView.setText(String.format("Total Emission: %.2f kg CO2e", totalEmission));
    }

    private void createDailyData() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalEmissions", 0.0);
        data.put("transportation", 0.0);
        data.put("foodConsumption", 0.0);
        data.put("shoppingAndConsumption", 0.0);

        Map<String, Object> dateData = new HashMap<>();
        String[] dateParts = selectedDate.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1; // Calendar month is 0-based
        int day = Integer.parseInt(dateParts[2]);

        dateData.put("year", year);
        dateData.put("month", month + 1);
        dateData.put("day", day);

        // Calculate the week of the year
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        dateData.put("week", weekOfYear);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .set(data)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "New day created", Toast.LENGTH_SHORT).show();
                    db.collection("emission").document(userId)
                            .collection("dailyData").document(selectedDate)
                            .update("date", dateData);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error creating new day", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDailyData(); // Refresh the data when returning to this activity
    }
}