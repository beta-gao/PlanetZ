package com.example.planetz.LoginandRegister;

import android.content.Context;
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

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;
    private Button loginButton;
    private TextInputEditText emailText;
    private TextInputEditText passwordText;
    private TextView toRegisterTextView;
    private TextView toForgetPasswordTextView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            UserManager.getInstance(this).setUserId(currentUser.getUid());
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            initializeData(currentUser.getUid(), this::navigateToHome);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenterImpl();
        presenter.attachView(this);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            String email = String.valueOf(emailText.getText());
            String password = String.valueOf(passwordText.getText());
            presenter.login(email, password);
        });

        toRegisterTextView = findViewById(R.id.toReg);
        toRegisterTextView.setOnClickListener(v -> presenter.navigateToRegister());

        toForgetPasswordTextView = findViewById(R.id.forgetpassword);
        toForgetPasswordTextView.setOnClickListener(v -> presenter.navigateToForgetPassword());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showLoading() {
        loginButton.setEnabled(false);
        // Optionally, show a progress bar
    }

    @Override
    public void hideLoading() {
        loginButton.setEnabled(true);
        // Optionally, hide the progress bar
    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToRegister() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToForgetPassword() {
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showEmailError(String message) {
        emailText.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        passwordText.setError(message);
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

    public Context getContext() {
        return this;
    }
}