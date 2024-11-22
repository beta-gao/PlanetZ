package com.example.planetz;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SearchKeyword extends AppCompatActivity {

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RAdapter(HabitList,getApplicationContext()));

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
        HabitList.add(new HabitItem("transportation","Take Public Transport",R.drawable.bus));
        HabitList.add(new HabitItem("energy","Unplug Devices",R.drawable.outlet));
        HabitList.add(new HabitItem("energy","Purchase Second-Handed Clothes",R.drawable.reuseclothes));

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
                adapter = new RAdapter(HabitList, getApplicationContext());
                recyclerView.setAdapter(adapter);
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
            adapter = new RAdapter(matchList, this);
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
            adapter = new RAdapter(matchList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(this, "There is no habit with this category yet", Toast.LENGTH_SHORT).show();
        }
    }
}