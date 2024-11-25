package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question21 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupRecyclingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question21);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupRecyclingFrequency = findViewById(R.id.radioGroup_recycling_frequency);
        RadioButton backButton = findViewById(R.id.radio_back);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question21.this, Question20.class);
            startActivity(intent);
            finish();
        });

        radioGroupRecyclingFrequency.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String selectedFrequency = selectedRadioButton.getText().toString();

                carbonFootprintData.setRecyclingFrequency(selectedFrequency);

                Intent intent = new Intent(Question21.this, ResultActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Question21.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}