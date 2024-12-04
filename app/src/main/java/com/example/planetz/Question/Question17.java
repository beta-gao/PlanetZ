package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question17 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupRenewableEnergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question17);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupRenewableEnergy = findViewById(R.id.radioGroup_renewable_energy);
        RadioButton backButton = findViewById(R.id.radio_back);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question17.this, Question16.class);
            startActivity(intent);
            finish();
        });

        radioGroupRenewableEnergy.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedRenewableEnergy = null;

                if (checkedId == R.id.radio_yes_primary) {
                    selectedRenewableEnergy = "Yes, primarily (more than 50% of energy use)";
                } else if (checkedId == R.id.radio_yes_partial) {
                    selectedRenewableEnergy = "Yes, partially (less than 50% of energy use)";
                } else if (checkedId == R.id.radio_no) {
                    selectedRenewableEnergy = "No";
                }

                if (selectedRenewableEnergy != null) {
                    carbonFootprintData.setRenewableEnergyUse(selectedRenewableEnergy);

                    Intent intent = new Intent(Question17.this, Question18.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question17.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question17.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}