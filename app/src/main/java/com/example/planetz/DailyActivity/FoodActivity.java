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

public class FoodActivity extends AppCompatActivity {

    private static final String TAG = "FoodActivity";
    private String selectedDate;
    private String userId;
    private FirebaseFirestore db;

    private Spinner mealTypeSpinner;
    private EditText servingsInput;
    private Button saveFoodEmissionsButton;
    private ImageView goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

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

        mealTypeSpinner = findViewById(R.id.mealTypeSpinner);
        servingsInput = findViewById(R.id.servingsInput);
        saveFoodEmissionsButton = findViewById(R.id.saveFoodEmissionsButton);
        goBackButton = findViewById(R.id.goBackButton);

        saveFoodEmissionsButton.setOnClickListener(v -> {
            String servingsStr = servingsInput.getText().toString();
            if (!servingsStr.isEmpty()) {
                int servings = Integer.parseInt(servingsStr);
                String mealType = mealTypeSpinner.getSelectedItem().toString();
                double emissionFactor = getMealEmissionFactor(mealType);
                double calculatedValue = servings * emissionFactor;
                updateFoodConsumption(calculatedValue);
            } else {
                Log.e(TAG, "Input is empty");
            }
        });

        goBackButton.setOnClickListener(v -> {
            finish(); // 结束当前活动，返回上一个活动
        });
    }

    private double getMealEmissionFactor(String mealType) {
        switch (mealType) {
            case "Beef":
                return 6.85;
            case "Pork":
                return 3.97;
            case "Chicken":
                return 2.60;
            case "Fish":
                return 2.19;
            case "Plant-based":
                return 1.37;
            default:
                return 0.0;
        }
    }

    private void updateFoodConsumption(double calculatedValue) {
        DocumentReference docRef = db.collection("emissions").document(userId);

        // 获取当前的 totalEmissions 和旧的 foodConsumption 值
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

            // 获取旧的 foodConsumption 值
            double oldFoodConsumptionValue = 0.0;
            Object foodConsumptionObj = consumptionData.get("foodConsumption");
            if (foodConsumptionObj instanceof Number) {
                oldFoodConsumptionValue = ((Number) foodConsumptionObj).doubleValue();
            }

            // 获取旧的 totalEmissions 值
            double oldTotalEmissions = 0.0;
            Object totalEmissionsObj = consumptionData.get("totalEmissions");
            if (totalEmissionsObj instanceof Number) {
                oldTotalEmissions = ((Number) totalEmissionsObj).doubleValue();
            }

            // 计算新的 totalEmissions 值
            double newTotalEmissions = oldTotalEmissions - oldFoodConsumptionValue + calculatedValue;

            // 更新 foodConsumption 和 totalEmissions
            consumptionData.put("foodConsumption", calculatedValue);
            consumptionData.put("totalEmissions", newTotalEmissions);

            // 更新 dateData 和 dailyData
            dateData.put("consumption", consumptionData);
            dailyData.put(selectedDate, dateData);

            // 构建更新的数据
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("dailyData", dailyData);

            // 更新到 Firebase
            docRef.set(updatedData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Food consumption and total emissions updated successfully."))
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating food consumption and total emissions", e));

        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document", e);
        });
    }

}
