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

public class FoodActivity extends AppCompatActivity {

    private String userId;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        userId = UserManager.getInstance(this).getUserId();
        selectedDate = getIntent().getStringExtra("selectedDate");

        if (userId == null || selectedDate == null) {
            Toast.makeText(this, "Error: User not logged in or date not selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Meal Section
        Spinner spinnerMealType = findViewById(R.id.spinnerMealType);
        EditText inputMealServings = findViewById(R.id.inputMealServings);
        Button mealLogButton = findViewById(R.id.mealLogButton);
        Button mealEditButton = findViewById(R.id.mealEditButton);
        Button mealDeleteButton = findViewById(R.id.mealDeleteButton);

        mealLogButton.setOnClickListener(v -> {
            String servingsText = inputMealServings.getText().toString();
            if (servingsText.isEmpty()) {
                Toast.makeText(this, "Please enter the number of servings", Toast.LENGTH_SHORT).show();
                return;
            }
            int servings = Integer.parseInt(servingsText);
            String mealType = spinnerMealType.getSelectedItem().toString();

            double emission = calculateMealEmissions(mealType, servings);
            saveMealData("Meal", emission);
        });

        mealEditButton.setOnClickListener(v -> editActivity("Meal", inputMealServings));
        mealDeleteButton.setOnClickListener(v -> deleteActivity("Meal"));
    }

    private double calculateMealEmissions(String mealType, int servings) {
        double emissionFactor;
        switch (mealType) {
            case "Beef":
                emissionFactor = 6.85; // kg CO2e per serving
                break;
            case "Pork":
                emissionFactor = 3.97; // kg CO2e per serving
                break;
            case "Chicken":
                emissionFactor = 2.60; // kg CO2e per serving
                break;
            case "Fish":
                emissionFactor = 2.19; // kg CO2e per serving
                break;
            case "Plant-based":
                emissionFactor = 1.37; // kg CO2e per serving
                break;
            default:
                emissionFactor = 0;
        }
        return servings * emissionFactor;
    }

    private void saveMealData(String activityType, double emission) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    double currentTotal = documentSnapshot.getDouble("totalEmissions") == null ? 0.0 : documentSnapshot.getDouble("totalEmissions");
                    currentTotal += emission;

                    documentSnapshot.getReference().update(activityType, emission, "totalEmissions", currentTotal)
                            .addOnSuccessListener(aVoid -> Toast.makeText(FoodActivity.this, "Meal logged successfully!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(FoodActivity.this, "Error saving meal data!", Toast.LENGTH_SHORT).show());
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