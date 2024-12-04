package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.planetz.DailyActivity.CalendarActivity;
import com.example.planetz.DisplayingAnnualFootprintResult.BreakdownActivity;
import com.example.planetz.EcoBalance.EcoBalanceHomePageActivity;
import com.example.planetz.EcoHub.EcoHubActivity;
import com.example.planetz.HabitSuggestionandTracker.TrackingHabit;
import com.example.planetz.LoginandRegister.UserManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainDashboard extends AppCompatActivity {

    private CardView annualCarbon;
    private CardView ecoBalance;
    private CardView ecoTracker;
    private CardView ecoGaugh;
    private CardView ecoHub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        initializeView();
        setUpOnClick();
    }
    void initializeView(){
        annualCarbon = findViewById(R.id.annualFootprintButton);
        ecoBalance = findViewById(R.id.ecoBalanceButton);
        ecoTracker = findViewById(R.id.ecoTrackerButton);
        ecoGaugh = findViewById(R.id.ecoGaughButton);
        ecoHub = findViewById(R.id.ecoHubButton);
    }

    void setUpOnClick(){
       annualCarbon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               navigateToActivity(BreakdownActivity.class);
           }
       });

       ecoBalance.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               navigateToActivity(EcoBalanceHomePageActivity.class);
           }
       });

       ecoTracker.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               navigateToActivity(EcoTrackerMainActivity.class);
           }
       });

       ecoHub.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               navigateToActivity(EcoHubActivity.class);
           }
       });

        UserManager userManager = UserManager.getInstance(MainDashboard.this);

        if (userManager.getUserId() == null) {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                userManager.setUserId(currentUser.getUid());
            }
        }

        ecoGaugh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(EcoGaughMainActivity.class);
            }
        });
    }
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(MainDashboard.this, activityClass);
        startActivity(intent);
    }
}