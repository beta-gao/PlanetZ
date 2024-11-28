package com.example.planetz.DisplayingAnnualFootprintResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.HomePageActivity;
import com.example.planetz.R;
import com.example.planetz.model.AnnualFootprintData;
import com.example.planetz.model.CountryAverage;

import java.util.ArrayList;
import java.util.List;

public class ComparisonActivity extends AppCompatActivity {

    private TextView comparisonTextView;
    private Spinner countrySpinner;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        comparisonTextView = findViewById(R.id.comparisonTextView);
        countrySpinner = findViewById(R.id.countrySpinner);
        backToHomeButton = findViewById(R.id.backToHomeButton);

        // 设置返回按钮点击事件
        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ComparisonActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        // 初始化国家列表到 Spinner
        initializeCountrySpinner();

        // 获取用户的碳足迹数据
        AnnualFootprintData userFootprintData = AnnualFootprintData.getInstance();

        if (userFootprintData == null) {
            Toast.makeText(this, "User footprint data is not available.", Toast.LENGTH_SHORT).show();
            comparisonTextView.setText("No data available for comparison.");
            return;
        }

        double userTotalFootprint = userFootprintData.getTotal();

        // 监听 Spinner 的选项变化
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = parent.getItemAtPosition(position).toString();
                compareWithSelectedCountry(selectedCountry, userTotalFootprint);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                comparisonTextView.setText("Please select a country to compare.");
            }
        });
    }

    private void initializeCountrySpinner() {
        CountryAverage countryAverage = CountryAverage.getInstance();
        List<String> countryList = new ArrayList<>(countryAverage.getAllAverages().keySet());

        // 设置 Spinner 的适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                countryList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);
    }

    private void compareWithSelectedCountry(String country, double userFootprint) {
        CountryAverage countryAverage = CountryAverage.getInstance();
        Double averageFootprint = countryAverage.getAverage(country);

        if (averageFootprint == null || averageFootprint == 0.0) {
            Toast.makeText(this, "No data available for " + country, Toast.LENGTH_SHORT).show();
            comparisonTextView.setText("No data available for " + country);
            return;
        }

        // 生成比较结果文本
        String comparisonResult = generateComparisonText(country, userFootprint, averageFootprint);

        // 显示比较结果
        comparisonTextView.setText(comparisonResult);
    }

    private String generateComparisonText(String country, double userFootprint, double averageFootprint) {
        double percentageDifference = ((userFootprint - averageFootprint) / averageFootprint) * 100;
        double differenceFromGlobalTarget = userFootprint;

        return String.format(
                "Your Total Carbon Footprint: %.2f tons CO2e\n\n" +
                        "Comparison with %s:\n" +
                        "- National Average: %.2f tons CO2e\n" +
                        "- You are %.2f%% %s the national average.\n\n" +
                        "Comparison with Global Target:\n" +
                        "- Target: 0 tons CO2e\n" +
                        "- You are %.2f tons CO2e above the global target.",
                userFootprint,
                country,
                averageFootprint,
                Math.abs(percentageDifference),
                percentageDifference > 0 ? "above" : "below",
                differenceFromGlobalTarget
        );
    }
}
