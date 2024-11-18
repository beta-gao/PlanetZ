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
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent i = new Intent(getApplicationContext(), Login.class);//replace with main dashboard activity
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

        LButton = findViewById(R.id.loginButton);
        LButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                String password;

                email = String.valueOf(emailText.getText());
                password = String.valueOf(passwordText.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login in Successful",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), Login.class);//replace with main dashboard activity
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        toRegTextView = findViewById(R.id.toReg);
        toRegTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            //link to the register page
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);//replace with register class
                startActivity(i);
                finish();
            }
        });

        toforgetTextView = findViewById(R.id.forgetpassword);
        toforgetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgetPassword.class);//replace with register class
                startActivity(i);
                finish();
            }
        });

    }
}
