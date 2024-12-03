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

public class TransportationActivity extends AppCompatActivity {

    private String userId;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);

        userId = UserManager.getInstance(this).getUserId();
        selectedDate = getIntent().getStringExtra("selectedDate");

        if (userId == null || selectedDate == null) {
            Toast.makeText(this, "Error: User not logged in or date not selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Drive Personal Vehicle Section
        EditText inputDriveDistance = findViewById(R.id.inputDriveDistance);
        Spinner spinnerDriveFuelType = findViewById(R.id.spinnerDriveFuelType);
        Button driveLogButton = findViewById(R.id.driveLogButton);
        Button driveEditButton = findViewById(R.id.driveEditButton);
        Button driveDeleteButton = findViewById(R.id.driveDeleteButton);

        driveLogButton.setOnClickListener(v -> {
            String distanceText = inputDriveDistance.getText().toString();
            if (distanceText.isEmpty()) {
                Toast.makeText(this, "Please enter the distance", Toast.LENGTH_SHORT).show();
                return;
            }
            double distance = Double.parseDouble(distanceText);
            String fuelType = spinnerDriveFuelType.getSelectedItem().toString();

            double emission = calculateDriveEmissions(distance, fuelType);
            saveActivityData("DrivePersonalVehicle", emission);
        });

        driveEditButton.setOnClickListener(v -> editActivity("DrivePersonalVehicle", inputDriveDistance));
        driveDeleteButton.setOnClickListener(v -> deleteActivity("DrivePersonalVehicle"));

        // Take Public Transportation Section
        EditText inputPublicTransportTime = findViewById(R.id.inputPublicTransportTime);
        Spinner spinnerPublicTransportType = findViewById(R.id.spinnerPublicTransportType);
        Button publicLogButton = findViewById(R.id.publicLogButton);
        Button publicEditButton = findViewById(R.id.publicEditButton);
        Button publicDeleteButton = findViewById(R.id.publicDeleteButton);

        publicLogButton.setOnClickListener(v -> {
            String timeText = inputPublicTransportTime.getText().toString();
            if (timeText.isEmpty()) {
                Toast.makeText(this, "Please enter the time spent", Toast.LENGTH_SHORT).show();
                return;
            }
            double time = Double.parseDouble(timeText);
            String transportType = spinnerPublicTransportType.getSelectedItem().toString();

            double emission = calculatePublicTransportEmissions(transportType, time);
            saveActivityData("TakePublicTransport", emission);
        });

        publicEditButton.setOnClickListener(v -> editActivity("TakePublicTransport", inputPublicTransportTime));
        publicDeleteButton.setOnClickListener(v -> deleteActivity("TakePublicTransport"));

        // Cycling or Walking Section
        EditText inputCycleWalkDistance = findViewById(R.id.inputCycleWalkDistance);
        Button cycleLogButton = findViewById(R.id.cycleLogButton);
        Button cycleEditButton = findViewById(R.id.cycleEditButton);
        Button cycleDeleteButton = findViewById(R.id.cycleDeleteButton);

        cycleLogButton.setOnClickListener(v -> {
            String distanceText = inputCycleWalkDistance.getText().toString();
            if (distanceText.isEmpty()) {
                Toast.makeText(this, "Please enter the distance", Toast.LENGTH_SHORT).show();
                return;
            }
            double distance = Double.parseDouble(distanceText);
            double emission = calculateCyclingEmissions(distance);

            saveActivityData("CyclingOrWalking", emission);
        });

        cycleEditButton.setOnClickListener(v -> editActivity("CyclingOrWalking", inputCycleWalkDistance));
        cycleDeleteButton.setOnClickListener(v -> deleteActivity("CyclingOrWalking"));

        // Flight Section
        EditText inputFlightCount = findViewById(R.id.inputFlightCount);
        Spinner spinnerFlightType = findViewById(R.id.spinnerFlightType);
        Button flightLogButton = findViewById(R.id.flightLogButton);
        Button flightEditButton = findViewById(R.id.flightEditButton);
        Button flightDeleteButton = findViewById(R.id.flightDeleteButton);

        flightLogButton.setOnClickListener(v -> {
            String flightCountText = inputFlightCount.getText().toString();
            if (flightCountText.isEmpty()) {
                Toast.makeText(this, "Please enter the number of flights", Toast.LENGTH_SHORT).show();
                return;
            }
            int flights = Integer.parseInt(flightCountText);
            String flightType = spinnerFlightType.getSelectedItem().toString();

            double emission = calculateFlightEmissions(flights, flightType);
            saveActivityData("Flight", emission);
        });

        flightEditButton.setOnClickListener(v -> editActivity("Flight", inputFlightCount));
        flightDeleteButton.setOnClickListener(v -> deleteActivity("Flight"));
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

    private double calculateDriveEmissions(double distance, String fuelType) {
        double emissionFactor;
        switch (fuelType) {
            case "Gasoline":
                emissionFactor = 0.24;
                break;
            case "Diesel":
                emissionFactor = 0.27;
                break;
            case "Hybrid":
                emissionFactor = 0.16;
                break;
            case "Electric":
                emissionFactor = 0.05;
                break;
            default:
                emissionFactor = 0;
        }
        return distance * emissionFactor;
    }

    private double calculatePublicTransportEmissions(String transportType, double time) {
        double emissionFactor;
        switch (transportType) {
            case "Bus":
                emissionFactor = 1.57;
                break;
            case "Train":
                emissionFactor = 1.57;
                break;
            case "Subway":
                emissionFactor = 1.57;
                break;
            default:
                emissionFactor = 0;
        }
        return time * emissionFactor;
    }

    private double calculateCyclingEmissions(double distance) {
        return 0.0; // Cycling and walking are assumed to have no CO2 emissions
    }

    private double calculateFlightEmissions(int flights, String flightType) {
        double emissionFactor;
        switch (flightType) {
            case "Short-haul (<1,500 km)":
                emissionFactor = 225;
                break;
            case "Long-haul (>1,500 km)":
                emissionFactor = 825;
                break;
            default:
                emissionFactor = 0;
        }
        return flights * emissionFactor;
    }

    private void saveActivityData(String activityType, double emission) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("emission").document(userId)
                .collection("dailyData").document(selectedDate)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    double currentTotal = documentSnapshot.getDouble("totalEmissions") == null ? 0.0 : documentSnapshot.getDouble("totalEmissions");
                    currentTotal += emission;

                    documentSnapshot.getReference().update(activityType, emission, "totalEmissions", currentTotal)
                            .addOnSuccessListener(aVoid -> Toast.makeText(TransportationActivity.this, "Activity logged successfully!", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(TransportationActivity.this, "Error saving data!", Toast.LENGTH_SHORT).show());
                });
    }
}