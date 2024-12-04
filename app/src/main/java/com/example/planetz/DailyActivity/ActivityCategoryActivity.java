package com.example.planetz.DailyActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;

public class ActivityCategoryActivity extends AppCompatActivity {

    private static final String TAG = "ActivityCategoryActivity";
    private String selectedDate;

    private Button transportationButton;
    private Button foodButton;
    private Button shoppingButton;
    private Button energyUseButton;
    private ImageView goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_category);

        selectedDate = getIntent().getStringExtra("selectedDate");
        if (selectedDate == null) {
            Log.e(TAG, "Selected date is null");
            finish();
            return;
        }

        // 初始化按钮
        transportationButton = findViewById(R.id.transportationButton);
        foodButton = findViewById(R.id.foodButton);
        shoppingButton = findViewById(R.id.shoppingButton);
        energyUseButton = findViewById(R.id.energyUseButton); // 初始化 energyUseButton
        goBackButton = findViewById(R.id.goBackButton);

        // 设置点击事件监听器
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

        energyUseButton.setOnClickListener(v -> { // 设置 energyUseButton 的点击事件
            Intent intent = new Intent(this, EnergyUseActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        });

        goBackButton.setOnClickListener(v -> {
            finish();
        });
    }
}
