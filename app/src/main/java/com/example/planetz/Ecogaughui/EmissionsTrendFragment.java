package com.example.planetz.Ecogaughui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// Import chart library
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.planetz.R;
import com.example.planetz.data.DailyData;
import com.example.planetz.data.FirestoreDataReader;
import com.example.planetz.data.UserEmissionData;
import com.example.planetz.EcoGaugh.EmissionsDashboard;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.example.planetz.LoginandRegister.UserManager;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmissionsTrendFragment extends Fragment {

    private EmissionsDashboard dashboard;
    private LineChart lineChart;
    private UserEmissionData userEmissionData;
    private FirestoreDataReader firestoreDataReader;

    private static final String TAG = "EmissionsTrendFragment";

    private SharedViewModel sharedViewModel;
    private String timePeriod;
    private String specificTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: EmissionsTrendFragment started");
        View view = inflater.inflate(R.layout.fragment_emissions_trend, container, false);

        lineChart = view.findViewById(R.id.line_chart);
        dashboard = EmissionsDashboard.getInstance();
        firestoreDataReader = FirestoreDataReader.getInstance();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getTimePeriod().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String period) {
                timePeriod = period;
                updateTrendChart(); // 调用修改后的方法更新图表
            }
        });

        sharedViewModel.getSpecificTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String time) {
                specificTime = time;
                updateTrendChart(); // 调用修改后的方法更新图表
            }
        });

        fetchUserEmissionData(); // 调用修改后的方法，动态获取用户 ID

        Button nextPageButton = view.findViewById(R.id.btn_next_page);
        nextPageButton.setOnClickListener(v -> {
            Log.d(TAG, "Next page button clicked");
            navigateToNextPage();
        });

        Button previousPageButton = view.findViewById(R.id.btn_previous_page);
        previousPageButton.setOnClickListener(v -> {
            Log.d(TAG, "Previous page button clicked");
            navigateToPreviousPage();
        });

        return view;
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
                updateTrendChart();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e(TAG, "Error fetching emission data: " + errorMessage);
            }
        });
    }

    private void updateTrendChart() {
        if (timePeriod == null || specificTime == null || userEmissionData == null) {
            Log.w(TAG, "Cannot update chart: Missing data");
            return;
        }

        Log.d(TAG, "updateTrendChart: Updating trend chart");
        Map<String, DailyData> filteredData = userEmissionData.filterByTimePeriod(timePeriod, specificTime);
        Map<String, Double> trendData = dashboard.getEmissionsTrend(timePeriod, specificTime);

        List<Entry> entries = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Double> entry : trendData.entrySet()) {
            entries.add(new Entry(index++, entry.getValue().floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Emissions Trend");
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        Log.d(TAG, "updateTrendChart: Trend chart updated");
    }

    private void navigateToNextPage() {
        Log.d(TAG, "navigateToNextPage: Navigating to next page");
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) {
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem + 1, true);
            Log.d(TAG, "navigateToNextPage: Moved to page " + (currentItem + 1));
        }
    }

    private void navigateToPreviousPage() {
        Log.d(TAG, "navigateToPreviousPage: Navigating to previous page");
        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        if (viewPager != null) {
            int currentItem = viewPager.getCurrentItem();
            viewPager.setCurrentItem(currentItem - 1, true); // Switch to previous page
            Log.d(TAG, "navigateToPreviousPage: Moved to page " + (currentItem - 1));
        }
    }
}
