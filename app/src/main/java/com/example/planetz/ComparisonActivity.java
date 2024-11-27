package com.example.planetz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComparisonActivity extends AppCompatActivity {

    private Spinner countrySpinner;
    private TextView comparisonResult;
    private FirebaseFirestore db;
    private Map<String, Double> countryEmissions;
    private double userFootprint = 10.5; // 示例用户碳足迹，可动态传递

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        // 初始化 UI 元素
        countrySpinner = findViewById(R.id.countrySpinner);
        comparisonResult = findViewById(R.id.comparisonResult);

        // 初始化 Firebase
        db = FirebaseFirestore.getInstance();
        countryEmissions = new HashMap<>();

        // 加载国家数据
        loadCountryData();
    }

    private void loadCountryData() {
        db.collection("globalStats").document("emissionsPerCapitaMap")
                .get()
                .addOnSuccessListener(this::populateCountrySpinner)
                .addOnFailureListener(e -> comparisonResult.setText("Failed to load data: " + e.getMessage()));
    }

    private void populateCountrySpinner(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            Map<String, Object> countries = (Map<String, Object>) documentSnapshot.get("countries");
            List<String> countryNames = new ArrayList<>(countries.keySet());

            // 保存每个国家对应的碳排放值
            for (String country : countryNames) {
                countryEmissions.put(country, ((Number) countries.get(country)).doubleValue());
            }

            // 设置 Spinner 的适配器
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countrySpinner.setAdapter(adapter);

            // 设置 Spinner 的选择事件
            countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedCountry = countryNames.get(position);
                    updateComparisonResult(selectedCountry);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    comparisonResult.setText("Please select a country.");
                }
            });
        }
    }

    private void updateComparisonResult(String country) {
        Double nationalAverage = countryEmissions.get(country);

        if (nationalAverage != null) {
            double difference = userFootprint - nationalAverage;
            double percentage = Math.abs(difference) / nationalAverage * 100;

            String result;
            if (difference < 0) {
                result = String.format("Your carbon footprint is %.2f%% below the national average for %s.", percentage, country);
            } else if (difference > 0) {
                result = String.format("Your carbon footprint is %.2f%% above the national average for %s.", percentage, country);
            } else {
                result = String.format("Your carbon footprint matches the national average for %s.", country);
            }

            comparisonResult.setText(result);
        } else {
            comparisonResult.setText("Data for " + country + " is not available.");
        }
    }
}
