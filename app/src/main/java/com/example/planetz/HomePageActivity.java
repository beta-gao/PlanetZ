package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.DisplayingAnnualFootprintResult.BreakdownActivity;
import com.example.planetz.EcoBalance.EcoBalanceHomePageActivity;
import com.example.planetz.LoginandRegister.UserManager;
import com.example.planetz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivity extends AppCompatActivity {

    private Button annualFootprintButton;
    private Button ecoBalanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Initialize buttons
        annualFootprintButton = findViewById(R.id.annualFootprintButton);
        ecoBalanceButton = findViewById(R.id.ecoBalanceButton);

        // Set button listeners
        annualFootprintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(BreakdownActivity.class);
            }
        });

        ecoBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(EcoBalanceHomePageActivity.class);
            }
        });

        UserManager userManager = UserManager.getInstance(this);

        if (userManager.getUserId() == null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                userManager.setUserId(currentUser.getUid());
            }
        }

        Button buttonToEcoGaugh = findViewById(R.id.btn_to_eco_gaugh);
        buttonToEcoGaugh.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, EcoGaughMainActivity.class);
            startActivity(intent);
        });
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(HomePageActivity.this, activityClass);
        startActivity(intent);
    }
}