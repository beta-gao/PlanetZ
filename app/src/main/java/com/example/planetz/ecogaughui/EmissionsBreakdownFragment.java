package com.example.planetz.ecogaughui;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.planetz.LoginandRegister.UserManager;
import com.example.planetz.R;
import com.example.planetz.data.UserEmissionData;
import com.example.planetz.ecogaugh.EmissionsDashboard;
import com.example.planetz.data.FirestoreDataReader;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmissionsBreakdownFragment extends Fragment {

    private static final String TAG = "EmissionsBreakdownFragment";
    private Button previousPageButton, nextPageButton;
    private PieChart pieChart;

    private UserEmissionData userEmissionData;
    private EmissionsDashboard dashboard;

    private SharedViewModel sharedViewModel;

    private FirestoreDataReader firestoreDataReader;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: EmissionsBreakdownFragment started");
        View view = inflater.inflate(R.layout.fragment_emissions_breakdown, container, false);

        pieChart = view.findViewById(R.id.pie_chart_breakdown);

        dashboard = EmissionsDashboard.getInstance();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        firestoreDataReader = FirestoreDataReader.getInstance();

        observeViewModel();
        fetchUserEmissionData();

        previousPageButton = view.findViewById(R.id.btn_previous_page);
        nextPageButton = view.findViewById(R.id.btn_next_page);

        previousPageButton.setOnClickListener(v -> navigateToPreviousPage());
        nextPageButton.setOnClickListener(v -> navigateToNextPage());

        return view;
    }

    private void observeViewModel() {
        sharedViewModel.getTimePeriod().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String timePeriod) {
                Log.d(TAG, "TimePeriod changed: " + timePeriod);
                updateBreakdownDisplay();
            }
        });

        sharedViewModel.getSpecificTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String specificTime) {
                Log.d(TAG, "SpecificTime changed: " + specificTime);
                updateBreakdownDisplay();
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
                updateBreakdownDisplay();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error fetching emission data: " + errorMessage);
            }
        });
    }

    private void updateBreakdownDisplay() {
        String timePeriod = sharedViewModel.getTimePeriod().getValue();
        String specificTime = sharedViewModel.getSpecificTime().getValue();

        if (timePeriod == null || specificTime == null || userEmissionData == null) {
            Log.w(TAG, "Cannot update breakdown: Missing data");
            pieChart.clear();
            return;
        }

        Map<String, Double> breakdown = dashboard.getEmissionsBreakdown(timePeriod, specificTime);

        if (breakdown == null || breakdown.isEmpty()) {
            Log.w(TAG, "No breakdown data available");
            pieChart.clear();
            return;
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : breakdown.entrySet()) {
            entries.add(new PieEntry((float) entry.getValue().doubleValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#FF6384"));
        colors.add(Color.parseColor("#36A2EB"));
        colors.add(Color.parseColor("#FFCE56"));
        colors.add(Color.parseColor("#4BC0C0"));
        colors.add(Color.parseColor("#9966FF"));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setCenterText("Emissions Breakdown");
        pieChart.setCenterTextSize(18f);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(14f);
        legend.setTextColor(Color.BLACK);
        legend.setWordWrapEnabled(true);

        pieChart.invalidate();

        Log.d(TAG, "updateBreakdownDisplay: Breakdown chart updated");
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

    private void navigateToNextPage() {
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) {
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem + 1, true);
        }
    }
}
