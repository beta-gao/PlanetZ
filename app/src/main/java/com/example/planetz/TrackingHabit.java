package com.example.planetz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


public class TrackingHabit extends AppCompatActivity {

    RecyclerView recyclerView;
    TrackerAdapter adapter;
    List<HabitTrackerItem> habitTrackerList;
    ImageView search;

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

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (habitTrackerList == null) {
            habitTrackerList = new ArrayList<>();
        }

        adapter = new TrackerAdapter(habitTrackerList, this);
        recyclerView.setAdapter(adapter);

        getHabitTrackerList();

        search = findViewById(R.id.searchicon);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchKeyword.class);
                startActivity(intent);
            }
        });

    }

    void getHabitTrackerList() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = "user1";

        //String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (habitTrackerList == null) {
            habitTrackerList = new ArrayList<>();
        }

        db.collection("habitTrackerList").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<Map<String, Object>> habitData = (List<Map<String, Object>>) document.get("habitTrackerList");
                        System.out.println(habitData.get(0).get("habitName"));

                        if (habitData != null) {
                            if(!habitData.isEmpty()){
                                for (Map<String, Object> habit : habitData) {
                                    if(habit == null) continue;
                                    System.out.println("not null");

                                    String habitName = (String) habit.get("habitName");
                                    int days = ((Long) Objects.requireNonNull(habit.get("days"))).intValue();
                                    int progress = ((Long) Objects.requireNonNull(habit.get("progress"))).intValue();
                                    int cycle = ((Long) Objects.requireNonNull(habit.get("progress"))).intValue();

                                    HabitTrackerItem item = new HabitTrackerItem(days, habitName, progress,cycle);
                                    habitTrackerList.add(item);
                                }
                            }
                        }

                    }else {
                        Toast.makeText(getApplicationContext(), "No habits found for this user.", Toast.LENGTH_SHORT).show();
                        Map<String, Object> data = new HashMap<>();
                        data.put("habitTrackerList", habitTrackerList); // Start with an empty list

                        db.collection("habitTrackerList").document(userId)
                                .set(data)
                                .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(),
                                        "Habit tracker created!", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),
                                        "Error creating habit tracker!", Toast.LENGTH_SHORT).show());
                    }

                    adapter.setHabitTrackerList(habitTrackerList);
                    adapter.notifyDataSetChanged();

                }else{
                    Log.e("Firestore", "Error retrieving habits", task.getException());
                    Toast.makeText(getApplicationContext(), "Error retrieving habits.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}