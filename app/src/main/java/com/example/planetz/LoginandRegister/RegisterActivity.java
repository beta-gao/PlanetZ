package com.example.planetz.LoginandRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.AnnualFootprintData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Fill in all the blanks", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = task.getResult().getUser().getUid();
                        saveInitialCarbonFootprintData(userId);
                    } else {
                        Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveInitialCarbonFootprintData(String userId) {
        // Create an empty CarbonFootprintData object
        CarbonFootprintData initialData = CarbonFootprintData.getInstance();
        initialData.clear(); // Ensure it's empty
        initialData.setUserId(userId);

        // Save to Firestore
        db.collection("carbonFootprints")
                .document(userId)
                .set(initialData.toMap())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Registration successful! Data initialized.", Toast.LENGTH_SHORT).show();
                    navigateToLogin();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to initialize user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void saveInitialConsumptionData(String userId) {
        // 获取单例实例
        AnnualFootprintData consumptionData = AnnualFootprintData.getInstance();
        consumptionData.setUserId(userId);

        // 将数据上传到 Firestore
        db.collection("AnnualFootprintData")
                .document(userId)
                .set(consumptionData.toMap())
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Consumption data initialized.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to initialize consumption data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void navigateToLogin() {
        Intent intent = new Intent(RegisterActivity.this, Login.class);
        startActivity(intent);
        finish();
    }
}