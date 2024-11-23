package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.R;

public class Question3 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupAnnualMileage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question3);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupAnnualMileage = findViewById(R.id.radioGroup_annual_mileage);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupAnnualMileage.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    int annualMileage;
                    if (selectedId == R.id.radio_up_to_5000) {
                        annualMileage = 5000;
                    } else if (selectedId == R.id.radio_5000_10000) {
                        annualMileage = 10000;
                    } else if (selectedId == R.id.radio_10000_15000) {
                        annualMileage = 15000;
                    } else if (selectedId == R.id.radio_15000_20000) {
                        annualMileage = 20000;
                    } else if (selectedId == R.id.radio_20000_25000) {
                        annualMileage = 25000;
                    } else if (selectedId == R.id.radio_more_than_25000) {
                        annualMileage = 26000;
                    } else {
                        Toast.makeText(Question3.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    carbonFootprintData.setAnnualMileage(annualMileage);
                    Intent intent = new Intent(Question3.this, Question4.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question3.this, "Please select your annual mileage.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}