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

public class Question17 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupRenewableEnergy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question17);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupRenewableEnergy = findViewById(R.id.radioGroup_renewable_energy);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupRenewableEnergy.getCheckedRadioButtonId();
                String selectedRenewableEnergy = null;

                if (selectedId == R.id.radio_yes_primary) {
                    selectedRenewableEnergy = "Yes, primarily (more than 50% of energy use)";
                } else if (selectedId == R.id.radio_yes_partial) {
                    selectedRenewableEnergy = "Yes, partially (less than 50% of energy use)";
                } else if (selectedId == R.id.radio_no) {
                    selectedRenewableEnergy = "No";
                }

                if (selectedRenewableEnergy != null) {
                    carbonFootprintData.setRenewableEnergyUse(selectedRenewableEnergy);

                    Intent intent = new Intent(Question17.this, Question18.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question17.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}