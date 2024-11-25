package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.planetz.model.CarbonFootprintData;

public class Question9 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load BeefFrequencyFragment directly
        if (savedInstanceState == null) {
            loadFragment(new Question9BeefFrequencyFragment());
        }

        // Initialize the CarbonFootprintData instance
        carbonFootprintData = CarbonFootprintData.getInstance();
    }

    // Method to load fragments dynamically
    void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(android.R.id.content, fragment); // Use the default content view
        transaction.commit();
    }

    // Method to go to the next fragment based on current fragment
    public void goToNextFragment(String currentFragmentTag, String frequency) {
        switch (currentFragmentTag) {
            case "Beef":
                carbonFootprintData.setBeefFrequency(frequency);
                loadFragment(new Question9PorkFrequencyFragment());
                break;
            case "Pork":
                carbonFootprintData.setPorkFrequency(frequency);
                loadFragment(new Question9ChickenFrequencyFragment());
                break;
            case "Chicken":
                carbonFootprintData.setChickenFrequency(frequency);
                loadFragment(new Question9FishFrequencyFragment());
                break;
            case "Fish":
                carbonFootprintData.setFishFrequency(frequency);
                Intent intent = new Intent(Question9.this, Question10.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}