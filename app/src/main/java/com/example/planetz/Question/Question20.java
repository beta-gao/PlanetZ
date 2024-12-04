package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question20 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupElectronicDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question20);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupElectronicDevices = findViewById(R.id.radioGroup_electronic_devices);
        RadioButton backButton = findViewById(R.id.radio_back);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question20.this, Question19.class);
            startActivity(intent);
            finish();
        });

        radioGroupElectronicDevices.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedOption = null;

                if (checkedId == R.id.radio_none) {
                    selectedOption = "None";
                } else if (checkedId == R.id.radio_one) {
                    selectedOption = "1";
                } else if (checkedId == R.id.radio_two) {
                    selectedOption = "2";
                } else if (checkedId == R.id.radio_three_or_more) {
                    selectedOption = "3 or more";
                }

                if (selectedOption != null) {
                    carbonFootprintData.setElectronicDevicesPurchased(selectedOption);

                    Intent intent = new Intent(Question20.this, Question21.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question20.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question20.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}