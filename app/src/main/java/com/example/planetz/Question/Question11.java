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

public class Question11 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHomeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question11);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupHomeType = findViewById(R.id.radioGroup_home_type);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupHomeType.getCheckedRadioButtonId();
                String selectedHomeType = null;

                if (selectedId == R.id.radio_detached) {
                    selectedHomeType = "Detached house";
                } else if (selectedId == R.id.radio_semi_detached) {
                    selectedHomeType = "Semi-detached house";
                } else if (selectedId == R.id.radio_townhouse) {
                    selectedHomeType = "Townhouse";
                } else if (selectedId == R.id.radio_condo) {
                    selectedHomeType = "Condo/Apartment";
                } else if (selectedId == R.id.radio_other) {
                    selectedHomeType = "Other";
                }

                if (selectedHomeType != null) {
                    carbonFootprintData.setHomeType(selectedHomeType);

                    Intent intent = new Intent(Question11.this, Question12.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question11.this, "Please select your home type before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}