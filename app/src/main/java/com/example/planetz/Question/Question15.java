package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;

public class Question15 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupElectricityBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question15);

        carbonFootprintData = CarbonFootprintData.getInstance();
        radioGroupElectricityBill = findViewById(R.id.radioGroup_electricity_bill);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Question15.this, Question14.class);
            startActivity(intent);
            finish();
        });

        // 设置点击选项直接跳转功能
        radioGroupElectricityBill.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                String selectedBillRange = null;

                if (checkedId == R.id.radio_under_50) {
                    selectedBillRange = "Under $50";
                } else if (checkedId == R.id.radio_50_100) {
                    selectedBillRange = "$50-$100";
                } else if (checkedId == R.id.radio_100_150) {
                    selectedBillRange = "$100-$150";
                } else if (checkedId == R.id.radio_150_200) {
                    selectedBillRange = "$150-$200";
                } else if (checkedId == R.id.radio_over_200) {
                    selectedBillRange = "Over $200";
                }

                if (selectedBillRange != null) {
                    carbonFootprintData.setMonthlyElectricityBill(selectedBillRange); // 确保设置了合法值
                    Intent intent = new Intent(Question15.this, Question16.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question15.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Question15.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}