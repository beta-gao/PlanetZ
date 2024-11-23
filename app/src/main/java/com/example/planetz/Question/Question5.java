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

public class Question5 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupPublicTransportTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question5);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupPublicTransportTime = findViewById(R.id.radioGroup_public_transport_time);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupPublicTransportTime.getCheckedRadioButtonId();
                String selectedTime = null;

                if (selectedId == R.id.radio_under_1_hour) {
                    selectedTime = "Under 1 hour";
                } else if (selectedId == R.id.radio_1_3_hours) {
                    selectedTime = "1-3 hours";
                } else if (selectedId == R.id.radio_3_5_hours) {
                    selectedTime = "3-5 hours";
                } else if (selectedId == R.id.radio_5_10_hours) {
                    selectedTime = "5-10 hours";
                } else if (selectedId == R.id.radio_more_than_10_hours) {
                    selectedTime = "More than 10 hours";
                }

                if (selectedTime != null) {
                    carbonFootprintData.setPublicTransportTime(selectedTime);
                    Intent intent = new Intent(Question5.this, Question6.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question5.this, "Please select the time you spend on public transport.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}