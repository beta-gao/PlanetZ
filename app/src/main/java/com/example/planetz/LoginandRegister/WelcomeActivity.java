package com.example.planetz.LoginandRegister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.Question.Question1;
import com.example.planetz.R;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforequestion);

        welcomeMessage = findViewById(R.id.welcomeMessage);
        nextButton = findViewById(R.id.nextButton);

        welcomeMessage.setText("Welcome to Planetze! Please fill out a questionnaire to get started.");

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, Question1.class);
            startActivity(intent);
            finish();
        });
    }
}