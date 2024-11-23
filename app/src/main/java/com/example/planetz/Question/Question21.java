package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.R;

public class Question21 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupRecyclingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question21);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupRecyclingFrequency = findViewById(R.id.radioGroup_recycling_frequency);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupRecyclingFrequency.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedFrequency = selectedRadioButton.getText().toString();

                    carbonFootprintData.setRecyclingFrequency(selectedFrequency);

                    Intent intent = new Intent(Question21.this, ResultActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question21.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}