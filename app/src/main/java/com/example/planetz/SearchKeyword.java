package com.example.planetz;

import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
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

        recyclerView = findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.searchview);

        List<HabitItem> HabitList = new ArrayList<>();
        HabitList.add(new HabitItem("Transportation","Take Public Transport",R.drawable.bus));
        HabitList.add(new HabitItem("Energy","Unplug Devices",R.drawable.outlet));
        HabitList.add(new HabitItem("Energy","Purchase Second-Handed Clothes",R.drawable.reuseclothes));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RAdapter(HabitList,getApplicationContext()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keywd) {
                searchList(HabitList, keywd);
                return true;
            }
        });
    }

    void searchList(List<HabitItem> HabitList, String keywd) {
        List<HabitItem> matchList = new ArrayList<>();
        String habitName;

        for(HabitItem habit: HabitList){
           habitName = habit.getHabit().toLowerCase();
           if(habitName.contains(keywd)){
               matchList.add(habit);
           }
        }

        if(!matchList.isEmpty()){
            adapter = new RAdapter(HabitList, this);
            recyclerView.setAdapter(adapter);
            adapter.setMatch(matchList);

        }else{
            Toast.makeText(this, "Habit not found", Toast.LENGTH_SHORT).show();
        }
    }
}