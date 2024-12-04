package com.example.planetz.Ecogaughui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.planetz.LoginandRegister.UserManager;
import com.example.planetz.R;
import com.example.planetz.Data.UserEmissionData;
import com.example.planetz.EcoGaugh.EmissionsDashboard;
import com.example.planetz.Data.FirestoreDataReader;

import java.util.ArrayList;
import java.util.List;

public class TotalEmissionsFragment extends Fragment {

    private static final String TAG = "TotalEmissionsFragment";

    private Spinner timePeriodSpinner;
    private Spinner specificTimeSpinner;
    private TextView totalEmissionsTextView;
    private Button nextPageButton;

    private UserEmissionData userEmissionData;
    private EmissionsDashboard dashboard;

    private FirestoreDataReader firestoreDataReader;

    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: TotalEmissionsFragment started");
        View view = inflater.inflate(R.layout.fragment_total_emissions, container, false);

        timePeriodSpinner = view.findViewById(R.id.spinner_time_period);
        specificTimeSpinner = view.findViewById(R.id.spinner_specific_time);
        totalEmissionsTextView = view.findViewById(R.id.text_total_emissions);
        nextPageButton = view.findViewById(R.id.btn_next_page);

        dashboard = EmissionsDashboard.getInstance();
        firestoreDataReader = FirestoreDataReader.getInstance();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        initTimePeriodSpinner();
        initSpecificTimeSpinner();

        fetchUserEmissionData();

        nextPageButton.setOnClickListener(v -> {
            Log.d(TAG, "Next page button clicked");
            navigateToNextPage();
        });

        return view;
    }

    private void initTimePeriodSpinner() {
        Log.d(TAG, "initTimePeriodSpinner: Initializing time period spinner");
        List<String> timePeriods = new ArrayList<>();
        timePeriods.add("Daily");
        timePeriods.add("Weekly");
        timePeriods.add("Monthly");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, timePeriods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timePeriodSpinner.setAdapter(adapter);

        timePeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                String selectedPeriod = parent.getItemAtPosition(position).toString();
                sharedViewModel.setTimePeriod(selectedPeriod);
                updateSpecificTimeOptions(selectedPeriod);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        specificTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                String selectedTime = parent.getItemAtPosition(position).toString();
                sharedViewModel.setSpecificTime(selectedTime);
                updateTotalEmissionsDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initSpecificTimeSpinner() {
        Log.d(TAG, "initSpecificTimeSpinner: Initializing specific time spinner");

        specificTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                String selectedTime = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "Selected specific time: " + selectedTime);
                sharedViewModel.setSpecificTime(selectedTime);
                updateTotalEmissionsDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: No specific time selected");
            }
        });
    }

    private void fetchUserEmissionData() {
        String userId = UserManager.getInstance(requireContext()).getUserId();
        if (userId == null) {
            Log.e(TAG, "fetchUserEmissionData: User ID is null");
            return;
        }

        firestoreDataReader.fetchEmissionData(userId, new FirestoreDataReader.EmissionDataCallback() {
            @Override
            public void onSuccess(UserEmissionData data) {
                Log.d(TAG, "fetchEmissionData: User emission data fetched successfully");
                userEmissionData = data;
                dashboard.setUserEmissionData(data);

                String selectedPeriod = timePeriodSpinner.getSelectedItem().toString();
                updateSpecificTimeOptions(selectedPeriod);
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error fetching emission data: " + errorMessage);
            }
        });
    }

    private void updateSpecificTimeOptions(String timePeriod) {
        Log.d(TAG, "updateSpecificTimeOptions: Updating specific time options for timePeriod " + timePeriod);
        List<String> specificTimes = new ArrayList<>();

        if (userEmissionData == null) {
            Log.w(TAG, "userEmissionData is null, cannot update specific time options");
            return;
        }

        switch (timePeriod) {
            case "Daily":
                specificTimes = dashboard.getDailyOptions();
                break;
            case "Weekly":
                specificTimes = dashboard.getWeeklyOptions();
                break;
            case "Monthly":
                specificTimes = dashboard.getMonthlyOptions();
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, specificTimes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specificTimeSpinner.setAdapter(adapter);

        if (!specificTimes.isEmpty()) {
            specificTimeSpinner.setSelection(0);
            String selectedTime = specificTimes.get(0);
            sharedViewModel.setSpecificTime(selectedTime);
            updateTotalEmissionsDisplay();
        }
    }

    private void updateTotalEmissionsDisplay() {
        Log.d(TAG, "updateTotalEmissionsDisplay: Updating total emissions display");
        String timePeriod = sharedViewModel.getTimePeriod().getValue();
        String specificTime = sharedViewModel.getSpecificTime().getValue();

        if (timePeriod == null || specificTime == null) {
            Log.w(TAG, "Cannot update display: Time period or specific time is null");
            return;
        }

        String displayText = dashboard.getTotalEmissionsOverview(timePeriod, specificTime);
        totalEmissionsTextView.setText(displayText);
        Log.d(TAG, "updateTotalEmissionsDisplay: Display updated with text - " + displayText);
    }

    private void navigateToNextPage() {
        Log.d(TAG, "navigateToNextPage: Navigating to next page");
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) {
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem + 1, true); // Switch to next page
            Log.d(TAG, "navigateToNextPage: Moved to page " + (currentItem + 1));
        }
    }
}
