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

public class Question20 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupElectronicDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question20);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupElectronicDevices = findViewById(R.id.radioGroup_electronic_devices);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupElectronicDevices.getCheckedRadioButtonId();
                String selectedOption = null;

                if (selectedId == R.id.radio_none) {
                    selectedOption = "None";
                } else if (selectedId == R.id.radio_one) {
                    selectedOption = "1";
                } else if (selectedId == R.id.radio_two) {
                    selectedOption = "2";
                } else if (selectedId == R.id.radio_three_or_more) {
                    selectedOption = "3 or more";
                }

                if (selectedOption != null) {
                    carbonFootprintData.setElectronicDevicesPurchased(selectedOption);

                    Intent intent = new Intent(Question20.this, Question21.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question20.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}