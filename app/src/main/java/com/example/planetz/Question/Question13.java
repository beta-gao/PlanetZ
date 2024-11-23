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

public class Question13 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupHomeSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question13);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupHomeSize = findViewById(R.id.radioGroup_home_size);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupHomeSize.getCheckedRadioButtonId();
                String selectedHomeSize = null;

                if (selectedId == R.id.radio_under_1000) {
                    selectedHomeSize = "Under 1000 sq. ft.";
                } else if (selectedId == R.id.radio_1000_2000) {
                    selectedHomeSize = "1000-2000 sq. ft.";
                } else if (selectedId == R.id.radio_over_2000) {
                    selectedHomeSize = "Over 2000 sq. ft.";
                }

                if (selectedHomeSize != null) {
                    carbonFootprintData.setHomeSize(selectedHomeSize);

                    Intent intent = new Intent(Question13.this, Question14.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question13.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}