package com.example.planetz.DailyActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class EnergyUseActivity extends AppCompatActivity {

    private static final String TAG = "EnergyUseActivity";
    private String selectedDate;
    private String userId;
    private FirebaseFirestore db;

    private EditText electricityCostInput;
    private EditText naturalGasCostInput;
    private EditText waterCostInput;
    private Button saveEnergyUseButton;
    private ImageView goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_use);

        // 初始化Firebase
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        selectedDate = getIntent().getStringExtra("selectedDate");
        if (selectedDate == null) {
            Log.e(TAG, "Selected date is null");
            finish();
            return;
        }

        electricityCostInput = findViewById(R.id.electricityCostInput);
        naturalGasCostInput = findViewById(R.id.naturalGasCostInput);
        waterCostInput = findViewById(R.id.waterCostInput);
        saveEnergyUseButton = findViewById(R.id.saveEnergyUseButton);
        goBackButton = findViewById(R.id.goBackButton);

        saveEnergyUseButton.setOnClickListener(v -> {
            String electricityStr = electricityCostInput.getText().toString();
            String naturalGasStr = naturalGasCostInput.getText().toString();
            String waterStr = waterCostInput.getText().toString();

            double electricityCost = electricityStr.isEmpty() ? 0.0 : Double.parseDouble(electricityStr);
            double naturalGasCost = naturalGasStr.isEmpty() ? 0.0 : Double.parseDouble(naturalGasStr);
            double waterCost = waterStr.isEmpty() ? 0.0 : Double.parseDouble(waterStr);

            double calculatedValue = electricityCost * 0.5 + naturalGasCost * 0.3 + waterCost * 0.2;

            updateEnergyUse(calculatedValue);
        });

        goBackButton.setOnClickListener(v -> {
            finish(); // 结束当前活动，返回上一个活动
        });
    }

    private void updateEnergyUse(double calculatedValue) {
        DocumentReference docRef = db.collection("emissions").document(userId);

        // 获取当前的 totalEmissions 和旧的 energyUse 值
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Map<String, Object> data = documentSnapshot.getData();
            if (data == null) {
                data = new HashMap<>();
            }

            // 获取 dailyData
            Map<String, Object> dailyData;
            Object dailyDataObj = data.get("dailyData");
            if (dailyDataObj instanceof Map) {
                dailyData = (Map<String, Object>) dailyDataObj;
            } else {
                dailyData = new HashMap<>();
            }

            // 获取 selectedDate 对应的 dateData
            Map<String, Object> dateData;
            Object dateDataObj = dailyData.get(selectedDate);
            if (dateDataObj instanceof Map) {
                dateData = (Map<String, Object>) dateDataObj;
            } else {
                dateData = new HashMap<>();
            }

            // 获取 consumptionData
            Map<String, Object> consumptionData;
            Object consumptionDataObj = dateData.get("consumption");
            if (consumptionDataObj instanceof Map) {
                consumptionData = (Map<String, Object>) consumptionDataObj;
            } else {
                consumptionData = new HashMap<>();
            }

            // 获取旧的 energyUse 值
            double oldEnergyUseValue = 0.0;
            Object energyUseObj = consumptionData.get("energyUse");
            if (energyUseObj instanceof Number) {
                oldEnergyUseValue = ((Number) energyUseObj).doubleValue();
            }

            // 获取旧的 totalEmissions 值
            double oldTotalEmissions = 0.0;
            Object totalEmissionsObj = consumptionData.get("totalEmissions");
            if (totalEmissionsObj instanceof Number) {
                oldTotalEmissions = ((Number) totalEmissionsObj).doubleValue();
            }

            // 计算新的 totalEmissions 值
            double newTotalEmissions = oldTotalEmissions - oldEnergyUseValue + calculatedValue;

            // 更新 energyUse 和 totalEmissions
            consumptionData.put("energyUse", calculatedValue);
            consumptionData.put("totalEmissions", newTotalEmissions);

            // 更新 dateData 和 dailyData
            dateData.put("consumption", consumptionData);
            dailyData.put(selectedDate, dateData);

            // 构建更新的数据
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("dailyData", dailyData);

            // 更新到 Firebase
            docRef.set(updatedData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Energy use and total emissions updated successfully."))
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating energy use and total emissions", e));

        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document", e);
        });
    }
}
