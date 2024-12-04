package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question12 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHouseholdSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question12);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupHouseholdSize = findViewById(R.id.radioGroup_household_size);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question12.this, Question11.class); // 返回上一题
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupHouseholdSize.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedHouseholdSize = null;

                if (checkedId == R.id.radio_1) {
                    selectedHouseholdSize = "1";
                } else if (checkedId == R.id.radio_2) {
                    selectedHouseholdSize = "2";
                } else if (checkedId == R.id.radio_3_4) {
                    selectedHouseholdSize = "3-4";
                } else if (checkedId == R.id.radio_5_or_more) {
                    selectedHouseholdSize = "5 or more";
                }

                if (selectedHouseholdSize != null) {
                    carbonFootprintData.setHouseholdSize(selectedHouseholdSize);

                    Intent intent = new Intent(Question12.this, Question13.class); // 跳转下一题
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question12.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question12.this, "Please select your household size before proceeding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}