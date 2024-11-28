package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question1 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupUsingVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupUsingVehicle = findViewById(R.id.radioGroup_using_vehicle);

        radioGroupUsingVehicle.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                boolean isUsingVehicle;
                if (checkedId == R.id.radio_yes) {
                    isUsingVehicle = true;
                } else if (checkedId == R.id.radio_no) {
                    isUsingVehicle = false;
                } else {
                    Toast.makeText(Question1.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                carbonFootprintData.setUsingVehicle(isUsingVehicle);

                Intent intent;
                if (isUsingVehicle) {
                    intent = new Intent(Question1.this, Question2.class);
                } else {
                    intent = new Intent(Question1.this, Question4.class);
                }
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Question1.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}