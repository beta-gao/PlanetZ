package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question6 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupShortHaulFlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question6);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupShortHaulFlights = findViewById(R.id.radioGroup_short_haul_flights);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question6.this, Question5.class);
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupShortHaulFlights.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedFlights = null;

                if (checkedId == R.id.radio_none) {
                    selectedFlights = "None";
                } else if (checkedId == R.id.radio_1_2_flights) {
                    selectedFlights = "1-2 flights";
                } else if (checkedId == R.id.radio_3_5_flights) {
                    selectedFlights = "3-5 flights";
                } else if (checkedId == R.id.radio_6_10_flights) {
                    selectedFlights = "6-10 flights";
                } else if (checkedId == R.id.radio_more_than_10_flights) {
                    selectedFlights = "More than 10 flights";
                }

                if (selectedFlights != null) {
                    carbonFootprintData.setShortHaulFlights(selectedFlights);
                    Intent intent = new Intent(Question6.this, Question7.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question6.this, "Please select the number of short-haul flights.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}