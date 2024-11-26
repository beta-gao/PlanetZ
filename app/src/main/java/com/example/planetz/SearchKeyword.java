package com.example.planetz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchKeyword extends AppCompatActivity implements SelectHabitOnClickListener{

    SearchView searchView;
    RecyclerView recyclerView;
    RAdapter adapter;
    Button resetFilterButton;
    Button transpButton;
    Button energyButton;
    Button foodButton;
    Button consumpButton;
    List<HabitItem> HabitList;
    List<HabitItem> workingList;
    Button addHabitButton;
    Button cancelButton;
    Dialog dialog;
    HabitTrackerItem habitTrackerItem;
    List<HabitTrackerItem> habitTrackerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_keyword);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        initializeButtons();
        retrieveHabitList();

        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RAdapter(HabitList,getApplicationContext(), this));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keywd) {
                searchList(keywd);
                return true;
            }
        });

        setOnclick();
    }

    void initializeButtons(){
        transpButton = findViewById(R.id.transportation);
        energyButton = findViewById(R.id.energy);
        foodButton = findViewById(R.id.food);
        consumpButton = findViewById(R.id.consumption);
        resetFilterButton = findViewById(R.id.resetFilter);
    }

    void initializeViews(){
        recyclerView = findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.searchview);
    }

    //potentially getting habit list from firebase
    void retrieveHabitList(){
        HabitList = new ArrayList<>();
        HabitList.add(new HabitItem("transportation","Take Public Transportation",R.drawable.bus, "mid"));
        HabitList.add(new HabitItem("transportation","Walking",R.drawable.bus, "mid"));
        HabitList.add(new HabitItem("transportation","Cycling",R.drawable.bus, "mid"));

        HabitList.add(new HabitItem("consumption","Purchase Second-Handed Clothes",R.drawable.reuseclothes,"low"));
        HabitList.add(new HabitItem("consumption","Mindful Shopping",R.drawable.reuseclothes,"low"));

        HabitList.add(new HabitItem("food","Recycling Waste",R.drawable.outlet,"mid"));
        HabitList.add(new HabitItem("food","Proper Storage",R.drawable.outlet,"low"));
        HabitList.add(new HabitItem("food","Meal Planning",R.drawable.outlet,"mid"));

        HabitList.add(new HabitItem("energy","Unplug Devices",R.drawable.outlet,"high"));
        HabitList.add(new HabitItem("energy","Efficient Appliances",R.drawable.outlet,"high"));
        HabitList.add(new HabitItem("energy","Save Usage",R.drawable.outlet,"high"));

        workingList = HabitList;
    }

    void setOnclick(){
        transpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList("transportation");
            }
        });

        energyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList("energy");
            }
        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList("food");
            }
        });

        consumpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList("consumption");
            }
        });

        resetFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workingList = HabitList;
                /*
                adapter = new RAdapter(HabitList, getApplicationContext(),new RAdapter.selectlistener);
                recyclerView.setAdapter(adapter);*/
                adapter.resetList(workingList);
            }
        });

    }

    //search feature
    void searchList(String keywd) {
        List<HabitItem> matchList = new ArrayList<>();
        String habitName;

        for(HabitItem habit: workingList){
            habitName = habit.getHabit().toLowerCase();
            if(habitName.contains(keywd)){
                matchList.add(habit);
            }
        }

        if(!matchList.isEmpty()){
            adapter = new RAdapter(matchList, this,this);
            recyclerView.setAdapter(adapter);
            //adapter.setMatch(matchList);
        }else{
            Toast.makeText(this, "Habit not found", Toast.LENGTH_SHORT).show();
        }
    }

    //filter feature
    void filterList(String filter){
        List<HabitItem> matchList = new ArrayList<>();
        String category;

        for(HabitItem habit: workingList){
            category = habit.getCategory();
            if(category.equalsIgnoreCase(filter)){
                matchList.add(habit);
            }
        }

        if(!matchList.isEmpty()){
            adapter = new RAdapter(matchList, getApplicationContext(),this);
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(this, "There is no habit with this category yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onHabitSelected(HabitItem habit) {
        //pop up window

        dialog = new Dialog(SearchKeyword.this);
        dialog.setContentView(R.layout.habit_selection_popup);

        addHabitButton = dialog.findViewById(R.id.addhabit);
        cancelButton = dialog.findViewById(R.id.cancel);

        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add habit to habit tracker item

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                //String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String userId = "user1";
                habitTrackerItem = new HabitTrackerItem(1, habit.getHabit(), 1,0);

                Map<String, Object> newHabit = new HashMap<>();
                newHabit.put("habitName", habitTrackerItem.getHabitName());
                newHabit.put("progress",1);
                newHabit.put("days", 1);
                newHabit.put("cycle", 0);

                if(habitTrackerList == null){
                    habitTrackerList = new ArrayList<>();
                }

                boolean exist = existItem(habitTrackerList, habitTrackerItem);

                if(!exist){
                    habitTrackerList.add(habitTrackerItem);

                    db.collection("habitTrackerList").document(userId).get().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();

                            //if doc exist, update, if not, set
                            if(document.exists()){
                                db.collection("habitTrackerList").document(userId)
                                        .update("habitTrackerList", FieldValue.arrayUnion(habitTrackerItem))
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getApplicationContext(), "Habit added successfully!", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(getApplicationContext(), TrackingHabit.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                            /*
                            else{
                                Map<String, Object> data = new HashMap<>();
                                data.put("habitTrackerList", habitTrackerList);

                                db.collection("habitTrackerList").document(userId)
                                        .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getApplicationContext(), "Habit and Document added successfully!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }*/
                        }else{
                            Exception e = task.getException();
                            Log.e("Firestore", "Error retrieving habits: " + (e != null ? e.getMessage() : "Unknown error"));
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Habit already exists in your tracker!", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), TrackingHabit.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    boolean existItem(List<HabitTrackerItem> habitTrackerList, HabitTrackerItem habitTrackerItem){

        if(habitTrackerList == null) return false;
        if(habitTrackerList.isEmpty()) return false;

        String habitName = habitTrackerItem.getHabitName();

        for (HabitTrackerItem habitexist : habitTrackerList) {
            if (habitName.equals(habitexist.getHabitName())) {
                return true;
            }
        }
        return false;
    }

}