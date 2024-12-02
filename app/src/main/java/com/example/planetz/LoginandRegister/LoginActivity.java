package com.example.planetz.LoginandRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.HomePageActivity;
import com.example.planetz.R;
import com.example.planetz.model.AnnualFootprintData;
import com.example.planetz.model.CarbonFootprintData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button loginButton;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    TextView toRegisterTextView;
    TextView toForgetPasswordTextView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            UserManager.getInstance(this).setUserId(currentUser.getUid());
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            initializeData(currentUser.getUid(), () -> navigateToActivity(HomePageActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String email = String.valueOf(emailText.getText());
            String password = String.valueOf(passwordText.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            loginButton.setEnabled(false);
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        loginButton.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                UserManager.getInstance(this).setUserId(user.getUid());
                                initializeData(user.getUid(), () -> navigateToActivity(HomePageActivity.class));
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed: wrong email or password", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        toRegisterTextView = findViewById(R.id.toReg);
        toRegisterTextView.setOnClickListener(v -> navigateToActivity(RegisterActivity.class));

        toForgetPasswordTextView = findViewById(R.id.forgetpassword);
        toForgetPasswordTextView.setOnClickListener(v -> navigateToActivity(ForgetPassword.class));
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(getApplicationContext(), targetActivity);
        startActivity(intent);
        finish();
    }

    private void initializeData(String userId, Runnable onSuccess) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Initialize CarbonFootprintData
        db.collection("carbonFootprints")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        CarbonFootprintData carbonData = CarbonFootprintData.getInstance();
                        carbonData.setUserId(userId);
                        carbonData.setUsingVehicle(documentSnapshot.getBoolean("isUsingVehicle"));
                        carbonData.setVehicleType(documentSnapshot.getString("vehicleType"));
                        carbonData.setAnnualMileage(documentSnapshot.getLong("annualMileage").intValue());
                        carbonData.setDietType(documentSnapshot.getString("dietType"));
                        // Set other fields as needed...
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load CarbonFootprintData", Toast.LENGTH_SHORT).show());

        // Initialize AnnualFootprintData
        db.collection("annualFootprints")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        AnnualFootprintData annualData = AnnualFootprintData.getInstance();
                        annualData.setUserId(userId);
                        annualData.setTransportation(documentSnapshot.getDouble("transportation"));
                        annualData.setHousing(documentSnapshot.getDouble("housing"));
                        annualData.setFood(documentSnapshot.getDouble("food"));
                        annualData.setConsumption(documentSnapshot.getDouble("consumption"));
                        annualData.setTotal(documentSnapshot.getDouble("total"));
                    }

                    // Call onSuccess after both data are loaded
                    onSuccess.run();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to load AnnualFootprintData", Toast.LENGTH_SHORT).show());
    }
}
