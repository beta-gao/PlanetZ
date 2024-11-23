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

public class Question18 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupClothingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question18);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupClothingFrequency = findViewById(R.id.radioGroup_clothing_frequency);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupClothingFrequency.getCheckedRadioButtonId();
                String selectedFrequency = null;

                if (selectedId == R.id.radio_monthly) {
                    selectedFrequency = "Monthly";
                } else if (selectedId == R.id.radio_quarterly) {
                    selectedFrequency = "Quarterly";
                } else if (selectedId == R.id.radio_annually) {
                    selectedFrequency = "Annually";
                } else if (selectedId == R.id.radio_rarely) {
                    selectedFrequency = "Rarely";
                }

                if (selectedFrequency != null) {
                    carbonFootprintData.setClothingPurchaseFrequency(selectedFrequency);

                    Intent intent = new Intent(Question18.this, Question19.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question18.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}