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

public class Question15 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupElectricityBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question15);

        carbonFootprintData = CarbonFootprintData.getInstance();

        radioGroupElectricityBill = findViewById(R.id.radioGroup_electricity_bill);
        Button nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupElectricityBill.getCheckedRadioButtonId();
                String selectedBillRange = null;

                if (selectedId == R.id.radio_under_50) {
                    selectedBillRange = "Under $50";
                } else if (selectedId == R.id.radio_50_100) {
                    selectedBillRange = "$50-$100";
                } else if (selectedId == R.id.radio_100_150) {
                    selectedBillRange = "$100-$150";
                } else if (selectedId == R.id.radio_150_200) {
                    selectedBillRange = "$150-$200";
                } else if (selectedId == R.id.radio_over_200) {
                    selectedBillRange = "Over $200";
                }

                if (selectedBillRange != null) {
                    carbonFootprintData.setMonthlyElectricityBill(selectedBillRange);

                    Intent intent = new Intent(Question15.this, Question16.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question15.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}