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

public class Question19 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupSecondHandEco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question19);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupSecondHandEco = findViewById(R.id.radioGroup_second_hand_eco);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupSecondHandEco.getCheckedRadioButtonId();
                String selectedOption = null;

                if (selectedId == R.id.radio_yes_regularly) {
                    selectedOption = "Yes, regularly";
                } else if (selectedId == R.id.radio_yes_occasionally) {
                    selectedOption = "Yes, occasionally";
                } else if (selectedId == R.id.radio_never) {
                    selectedOption = "No";
                }

                if (selectedOption != null) {
                    carbonFootprintData.setSecondHandOrEcoFriendlyProducts(selectedOption);

                    Intent intent = new Intent(Question19.this, Question20.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question19.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}