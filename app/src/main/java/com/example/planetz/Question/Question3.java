package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question3 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupAnnualMileage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupAnnualMileage = findViewById(R.id.radioGroup_annual_mileage);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question3.this, Question2.class);
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupAnnualMileage.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                int annualMileage = 0;

                if (checkedId == R.id.radio_up_to_5000) {
                    annualMileage = 5000;
                } else if (checkedId == R.id.radio_5000_10000) {
                    annualMileage = 10000;
                } else if (checkedId == R.id.radio_10000_15000) {
                    annualMileage = 15000;
                } else if (checkedId == R.id.radio_15000_20000) {
                    annualMileage = 20000;
                } else if (checkedId == R.id.radio_20000_25000) {
                    annualMileage = 25000;
                } else if (checkedId == R.id.radio_more_than_25000) {
                    annualMileage = 26000;
                } else {
                    Toast.makeText(Question3.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                carbonFootprintData.setAnnualMileage(annualMileage);
                Intent intent = new Intent(Question3.this, Question4.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Question3.this, "Please select your annual mileage.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}