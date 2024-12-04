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

public class ShoppingActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingActivity";
    private String selectedDate;
    private String userId;
    private FirebaseFirestore db;

    private Spinner shoppingTypeSpinner;
    private EditText itemsCountInput;
    private Button saveShoppingEmissionsButton;
    private ImageView goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

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

        shoppingTypeSpinner = findViewById(R.id.shoppingTypeSpinner);
        itemsCountInput = findViewById(R.id.itemsCountInput);
        saveShoppingEmissionsButton = findViewById(R.id.saveShoppingEmissionsButton);
        goBackButton = findViewById(R.id.goBackButton);

        saveShoppingEmissionsButton.setOnClickListener(v -> {
            String countStr = itemsCountInput.getText().toString();
            if (!countStr.isEmpty()) {
                int count = Integer.parseInt(countStr);
                String shoppingType = shoppingTypeSpinner.getSelectedItem().toString();
                double emissionFactor = getShoppingEmissionFactor(shoppingType);
                double calculatedValue = count * emissionFactor;
                updateShoppingAndConsumption(calculatedValue);
            } else {
                Log.e(TAG, "Input is empty");
            }
        });

        goBackButton.setOnClickListener(v -> {
            finish(); // 结束当前活动，返回上一个活动
        });
    }

    private double getShoppingEmissionFactor(String shoppingType) {
        switch (shoppingType) {
            case "Buy New Clothes":
                return 12.0;
            case "Buy Electronics":
                return 300.0;
            case "Other Purchases":
                return 20.0;
            default:
                return 0.0;
        }
    }

    private void updateShoppingAndConsumption(double calculatedValue) {
        DocumentReference docRef = db.collection("emissions").document(userId);

        // 获取当前的 totalEmissions 和旧的 shoppingAndConsumption 值
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

            // 获取旧的 shoppingAndConsumption 值
            double oldShoppingValue = 0.0;
            Object shoppingObj = consumptionData.get("shoppingAndConsumption");
            if (shoppingObj instanceof Number) {
                oldShoppingValue = ((Number) shoppingObj).doubleValue();
            }

            // 获取旧的 totalEmissions 值
            double oldTotalEmissions = 0.0;
            Object totalEmissionsObj = consumptionData.get("totalEmissions");
            if (totalEmissionsObj instanceof Number) {
                oldTotalEmissions = ((Number) totalEmissionsObj).doubleValue();
            }

            // 计算新的 totalEmissions 值
            double newTotalEmissions = oldTotalEmissions - oldShoppingValue + calculatedValue;

            // 更新 shoppingAndConsumption 和 totalEmissions
            consumptionData.put("shoppingAndConsumption", calculatedValue);
            consumptionData.put("totalEmissions", newTotalEmissions);

            // 更新 dateData 和 dailyData
            dateData.put("consumption", consumptionData);
            dailyData.put(selectedDate, dateData);

            // 构建更新的数据
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("dailyData", dailyData);

            // 更新到 Firebase
            docRef.set(updatedData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Shopping and total emissions updated successfully."))
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating shopping and total emissions", e));

        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document", e);
        });
    }

}
