package com.example.planetz.Ecogaughui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.planetz.HomePageActivity;
import com.example.planetz.LoginandRegister.UserManager;
import com.example.planetz.R;
import com.example.planetz.data.UserEmissionData;
import com.example.planetz.EcoGaugh.EmissionsDashboard;
import com.example.planetz.data.CountryDataReader;
import com.example.planetz.data.FirestoreDataReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompareEmissionsFragment extends Fragment {

    private static final String TAG = "CompareEmissionsFragment";
    private Button previousPageButton;
    private TextView compareEmissionsText;
    private Spinner countrySpinner;

    private UserEmissionData userEmissionData;
    private EmissionsDashboard dashboard;
    FirestoreDataReader firestoreDataReader;

    private SharedViewModel sharedViewModel;

    private Map<String, Double> countryEmissions;
    private List<String> countryList;
    private String selectedCountry;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: CompareEmissionsFragment started");
        View view = inflater.inflate(R.layout.fragment_compare_emissions, container, false);

        compareEmissionsText = view.findViewById(R.id.text_compare_emissions);
        countrySpinner = view.findViewById(R.id.spinner_country);

        dashboard = EmissionsDashboard.getInstance();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        firestoreDataReader = FirestoreDataReader.getInstance();

        countryEmissions = CountryDataReader.readCountryEmissionsData(getContext(), "country_emissions.csv");

        countryList = new ArrayList<>(countryEmissions.keySet());
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedView, int position, long id) {
                selectedCountry = countryList.get(position);
                Log.d(TAG, "Selected country: " + selectedCountry);
                updateComparisonDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCountry = null;
            }
        });

        observeViewModel();
        fetchUserEmissionData();

        previousPageButton = view.findViewById(R.id.btn_previous_page);
        previousPageButton.setOnClickListener(v -> navigateToPreviousPage());

        Button buttonBackToHome = view.findViewById(R.id.btn_back_to_home);
        buttonBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HomePageActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }

    private void observeViewModel() {
        sharedViewModel.getTimePeriod().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String timePeriod) {
                Log.d(TAG, "TimePeriod changed: " + timePeriod);
                updateComparisonDisplay();
            }
        });

        sharedViewModel.getSpecificTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String specificTime) {
                Log.d(TAG, "SpecificTime changed: " + specificTime);
                updateComparisonDisplay();
            }
        });
    }

    private void fetchUserEmissionData() {
        String userId = UserManager.getInstance(requireContext()).getUserId();
        if (userId == null) {
            Log.e(TAG, "fetchUserEmissionData: User ID is null");
            return;
        }

        FirestoreDataReader firestoreDataReader = FirestoreDataReader.getInstance();
        firestoreDataReader.fetchEmissionData(userId, new FirestoreDataReader.EmissionDataCallback() {
            @Override
            public void onSuccess(UserEmissionData data) {
                Log.d(TAG, "fetchEmissionData: User emission data fetched successfully");
                userEmissionData = data;
                dashboard.setUserEmissionData(data);
                updateComparisonDisplay();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error fetching emission data: " + errorMessage);
            }
        });
    }

    private void updateComparisonDisplay() {
        String timePeriod = sharedViewModel.getTimePeriod().getValue();
        String specificTime = sharedViewModel.getSpecificTime().getValue();

        if (timePeriod == null || specificTime == null || userEmissionData == null || selectedCountry == null) {
            Log.w(TAG, "Cannot update comparison: Missing data");
            compareEmissionsText.setText("Please select time period, specific time, and country.");
            return;
        }

        String comparisonResult = dashboard.compareToCountryAverage(selectedCountry, countryEmissions, timePeriod, specificTime);
        compareEmissionsText.setText(comparisonResult);
        Log.d(TAG, "updateComparisonDisplay: Comparison updated");
    }

    private void navigateToPreviousPage() {
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1, true);
            }
        }
    }
}
