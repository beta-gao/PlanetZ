package com.example.planetz.DailyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;

public class ActivityCategoryActivity extends AppCompatActivity {
    private Button transportationButton, foodButton, shoppingButton;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_category);

        transportationButton = findViewById(R.id.transportationButton);
        foodButton = findViewById(R.id.foodButton);
        shoppingButton = findViewById(R.id.shoppingButton);

        selectedDate = getIntent().getStringExtra("selectedDate");

        transportationButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TransportationActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });

        foodButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FoodActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });

        shoppingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });
    }
}