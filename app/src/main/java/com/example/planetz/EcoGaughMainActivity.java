package com.example.planetz;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.planetz.ecogaughui.EmissionsPagerAdapter;
import com.google.firebase.FirebaseApp;

import android.util.Log;

public class EcoGaughMainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        Log.d(TAG, "onCreate: FirebaseApp initialized");
        setContentView(R.layout.activity_ecogaugh_main);

        viewPager = findViewById(R.id.viewPager);
        EmissionsPagerAdapter adapter = new EmissionsPagerAdapter(this);
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(1);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d(TAG, "onPageSelected: Current page position: " + position);
            }
        });
    }

    @NonNull
    public ViewPager2 getViewPager() {
        Log.d(TAG, "getViewPager: Returning viewPager");
        return viewPager;
    }
}
