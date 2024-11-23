package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.R;

public class Question9 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question9);

        carbonFootprintData = CarbonFootprintData.getInstance();

        // Load the first fragment (BeefFrequencyFragment)
        loadFragment(new Question9BeefFrequencyFragment());
    }

    // Method to load fragments
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
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
                loadFragment(new Question9PorkFrequencyFragment());
                break;
            case "Fish":
                carbonFootprintData.setFishFrequency(frequency);
                // Go to the next question activity (Question10Activity)
                Intent intent = new Intent(Question9.this, Question10.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
