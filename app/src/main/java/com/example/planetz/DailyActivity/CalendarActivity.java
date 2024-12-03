package com.example.planetz.DailyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;

public class CalendarActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private Button nextPageButton;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecotracker_main);

        calendarView = findViewById(R.id.calendarView);
        nextPageButton = findViewById(R.id.nextPageButton);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        });

        nextPageButton.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }

            // 跳转到 DailyEmissionActivity，并传递选中的日期
            Intent intent = new Intent(this, DailyEmissionActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });
    }
}