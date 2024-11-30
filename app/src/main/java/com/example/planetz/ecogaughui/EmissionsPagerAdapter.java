package com.example.planetz.ecogaughui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.util.Log;

public class EmissionsPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "EmissionsPagerAdapter";

    public EmissionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        Log.d(TAG, "EmissionsPagerAdapter: Constructor called");
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(TAG, "createFragment: Creating fragment for position " + position);
        switch (position) {
            case 0:
                return new TotalEmissionsFragment();
            case 1:
                return new EmissionsBreakdownFragment();
            case 2:
                return new EmissionsTrendFragment();
            case 3:
                return new CompareEmissionsFragment();
            default:
                Log.e(TAG, "createFragment: Invalid position " + position);
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Returning item count 4");
        return 4;
    }
}
