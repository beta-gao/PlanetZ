package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question13 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHomeSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question13);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupHomeSize = findViewById(R.id.radioGroup_home_size);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question13.this, Question12.class); // 返回上一题
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupHomeSize.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedHomeSize = null;

                if (checkedId == R.id.radio_under_1000) {
                    selectedHomeSize = "Under 1000 sq. ft.";
                } else if (checkedId == R.id.radio_1000_2000) {
                    selectedHomeSize = "1000-2000 sq. ft.";
                } else if (checkedId == R.id.radio_over_2000) {
                    selectedHomeSize = "Over 2000 sq. ft.";
                }

                if (selectedHomeSize != null) {
                    carbonFootprintData.setHomeSize(selectedHomeSize);

                    Intent intent = new Intent(Question13.this, Question14.class); // 跳转下一题
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question13.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question13.this, "Please select your home size before proceeding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}