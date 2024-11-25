package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question5 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupPublicTransportTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupPublicTransportTime = findViewById(R.id.radioGroup_public_transport_time);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question5.this, Question4.class);
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupPublicTransportTime.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedTime = null;

            if (checkedId == R.id.radio_under_1_hour) {
                selectedTime = "Under 1 hour";
            } else if (checkedId == R.id.radio_1_3_hours) {
                selectedTime = "1-3 hours";
            } else if (checkedId == R.id.radio_3_5_hours) {
                selectedTime = "3-5 hours";
            } else if (checkedId == R.id.radio_5_10_hours) {
                selectedTime = "5-10 hours";
            } else if (checkedId == R.id.radio_more_than_10_hours) {
                selectedTime = "More than 10 hours";
            }

            if (selectedTime != null) {
                carbonFootprintData.setPublicTransportTime(selectedTime);
                Intent intent = new Intent(Question5.this, Question6.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Question5.this, "Please select the time you spend on public transport.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}