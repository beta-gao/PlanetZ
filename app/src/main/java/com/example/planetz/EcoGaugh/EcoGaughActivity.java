package com.example.planetz.EcoGaugh;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.planetz.R;
import com.example.planetz.Ecogaughui.TotalEmissionsFragment;

import android.util.Log;

public class EcoGaughActivity extends AppCompatActivity {
    private static final String TAG = "EcoGaughActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: EcoGaughActivity started");
        setContentView(R.layout.activity_ecogaugh_main);

        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: Loading TotalEmissionsFragment");
            loadFragment(new TotalEmissionsFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        Log.d(TAG, "loadFragment: Loading fragment " + fragment.getClass().getSimpleName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
