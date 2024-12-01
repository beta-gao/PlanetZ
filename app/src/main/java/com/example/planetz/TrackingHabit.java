package com.example.planetz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class TrackingHabit extends AppCompatActivity implements RemoveHabit {

    RecyclerView recyclerView;
    TrackerAdapter adapter;
    List<HabitTrackerItem> habitTrackerList;
    ImageView search;
    String userId;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tracking_habit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();

        if (habitTrackerList == null) {
            habitTrackerList = new ArrayList<>();
        }

        getHabitTrackerList();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TrackerAdapter(habitTrackerList, this, this);
        recyclerView.setAdapter(adapter);

        search = findViewById(R.id.searchicon);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchKeyword.class);
                startActivity(intent);
            }
        });

        if (shouldShowPopup()) {
            showReminderPopup();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("HabitTrackerList").document("testDoc")
                .set(Collections.singletonMap("testField", "testValue"))
                .addOnSuccessListener(aVoid -> Log.d("FirestoreTest", "Write succeeded"))
                .addOnFailureListener(e -> Log.e("FirestoreTest", "Write failed", e));

    }

    private void showReminderPopup() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reminderlog);
        dialog.setCancelable(false);

        Button startLoggingButton = dialog.findViewById(R.id.startlogging);
        Button notShowTodayButton = dialog.findViewById(R.id.dontshow);

        startLoggingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        notShowTodayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("HabitTrackerPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong("lastPopupTime", System.currentTimeMillis());
                editor.apply();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean shouldShowPopup() {
        SharedPreferences prefs = getSharedPreferences("HabitTrackerPrefs", Context.MODE_PRIVATE);
        long lastShownTime = prefs.getLong("lastPopupTime", 0);

        // Check if the popup was shown today
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastShownTime);

        Calendar today = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) != today.get(Calendar.YEAR) ||
                calendar.get(Calendar.DAY_OF_YEAR) != today.get(Calendar.DAY_OF_YEAR);
    }

    public void getHabitTrackerList() {

        db.collection("habitTrackerList").document(userId).addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e("Firestore", "Listen failed.", error);
                }
                if (value != null && value.exists()) {
                    Log.d("Firestore", "Snapshot data: " + value.getData());

                    List<Map<String, Object>> habitData = (List<Map<String, Object>>) value.get("habitTrackerList");

                    if (habitData != null) {
                        habitTrackerList.clear();
                        adapter.notifyDataSetChanged();

                        if (!habitData.isEmpty()) {
                            for (Map<String, Object> habit : habitData) {
                                if (habit == null) continue;

                                String habitName = (String) habit.get("habitName");
                                int days = ((Long) Objects.requireNonNull(habit.get("days"))).intValue();
                                int progress = ((Long) Objects.requireNonNull(habit.get("progress"))).intValue();
                                int cycle = ((Long) Objects.requireNonNull(habit.get("cycle"))).intValue();

                                HabitTrackerItem item = new HabitTrackerItem(days, habitName, progress, cycle);
                                habitTrackerList.add(item);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "no document", Toast.LENGTH_SHORT).show();
                    createNewHabitTracker();
                }
            }
        });
    }

    public void createNewHabitTracker(){
        Map<String, Object> habitMap = new HashMap<>();
        habitMap.put("habitTrackerList", new ArrayList<>());

        db.collection("habitTrackerList").document(userId)
                .set(habitMap)
                .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(),
                        "Habit tracker created!", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
                    Log.e("Firestore", "Failed to create habit tracker", e);
                    Toast.makeText(getApplicationContext(), "Failed to create habit tracker.", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHabitTrackerList();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRemoveHabit(int position) {
        Log.d("TrackerAdapter", "onRemoveHabit triggered for position: " + position);

        HabitTrackerItem habitToRemove = habitTrackerList.get(position);
        //FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("habitTrackerList").document(userId)
                .update("habitTrackerList", FieldValue.arrayRemove(habitToRemove))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Habit removed successfully", Toast.LENGTH_SHORT).show();
                });
    }
}

