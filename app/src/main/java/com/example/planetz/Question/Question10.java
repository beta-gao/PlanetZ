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

public class Question10 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupFoodWaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question10);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupFoodWaste = findViewById(R.id.radioGroup_food_waste);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupFoodWaste.getCheckedRadioButtonId();
                String selectedFoodWasteFrequency = null;

                if (selectedId == R.id.radio_never) {
                    selectedFoodWasteFrequency = "Never";
                } else if (selectedId == R.id.radio_rarely) {
                    selectedFoodWasteFrequency = "Rarely";
                } else if (selectedId == R.id.radio_occasionally) {
                    selectedFoodWasteFrequency = "Occasionally";
                } else if (selectedId == R.id.radio_frequently) {
                    selectedFoodWasteFrequency = "Frequently";
                }

                if (selectedFoodWasteFrequency != null) {
                    carbonFootprintData.setFoodWasteFrequency(selectedFoodWasteFrequency);

                    Intent intent = new Intent(Question10.this, Question11.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question10.this, "Please select your food waste frequency.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}