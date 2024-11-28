package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.Calculator.BigResult;
import com.example.planetz.Calculator.ConsumptionCalculator;
import com.example.planetz.Calculator.FoodCalculator;
import com.example.planetz.Calculator.HousingCalculator;
import com.example.planetz.Calculator.TransportationCalculator;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.AnnualFootprintData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

    private TextView resultTextView;
    private Button breakdownButton; // 跳转按钮
    private BigResult bigResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 初始化组件
        bigResult = new BigResult();
        resultTextView = findViewById(R.id.annualFootprint);
        breakdownButton = findViewById(R.id.buttonBreakdown);

        // 获取全局单例实例的 CarbonFootprintData
        CarbonFootprintData carbonFootprintData = CarbonFootprintData.getInstance();

        // 检查数据有效性
        if (carbonFootprintData == null) {
            Toast.makeText(this, "CarbonFootprintData is not initialized!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 调用 BigResult 计算总碳足迹
        double totalCarbonFootprint = bigResult.calculateTotalCarbonFootprint(
                carbonFootprintData,   // 交通相关数据
                carbonFootprintData,   // 住房相关数据
                carbonFootprintData,   // 消费相关数据
                carbonFootprintData    // 饮食相关数据
        );

        // 将计算结果保存到全局单例 AnnualFootprintData
        saveToAnnualFootprintData(totalCarbonFootprint);

        // 上传数据到 Firebase
        uploadCarbonFootprintDataToFirebase();
        uploadAnnualFootprintDataToFirebase();

        // 显示结果
        resultTextView.setText(String.format("Your total carbon footprint is %.2f tons of CO2e", totalCarbonFootprint));

        // 设置跳转按钮监听
        breakdownButton.setOnClickListener(v -> navigateToBreakdownActivity());
    }

    /**
     * 保存计算结果到全局单例 AnnualFootprintData
     *
     * @param totalCarbonFootprint 计算出的总碳足迹
     */
    private void saveToAnnualFootprintData(double totalCarbonFootprint) {
        AnnualFootprintData Adata = AnnualFootprintData.getInstance();

        if (Adata == null) {
            Toast.makeText(this, "AnnualFootprintData is not initialized!", Toast.LENGTH_SHORT).show();
            return;
        }

        TransportationCalculator cal1 = new TransportationCalculator();
        FoodCalculator cal2 = new FoodCalculator();
        HousingCalculator cal3 = new HousingCalculator();
        ConsumptionCalculator cal4 = new ConsumptionCalculator();

        CarbonFootprintData carbonFootprintData = CarbonFootprintData.getInstance();
        if (carbonFootprintData == null) {
            Toast.makeText(this, "CarbonFootprintData is not initialized!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 设置字段
        Adata.setTransportation(cal1.calculateTransportationEmission(carbonFootprintData));
        Adata.setFood(cal2.calculateFoodEmission(carbonFootprintData));
        Adata.setHousing(cal3.calculateHousingEmission(carbonFootprintData));
        Adata.setConsumption(cal4.calculateConsumptionEmission(carbonFootprintData));
        Adata.setTotal(totalCarbonFootprint);
    }

    /**
     * 上传 CarbonFootprintData 到 Firebase
     */
    private void uploadCarbonFootprintDataToFirebase() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CarbonFootprintData carbonFootprintData = CarbonFootprintData.getInstance();

        if (carbonFootprintData == null) {
            Toast.makeText(this, "CarbonFootprintData is not initialized!", Toast.LENGTH_SHORT).show();
            return;
        }

        carbonFootprintData.setUserId(userId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("carbonFootprints")
                .document(userId)
                .set(carbonFootprintData.toMap())
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "CarbonFootprintData uploaded successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to upload CarbonFootprintData: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    /**
     * 上传 AnnualFootprintData 到 Firebase
     */
    private void uploadAnnualFootprintDataToFirebase() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        AnnualFootprintData annualFootprintData = AnnualFootprintData.getInstance();

        if (annualFootprintData == null) {
            Toast.makeText(this, "AnnualFootprintData is not initialized!", Toast.LENGTH_SHORT).show();
            return;
        }

        annualFootprintData.setUserId(userId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("annualFootprints")
                .document(userId)
                .set(annualFootprintData.toMap())
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "AnnualFootprintData uploaded successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to upload AnnualFootprintData: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void navigateToBreakdownActivity() {
        Intent intent = new Intent(ResultActivity.this, BreakdownActivity.class);
        startActivity(intent);
    }
}
