package com.example.planetz.DailyActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class TransportationActivity extends AppCompatActivity {

    private static final String TAG = "TransportationActivity";
    private String selectedDate;
    private String userId;
    private FirebaseFirestore db;

    private Spinner transportationTypeSpinner;
    private Button saveTransportationEmissionsButton;
    private ImageView goBackButton;

    // 动态添加的控件
    private LinearLayout dynamicLayout;
    private EditText distanceInput; // 用于输入距离
    private Spinner vehicleTypeSpinner; // 用于选择车辆类型
    private EditText timeInput; // 用于输入时间
    private EditText flightCountInput; // 用于输入航班次数
    private Spinner flightTypeSpinner; // 用于选择航班类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

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

        transportationTypeSpinner = findViewById(R.id.transportationTypeSpinner);
        saveTransportationEmissionsButton = findViewById(R.id.saveTransportationEmissionsButton);
        goBackButton = findViewById(R.id.goBackButton);

        dynamicLayout = findViewById(R.id.dynamicLayout);

        transportationTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDynamicInputs(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        saveTransportationEmissionsButton.setOnClickListener(v -> {
            calculateAndUpdateEmissions();
        });

        goBackButton.setOnClickListener(v -> {
            finish(); // 结束当前活动，返回上一个活动
        });
    }

    private void updateDynamicInputs(int position) {
        dynamicLayout.removeAllViews();

        switch (position) {
            case 0:
                // Drive Personal Vehicle
                addDistanceInput();
                addVehicleTypeSpinner();
                break;
            case 1:
                // Take Public Transportation
                addPublicTransportTypeSpinner();
                addTimeInput();
                break;
            case 2:
                // Cycling or Walking
                addDistanceInput();
                break;
            case 3:
                // Flight
                addFlightCountInput();
                addFlightTypeSpinner();
                break;
        }
    }

    private void addDistanceInput() {
        distanceInput = new EditText(this);
        distanceInput.setHint("enter kilometers");
        distanceInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dynamicLayout.addView(distanceInput);
    }

    private void addVehicleTypeSpinner() {
        TextView textView = new TextView(this);
        textView.setText("choose vehicle type");
        dynamicLayout.addView(textView);

        vehicleTypeSpinner = new Spinner(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(adapter);
        dynamicLayout.addView(vehicleTypeSpinner);
    }

    private void addTimeInput() {
        timeInput = new EditText(this);
        timeInput.setHint("输入时间（小时）");
        timeInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dynamicLayout.addView(timeInput);
    }

    private void addPublicTransportTypeSpinner() {
        TextView textView = new TextView(this);
        textView.setText("选择公共交通类型：");
        dynamicLayout.addView(textView);

        // 如果需要区分公交、火车、地铁，可以添加对应的 Spinner
        // 这里因为三者的排放因子相同，所以可以省略
    }

    private void addFlightCountInput() {
        flightCountInput = new EditText(this);
        flightCountInput.setHint("输入飞行次数");
        flightCountInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        dynamicLayout.addView(flightCountInput);
    }

    private void addFlightTypeSpinner() {
        TextView textView = new TextView(this);
        textView.setText("选择航班类型：");
        dynamicLayout.addView(textView);

        flightTypeSpinner = new Spinner(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.flight_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flightTypeSpinner.setAdapter(adapter);
        dynamicLayout.addView(flightTypeSpinner);
    }

    private void calculateAndUpdateEmissions() {
        int position = transportationTypeSpinner.getSelectedItemPosition();
        double calculatedValue = 0.0;

        switch (position) {
            case 0:
                // Drive Personal Vehicle
                if (distanceInput != null && vehicleTypeSpinner != null) {
                    String distanceStr = distanceInput.getText().toString();
                    if (!distanceStr.isEmpty()) {
                        double distance = Double.parseDouble(distanceStr);
                        String vehicleType = vehicleTypeSpinner.getSelectedItem().toString();
                        double emissionFactor = getVehicleEmissionFactor(vehicleType);
                        calculatedValue = distance * emissionFactor;
                    }
                }
                break;
            case 1:
                // Take Public Transportation
                if (timeInput != null) {
                    String timeStr = timeInput.getText().toString();
                    if (!timeStr.isEmpty()) {
                        double time = Double.parseDouble(timeStr);
                        calculatedValue = time * 1.57;
                    }
                }
                break;
            case 2:
                // Cycling or Walking
                if (distanceInput != null) {
                    String distanceStr = distanceInput.getText().toString();
                    if (!distanceStr.isEmpty()) {
                        // Emission is zero
                        calculatedValue = 0.0;
                    }
                }
                break;
            case 3:
                // Flight
                if (flightCountInput != null && flightTypeSpinner != null) {
                    String countStr = flightCountInput.getText().toString();
                    if (!countStr.isEmpty()) {
                        int count = Integer.parseInt(countStr);
                        String flightType = flightTypeSpinner.getSelectedItem().toString();
                        double emissionFactor = getFlightEmissionFactor(flightType);
                        calculatedValue = count * emissionFactor;
                    }
                }
                break;
        }

        if (calculatedValue >= 0) {
            updateTransportation(calculatedValue);
        } else {
            Log.e(TAG, "计算的排放量无效");
        }
    }

    private double getVehicleEmissionFactor(String vehicleType) {
        switch (vehicleType) {
            case "Gasoline":
                return 0.24;
            case "Diesel":
                return 0.27;
            case "Hybrid":
                return 0.16;
            case "Electric":
                return 0.05;
            default:
                return 0.0;
        }
    }

    private double getFlightEmissionFactor(String flightType) {
        if (flightType.equals("Short-haul (<1500 km)")) {
            return 225.0;
        } else if (flightType.equals("Long-haul (>1500 km)")) {
            return 825.0;
        } else {
            return 0.0;
        }
    }

    private void updateTransportation(double calculatedValue) {
        DocumentReference docRef = db.collection("emissions").document(userId);

        // 获取当前的 totalEmissions 和旧的 transportation 值
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

            // 获取旧的 transportation 值
            double oldTransportationValue = 0.0;
            Object transportationObj = consumptionData.get("transportation");
            if (transportationObj instanceof Number) {
                oldTransportationValue = ((Number) transportationObj).doubleValue();
            }

            // 获取旧的 totalEmissions 值
            double oldTotalEmissions = 0.0;
            Object totalEmissionsObj = consumptionData.get("totalEmissions");
            if (totalEmissionsObj instanceof Number) {
                oldTotalEmissions = ((Number) totalEmissionsObj).doubleValue();
            }

            // 计算新的 totalEmissions 值
            double newTotalEmissions = oldTotalEmissions - oldTransportationValue + calculatedValue;

            // 更新 transportation 和 totalEmissions
            consumptionData.put("transportation", calculatedValue);
            consumptionData.put("totalEmissions", newTotalEmissions);

            // 更新 dateData 和 dailyData
            dateData.put("consumption", consumptionData);
            dailyData.put(selectedDate, dateData);

            // 构建更新的数据
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("dailyData", dailyData);

            // 更新到 Firebase
            docRef.set(updatedData, SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Transportation and total emissions updated successfully."))
                    .addOnFailureListener(e -> Log.e(TAG, "Error updating transportation and total emissions", e));

        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document", e);
        });
    }

}
