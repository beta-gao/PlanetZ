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

public class Question12 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHouseholdSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question12);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupHouseholdSize = findViewById(R.id.radioGroup_household_size);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupHouseholdSize.getCheckedRadioButtonId();
                String selectedHouseholdSize = null;

                if (selectedId == R.id.radio_1) {
                    selectedHouseholdSize = "1";
                } else if (selectedId == R.id.radio_2) {
                    selectedHouseholdSize = "2";
                } else if (selectedId == R.id.radio_3_4) {
                    selectedHouseholdSize = "3-4";
                } else if (selectedId == R.id.radio_5_or_more) {
                    selectedHouseholdSize = "5 or more";
                }

                if (selectedHouseholdSize != null) {
                    carbonFootprintData.setHouseholdSize(selectedHouseholdSize);

                    Intent intent = new Intent(Question12.this, Question13.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question12.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}