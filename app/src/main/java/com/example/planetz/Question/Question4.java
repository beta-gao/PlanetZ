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

public class Question4 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupPublicTransportFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question4);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupPublicTransportFrequency = findViewById(R.id.radioGroup_public_transport_frequency);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupPublicTransportFrequency.getCheckedRadioButtonId();
                String selectedFrequency = null;

                if (selectedId == R.id.radio_never) {
                    selectedFrequency = "Never";
                } else if (selectedId == R.id.radio_occasionally) {
                    selectedFrequency = "Occasionally (1-2 times/week)";
                } else if (selectedId == R.id.radio_frequently) {
                    selectedFrequency = "Frequently (3-4 times/week)";
                } else if (selectedId == R.id.radio_always) {
                    selectedFrequency = "Always (5+ times/week)";
                }

                if (selectedFrequency != null) {
                    carbonFootprintData.setPublicTransportFrequency(selectedFrequency);
                    Intent intent = new Intent(Question4.this, Question5.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question4.this, "Please select how often you use public transport.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}