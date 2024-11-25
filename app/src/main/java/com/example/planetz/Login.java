package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
    Button LButton;
    TextInputEditText emailText;
    TextInputEditText passwordText;
    TextView toRegTextView;
    TextView toforgetTextView;

    // Check if user is signed in
    @Override
    public void onStart() {
        super.onStart();
        // 检查用户是否已登录，如果已登录，加载用户信息或保持在当前页面
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show();
            // 这里可以加载用户相关的数据，比如显示欢迎信息等
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

        LButton = findViewById(R.id.loginButton);
        LButton.setOnClickListener(v -> {
            String email = String.valueOf(emailText.getText());
            String password = String.valueOf(passwordText.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            LButton.setEnabled(false);
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        LButton.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            // 登录成功后，加载登录用户数据，停留在当前页面
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                // 显示用户信息或更新 UI
                                Toast.makeText(Login.this, "Welcome " + user.getEmail(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(Login.this, "Authentication failed: wrong email address or password", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        toRegTextView = findViewById(R.id.toReg);
        toRegTextView.setOnClickListener(v -> navigateToActivity(RegisterActivity.class));

        toforgetTextView = findViewById(R.id.forgetpassword);
        toforgetTextView.setOnClickListener(v -> navigateToActivity(ForgetPassword.class));
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent i = new Intent(getApplicationContext(), targetActivity);
        startActivity(i);
        finish();
    }
}
