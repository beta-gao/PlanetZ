package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question8 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupDietType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question8);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupDietType = findViewById(R.id.radioGroup_diet_type);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question8.this, Question7.class);
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupDietType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedDiet = null;

                if (checkedId == R.id.radio_vegetarian) {
                    selectedDiet = "Vegetarian";
                } else if (checkedId == R.id.radio_vegan) {
                    selectedDiet = "Vegan";
                } else if (checkedId == R.id.radio_pescatarian) {
                    selectedDiet = "Pescatarian (fish/seafood)";
                } else if (checkedId == R.id.radio_meat_based) {
                    selectedDiet = "Meat-based (eat all types of animal products)";
                }

                if (selectedDiet != null) {
                    carbonFootprintData.setDietType(selectedDiet);

                    Intent intent;
                    if (selectedDiet.equals("Meat-based (eat all types of animal products)")) {
                        intent = new Intent(Question8.this, Question9.class);
                    } else {
                        intent = new Intent(Question8.this, Question10.class);
                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question8.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question8.this, "Please select your diet type.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}