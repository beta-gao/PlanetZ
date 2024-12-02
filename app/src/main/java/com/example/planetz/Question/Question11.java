package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question11 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHomeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question11);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupHomeType = findViewById(R.id.radioGroup_home_type);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question11.this, Question10.class); // 返回第十题
            startActivity(intent);
            finish();
        });

        // 设置RadioGroup监听器，点击选项直接跳转到下一题
        radioGroupHomeType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedHomeType = null;

                if (checkedId == R.id.radio_detached) {
                    selectedHomeType = "Detached house";
                } else if (checkedId == R.id.radio_semi_detached) {
                    selectedHomeType = "Semi-detached house";
                } else if (checkedId == R.id.radio_townhouse) {
                    selectedHomeType = "Townhouse";
                } else if (checkedId == R.id.radio_condo) {
                    selectedHomeType = "Condo/Apartment";
                } else if (checkedId == R.id.radio_other) {
                    selectedHomeType = "Other";
                }

                if (selectedHomeType != null) {
                    carbonFootprintData.setHomeType(selectedHomeType);

                    Intent intent = new Intent(Question11.this, Question12.class); // 跳转第十二题
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question11.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question11.this, "Please select your home type before proceeding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}