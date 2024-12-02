package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question16 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupWaterHeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question16);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupWaterHeating = findViewById(R.id.radioGroup_water_heating);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question16.this, Question15.class);
            startActivity(intent);
            finish();
        });

        // 设置点击选项直接跳转功能
        radioGroupWaterHeating.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedHeatingType = null;

                if (checkedId == R.id.radio_natural_gas) {
                    selectedHeatingType = "Natural Gas";
                } else if (checkedId == R.id.radio_electricity) {
                    selectedHeatingType = "Electricity";
                } else if (checkedId == R.id.radio_oil) {
                    selectedHeatingType = "Oil";
                } else if (checkedId == R.id.radio_propane) {
                    selectedHeatingType = "Propane";
                } else if (checkedId == R.id.radio_solar) {
                    selectedHeatingType = "Solar";
                } else if (checkedId == R.id.radio_other) {
                    selectedHeatingType = "Other";
                }

                if (selectedHeatingType != null) {
                    carbonFootprintData.setWaterHeatingType(selectedHeatingType);
                    Intent intent = new Intent(Question16.this, Question17.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question16.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question16.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}