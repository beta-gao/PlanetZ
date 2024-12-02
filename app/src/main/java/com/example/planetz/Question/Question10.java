package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question10 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupFoodWaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question10);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupFoodWaste = findViewById(R.id.radioGroup_food_waste);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question10.this, Question9.class); // 返回到第九题
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupFoodWaste.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedFoodWasteFrequency = null;

                if (checkedId == R.id.radio_never) {
                    selectedFoodWasteFrequency = "Never";
                } else if (checkedId == R.id.radio_rarely) {
                    selectedFoodWasteFrequency = "Rarely";
                } else if (checkedId == R.id.radio_ocasionally) {
                    selectedFoodWasteFrequency = "Occasionally";
                } else if (checkedId == R.id.radio_frequently) {
                    selectedFoodWasteFrequency = "Frequently";
                }

                if (selectedFoodWasteFrequency != null) {
                    carbonFootprintData.setFoodWasteFrequency(selectedFoodWasteFrequency);

                    Intent intent = new Intent(Question10.this, Question11.class); // 跳转到第十一题
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question10.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question10.this, "Please select your food waste frequency.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}