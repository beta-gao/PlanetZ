package com.example.planetz;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.planetz.model.AnnualFootprintData;

public class ComparisonActivity extends AppCompatActivity {

    private TextView comparisonTextView;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        comparisonTextView = findViewById(R.id.comparisonTextView);
        db = FirebaseFirestore.getInstance();

        // 获取用户的碳足迹数据
        AnnualFootprintData userFootprintData = AnnualFootprintData.getInstance();

        if (userFootprintData == null) {
            Toast.makeText(this, "User footprint data is not available.", Toast.LENGTH_SHORT).show();
            comparisonTextView.setText("No data available for comparison.");
            return;
        }

        double userTotalFootprint = userFootprintData.getTotal();

        // 假设用户所在国家（可以从用户设置或地理信息中获取）
        String userCountry = "Canada";

        // 从 Firestore 获取国家平均碳排放数据
        fetchCountryStats(userCountry, userTotalFootprint);
    }

    private void fetchCountryStats(String userCountry, double userTotalFootprint) {
        db.collection("globalStats").document("emissionsPerCapitaMap")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // 获取国家对应的平均碳排放数据
                            Double countryAverageFootprint = document.getDouble("countries." + userCountry);

                            if (countryAverageFootprint == null) {
                                comparisonTextView.setText("No data available for " + userCountry);
                                return;
                            }

                            // 生成比较结果文本
                            String comparisonResult = generateComparisonText(userCountry, userTotalFootprint, countryAverageFootprint);

                            // 显示比较结果
                            comparisonTextView.setText(comparisonResult);
                        } else {
                            Toast.makeText(this, "Global stats document not found.", Toast.LENGTH_SHORT).show();
                            comparisonTextView.setText("Error: Global stats document not found.");
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch data from Firestore.", Toast.LENGTH_SHORT).show();
                        comparisonTextView.setText("Error fetching comparison data.");
                    }
                });
    }

    private String generateComparisonText(String country, double userFootprint, double averageFootprint) {
        // 用户与国家平均碳足迹的比较
        double percentageDifference = ((userFootprint - averageFootprint) / averageFootprint) * 100;

        // 用户与全球目标（0）的比较
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
