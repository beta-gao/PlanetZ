// CalendarActivity.java
package com.example.planetz.DailyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Button nextPageButton;
    private String selectedDate;
    private static final String TAG = "CalendarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecotracker_main);

        Log.d(TAG, "onCreate called");

        calendarView = findViewById(R.id.calendarView);
        nextPageButton = findViewById(R.id.nextPageButton);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            Log.d(TAG, "Date selected: " + selectedDate);
        });

        nextPageButton.setOnClickListener(v -> {
            Log.d(TAG, "Next page button clicked");
            if (selectedDate == null) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "No date selected");
                return;
            }

            Intent intent = new Intent(this, DailyEmissionActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
            Log.d(TAG, "Starting DailyEmissionActivity with date " + selectedDate);
        });
    }
}
