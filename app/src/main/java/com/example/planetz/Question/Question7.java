package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question7 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupLongHaulFlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question7);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupLongHaulFlights = findViewById(R.id.radioGroup_long_haul_flights);
        RadioButton backButton = findViewById(R.id.radio_back);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question7.this, Question6.class);
            startActivity(intent);
            finish();
        });

        radioGroupLongHaulFlights.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedFlights = null;

                if (checkedId == R.id.radio_none) {
                    selectedFlights = "None";
                } else if (checkedId == R.id.radio_1_2_flights) {
                    selectedFlights = "1-2 flights";
                } else if (checkedId == R.id.radio_3_5_flights) {
                    selectedFlights = "3-5 flights";
                } else if (checkedId == R.id.radio_6_10_flights) {
                    selectedFlights = "6-10 flights";
                } else if (checkedId == R.id.radio_more_than_10_flights) {
                    selectedFlights = "More than 10 flights";
                }

                if (selectedFlights != null) {
                    carbonFootprintData.setLongHaulFlights(selectedFlights);
                    Intent intent = new Intent(Question7.this, Question8.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question7.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question7.this, "Please select the number of long-haul flights.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}