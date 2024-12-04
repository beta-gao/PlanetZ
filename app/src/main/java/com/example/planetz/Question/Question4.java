package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question4 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupPublicTransportFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupPublicTransportFrequency = findViewById(R.id.radioGroup_public_transport_frequency);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question4.this, Question1.class); // 返回第一个问题
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupPublicTransportFrequency.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedFrequency;

                if (checkedId == R.id.radio_never) {
                    selectedFrequency = "Never";
                } else if (checkedId == R.id.radio_occasionally) {
                    selectedFrequency = "Occasionally (1-2 times/week)";
                } else if (checkedId == R.id.radio_frequently) {
                    selectedFrequency = "Frequently (3-4 times/week)";
                } else if (checkedId == R.id.radio_always) {
                    selectedFrequency = "Always (5+ times/week)";
                } else {
                    Toast.makeText(Question4.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                carbonFootprintData.setPublicTransportFrequency(selectedFrequency);
                Intent intent = new Intent(Question4.this, Question5.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Question4.this, "Please select how often you use public transport.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}