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

public class Question16 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupWaterHeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question16);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupWaterHeating = findViewById(R.id.radioGroup_water_heating);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupWaterHeating.getCheckedRadioButtonId();
                String selectedHeatingType = null;

                if (selectedId == R.id.radio_natural_gas) {
                    selectedHeatingType = "Natural Gas";
                } else if (selectedId == R.id.radio_electricity) {
                    selectedHeatingType = "Electricity";
                } else if (selectedId == R.id.radio_oil) {
                    selectedHeatingType = "Oil";
                } else if (selectedId == R.id.radio_propane) {
                    selectedHeatingType = "Propane";
                } else if (selectedId == R.id.radio_solar) {
                    selectedHeatingType = "Solar";
                } else if (selectedId == R.id.radio_other) {
                    selectedHeatingType = "Other";
                }

                if (selectedHeatingType != null) {
                    carbonFootprintData.setWaterHeatingType(selectedHeatingType);

                    Intent intent = new Intent(Question16.this, Question17.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question16.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}