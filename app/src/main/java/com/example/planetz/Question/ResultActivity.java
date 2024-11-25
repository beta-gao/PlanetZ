package com.example.planetz.Question;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CarbonFootprintData carbonFootprintData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = FirebaseFirestore.getInstance(); // 初始化 Firestore
        carbonFootprintData = CarbonFootprintData.getInstance(); // 获取单例数据

        // 上传数据到 Firestore
        uploadDataToFirestore();

        // 显示问卷完成消息
        TextView completionMessage = findViewById(R.id.completion_message);
        completionMessage.setText("Thank you for completing the questionnaire!");
    }

    private void uploadDataToFirestore() {
        // 将 CarbonFootprintData 上传到 Firestore 的 "users" 集合中
        db.collection("users")
                .add(carbonFootprintDataToMap(carbonFootprintData)) // 转换为 Map 格式
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(ResultActivity.this, "数据上传成功！", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(ResultActivity.this, "数据上传失败：" + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    // 将 CarbonFootprintData 转换为 Map 形式，便于 Firestore 处理
    private Object carbonFootprintDataToMap(CarbonFootprintData data) {
        Map<String, Object> map = new HashMap<>();
        map.put("isUsingVehicle", data.isUsingVehicle());
        map.put("vehicleType", data.getVehicleType() != null ? data.getVehicleType().toString() : "Unknown");
        map.put("annualMileage", data.getAnnualMileage());
        map.put("dietType", data.getDietType());
        map.put("publicTransportFrequency", data.getPublicTransportFrequency());
        map.put("publicTransportTime", data.getPublicTransportTime());
        map.put("shortHaulFlights", data.getShortHaulFlights());
        map.put("longHaulFlights", data.getLongHaulFlights());
        map.put("beefFrequency", data.getBeefFrequency());
        map.put("porkFrequency", data.getPorkFrequency());
        map.put("chickenFrequency", data.getChickenFrequency());
        map.put("fishFrequency", data.getFishFrequency());
        map.put("foodWasteFrequency", data.getFoodWasteFrequency());
        map.put("homeType", data.getHomeType());
        map.put("householdSize", data.getHouseholdSize());
        map.put("homeSize", data.getHomeSize());
        map.put("homeHeatingType", data.getHomeHeatingType());
        map.put("monthlyElectricityBill", data.getMonthlyElectricityBill());
        map.put("waterHeatingType", data.getWaterHeatingType());
        map.put("renewableEnergyUse", data.getRenewableEnergyUse());
        map.put("clothingPurchaseFrequency", data.getClothingPurchaseFrequency());
        map.put("secondHandOrEcoFriendlyProducts", data.getSecondHandOrEcoFriendlyProducts());
        map.put("electronicDevicesPurchased", data.getElectronicDevicesPurchased());
        map.put("recyclingFrequency", data.getRecyclingFrequency());
        return map;
    }
}