package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question18 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupClothingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question18);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupClothingFrequency = findViewById(R.id.radioGroup_clothing_frequency);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question18.this, Question17.class);
            startActivity(intent);
            finish();
        });

        // 设置点击选项直接跳转功能
        radioGroupClothingFrequency.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedFrequency = null;

                if (checkedId == R.id.radio_monthly) {
                    selectedFrequency = "Monthly";
                } else if (checkedId == R.id.radio_quarterly) {
                    selectedFrequency = "Quarterly";
                } else if (checkedId == R.id.radio_annually) {
                    selectedFrequency = "Annually";
                } else if (checkedId == R.id.radio_rarely) {
                    selectedFrequency = "Rarely";
                }

                if (selectedFrequency != null) {
                    carbonFootprintData.setClothingPurchaseFrequency(selectedFrequency);

                    Intent intent = new Intent(Question18.this, Question19.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question18.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question18.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}