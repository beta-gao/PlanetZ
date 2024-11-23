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

public class Question14 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHomeHeating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question14);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupHomeHeating = findViewById(R.id.radioGroup_home_heating);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupHomeHeating.getCheckedRadioButtonId();
                String selectedHeatingType = null;

                if (selectedId == R.id.radio_natural_gas) {
                    selectedHeatingType = "Natural Gas";
                } else if (selectedId == R.id.radio_electricity) {
                    selectedHeatingType = "Electricity";
                } else if (selectedId == R.id.radio_oil) {
                    selectedHeatingType = "Oil";
                } else if (selectedId == R.id.radio_propane) {
                    selectedHeatingType = "Propane";
                } else if (selectedId == R.id.radio_wood) {
                    selectedHeatingType = "Wood";
                } else if (selectedId == R.id.radio_other) {
                    selectedHeatingType = "Other";
                }

                if (selectedHeatingType != null) {
                    carbonFootprintData.setHomeHeatingType(selectedHeatingType);

                    Intent intent = new Intent(Question14.this, Question15.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question14.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}