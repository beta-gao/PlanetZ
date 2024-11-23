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

public class Question8 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupDietType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question8);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupDietType = findViewById(R.id.radioGroup_diet_type);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupDietType.getCheckedRadioButtonId();
                String selectedDiet = null;

                if (selectedId == R.id.radio_vegetarian) {
                    selectedDiet = "Vegetarian";
                } else if (selectedId == R.id.radio_vegan) {
                    selectedDiet = "Vegan";
                } else if (selectedId == R.id.radio_pescatarian) {
                    selectedDiet = "Pescatarian (fish/seafood)";
                } else if (selectedId == R.id.radio_meat_based) {
                    selectedDiet = "Meat-based (eat all types of animal products)";
                }

                if (selectedDiet != null) {
                    carbonFootprintData.setDietType(selectedDiet);

                    if (selectedDiet.equals("Meat-based (eat all types of animal products)")) {
                        Intent intent = new Intent(Question8.this, Question9.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(Question8.this, Question10.class);
                        startActivity(intent);
                    }
                    finish();
                } else {
                    Toast.makeText(Question8.this, "Please select your diet type.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}