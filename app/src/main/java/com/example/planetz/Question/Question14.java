package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question14 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHomeHeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question14);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupHomeHeating = findViewById(R.id.radioGroup_home_heating);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 返回按钮设置点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question14.this, Question13.class);
            startActivity(intent);
            finish();
        });

        // 设置选项直接跳转逻辑
        radioGroupHomeHeating.setOnCheckedChangeListener((group, checkedId) -> {
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
                } else if (checkedId == R.id.radio_wood) {
                    selectedHeatingType = "Wood";
                } else if (checkedId == R.id.radio_other) {
                    selectedHeatingType = "Other";
                }

                if (selectedHeatingType != null) {
                    carbonFootprintData.setHomeHeatingType(selectedHeatingType);
                    Intent intent = new Intent(Question14.this, Question15.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question14.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question14.this, "Please select a home heating option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}