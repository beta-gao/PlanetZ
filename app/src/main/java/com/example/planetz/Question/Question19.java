package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question19 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupSecondHandEco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question19);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupSecondHandEco = findViewById(R.id.radioGroup_second_hand_eco);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 返回按钮功能
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question19.this, Question18.class);
            startActivity(intent);
            finish();
        });

        // 点击选项直接跳转到下一个问题
        radioGroupSecondHandEco.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedOption = null;

                if (checkedId == R.id.radio_yes_regularly) {
                    selectedOption = "Yes, regularly";
                } else if (checkedId == R.id.radio_yes_occasionally) {
                    selectedOption = "Yes, occasionally";
                } else if (checkedId == R.id.radio_no) {
                    selectedOption = "No";
                }

                if (selectedOption != null) {
                    carbonFootprintData.setSecondHandOrEcoFriendlyProducts(selectedOption);

                    Intent intent = new Intent(Question19.this, Question20.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question19.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question19.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}