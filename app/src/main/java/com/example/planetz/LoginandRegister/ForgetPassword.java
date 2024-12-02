package com.example.planetz.LoginandRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.planetz.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class ForgetPassword extends AppCompatActivity implements ForgetPasswordContract.View {

    private ForgetPasswordContract.Presenter presenter;
    private Button forgetButton;
    private TextView backToLog;
    private TextInputEditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        presenter = new ForgetPasswordPresenterImpl(FirebaseAuth.getInstance());
        presenter.attachView(this);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forget), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        forgetButton = findViewById(R.id.ResetButton);
        emailText = findViewById(R.id.email);
        backToLog = findViewById(R.id.toReg);

        forgetButton.setOnClickListener(v -> {
            String email = String.valueOf(emailText.getText());
            presenter.sendResetLink(email);
        });

        backToLog.setOnClickListener(v -> presenter.navigateToLogin());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showLoading() {
        forgetButton.setEnabled(false);
        // Optionally, show a progress bar or loading indicator
    }

    @Override
    public void hideLoading() {
        forgetButton.setEnabled(true);
        // Optionally, hide the progress bar or loading indicator
    }

    @Override
    public void showEmailError(String message) {
        emailText.setError(message);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}