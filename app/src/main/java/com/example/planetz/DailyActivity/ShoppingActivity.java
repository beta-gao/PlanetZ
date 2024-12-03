package com.example.planetz.DailyActivity;

import com.example.planetz.R;
import com.example.planetz.LoginandRegister.UserManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ShoppingActivity extends AppCompatActivity {

    private String userId;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        userId = UserManager.getInstance(this).getUserId();
        selectedDate = getIntent().getStringExtra("selectedDate");

        if (userId == null || selectedDate == null) {
            Toast.makeText(this, "Error: User not logged in or date not selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Buy New Clothes Section
        EditText inputClothesQuantity = findViewById(R.id.inputClothesQuantity);
        Button clothesLogButton = findViewById(R.id.clothesLogButton);
        Button clothesEditButton = findViewById(R.id.clothesEditButton);
        Button clothesDeleteButton = findViewById(R.id.clothesDeleteButton);

        clothesLogButton.setOnClickListener(v -> {
            String quantityText = inputClothesQuantity.getText().toString();
            if (quantityText.isEmpty()) {
                Toast.makeText(this, "Please enter the quantity of clothing items", Toast.LENGTH_SHORT).show();
                return;
            }
            int quantity = Integer.parseInt(quantityText);
            double emission = calculateClothesEmissions(quantity);

            saveActivityData("BuyNewClothes", emission);
        });

        clothesEditButton.setOnClickListener(v -> editActivity("BuyNewClothes", inputClothesQuantity));
        clothesDeleteButton.setOnClickListener(v -> deleteActivity("BuyNewClothes"));

        // Buy Electronics Section
        EditText inputElectronicsQuantity = findViewById(R.id.inputElectronicsQuantity);
        Spinner spinnerElectronicsType = findViewById(R.id.spinnerElectronicsType);
        Button electronicsLogButton = findViewById(R.id.electronicsLogButton);
        Button electronicsEditButton = findViewById(R.id.electronicsEditButton);
        Button electronicsDeleteButton = findViewById(R.id.electronicsDeleteButton);

        electronicsLogButton.setOnClickListener(v -> {
            String quantityText = inputElectronicsQuantity.getText().toString();
            if (quantityText.isEmpty()) {
                Toast.makeText(this, "Please enter the quantity of electronics", Toast.LENGTH_SHORT).show();
                return;
            }
            int quantity = Integer.parseInt(quantityText);
            String type = spinnerElectronicsType.getSelectedItem().toString();
            double emission = calculateElectronicsEmissions(type, quantity);

            saveActivityData("BuyElectronics", emission);
        });

        electronicsEditButton.setOnClickListener(v -> editActivity("BuyElectronics", inputElectronicsQuantity));
        electronicsDeleteButton.setOnClickListener(v -> deleteActivity("BuyElectronics"));

        // Other Purchases Section
        EditText inputPurchaseQuantity = findViewById(R.id.inputPurchaseQuantity);
        Button purchaseLogButton = findViewById(R.id.purchaseLogButton);
        Button purchaseEditButton = findViewById(R.id.purchaseEditButton);
        Button purchaseDeleteButton = findViewById(R.id.purchaseDeleteButton);

        purchaseLogButton.setOnClickListener(v -> {
            String quantityText = inputPurchaseQuantity.getText().toString();
            if (quantityText.isEmpty()) {
                Toast.makeText(this, "Please enter the quantity of purchases", Toast.LENGTH_SHORT).show();
                return;
            }
            int quantity = Integer.parseInt(quantityText);
            double emission = calculateOtherPurchasesEmissions(quantity);

            saveActivityData("OtherPurchases", emission);
        });

        purchaseEditButton.setOnClickListener(v -> editActivity("OtherPurchases", inputPurchaseQuantity));
        purchaseDeleteButton.setOnClickListener(v -> deleteActivity("OtherPurchases"));

        // Energy Bills Section
        EditText inputElectricityBill = findViewById(R.id.inputElectricityBill);
        EditText inputGasBill = findViewById(R.id.inputGasBill);
        EditText inputWaterBill = findViewById(R.id.inputWaterBill);
        Button energyBillLogButton = findViewById(R.id.energyBillLogButton);
        Button energyBillEditButton = findViewById(R.id.energyBillEditButton);
        Button energyBillDeleteButton = findViewById(R.id.energyBillDeleteButton);

        energyBillLogButton.setOnClickListener(v -> {
            String electricityText = inputElectricityBill.getText().toString();
            String gasText = inputGasBill.getText().toString();
            String waterText = inputWaterBill.getText().toString();

            double electricity = electricityText.isEmpty() ? 0.0 : Double.parseDouble(electricityText);
            double gas = gasText.isEmpty() ? 0.0 : Double.parseDouble(gasText);
            double water = waterText.isEmpty() ? 0.0 : Double.parseDouble(waterText);

            double emission = calculateEnergyBillEmissions(electricity, gas, water);
            saveActivityData("EnergyBills", emission);
        });

        energyBillEditButton.setOnClickListener(v -> editEnergyBill(inputElectricityBill, inputGasBill, inputWaterBill));
        energyBillDeleteButton.setOnClickListener(v -> deleteActivity("EnergyBills"));
    }

    // Emission Calculations
    private double calculateClothesEmissions(int quantity) {
        return quantity * 12.0; // Assume each clothing item contributes 12 kg CO2e
    }

    private double calculateElectronicsEmissions(String type, int quantity) {
        double emissionFactor;
        switch (type.toLowerCase()) {
            case "smartphone":
            case "laptop":
            case "tv":
                emissionFactor = 300.0; // kg CO2e per device
                break;
            default:
                emissionFactor = 0.0; // kg CO2e for other electronics
        }
        return quantity * emissionFactor;
    }

    private double calculateOtherPurchasesEmissions(int quantity) {
        return quantity * 20.0; // Assume each general purchase contributes 20 kg CO2e
    }

    private double calculateEnergyBillEmissions(double electricity, double gas, double water) {
        double electricityEmission = electricity * 0.5; // kg CO2e per dollar
        double gasEmission = gas * 0.4; // kg CO2e per dollar
        double waterEmission = water * 0.2; // kg CO2e per dollar

        return electricityEmission + gasEmission + waterEmission;
    }

    // Firebase Operations
    private void saveActivityData(String activityType, double emission) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    double currentTotal = documentSnapshot.getDouble("totalEmissions") == null ? 0.0 : documentSnapshot.getDouble("totalEmissions");
                    currentTotal += emission;

                    documentSnapshot.getReference().update(activityType, emission, "totalEmissions", currentTotal)
                            .addOnSuccessListener(aVoid -> Toast.makeText(ShoppingActivity.this, "Activity logged successfully!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(ShoppingActivity.this, "Error saving data!", Toast.LENGTH_SHORT).show());
                });
    }

    private void editActivity(String activityType, EditText inputField) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.contains(activityType)) {
                        double currentAmount = documentSnapshot.getDouble(activityType);
                        inputField.setText(String.valueOf(currentAmount));
                    } else {
                        Toast.makeText(this, "Not logged yet", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editEnergyBill(EditText electricityField, EditText gasField, EditText waterField) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.contains("EnergyBills")) {
                        Map<String, Object> bills = (Map<String, Object>) documentSnapshot.get("EnergyBills");
                        if (bills != null) {
                            electricityField.setText(String.valueOf(bills.getOrDefault("electricity", 0.0)));
                            gasField.setText(String.valueOf(bills.getOrDefault("gas", 0.0)));
                            waterField.setText(String.valueOf(bills.getOrDefault("water", 0.0)));
                        }
                    } else {
                        Toast.makeText(this, "Not logged yet", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteActivity(String activityType) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.contains(activityType)) {
                        double currentTotal = documentSnapshot.getDouble("totalEmissions");
                        double activityAmount = documentSnapshot.getDouble(activityType);
                        double newTotal = currentTotal - activityAmount;

                        documentSnapshot.getReference().update(activityType, null, "totalEmissions", newTotal)
                                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(this, "Error deleting data", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(this, "Not logged yet", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}