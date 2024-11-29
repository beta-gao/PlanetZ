package com.example.planetz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HabitRecommendation extends AppCompatActivity  {


    List<HabitItem> habitRecomList;
    Map<String, Object> userAnswersMap;
    ImageView imageView;
    RecomAdapter adapter;
    RecyclerView recomRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_habit_recommendation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //hardcode to question answers

        readUserAnswer();

        recomRecyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recomRecyclerView.setLayoutManager(layoutManager);

        addHabitRecommendation();
        adapter = new RecomAdapter(habitRecomList, getApplicationContext());
        recomRecyclerView.setAdapter(adapter);

        imageView = findViewById(R.id.goback);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackingHabit.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void readUserAnswer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userId = "kSG2mQjBKYQ23M5O1eWLaQBYFS62";

        db.collection("carbonFootprints").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userAnswersMap = document.getData();
                        addHabitRecommendation();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(),"User haven't taken Survey", Toast.LENGTH_SHORT).show();
                        Log.d("Habit Recommendation","User haven't taken Survey",task.getException());
                    }
                } else{
                    Log.d("Habit Recommendation","Read failed",task.getException());
                }
            }
        });
    }

    public void addHabitRecommendation(){
        if (habitRecomList == null) {
            habitRecomList = new ArrayList<>();
        }

        personalizedRecommendation("clothingPurchaseFrequency","always", new HabitItem("consumption","Reduce New Clothing Purchases",R.drawable.reuseclothes,"low"));

        personalizedRecommendation("electronicDevicesPurchased","1", new HabitItem("energy","Reduce Electricity Consumption",R.drawable.outlet,"mid"));

        personalizedRecommendation("foodWasteFrequency","frequently",new HabitItem("food","Recycling Waste",R.drawable.recycle,"low"));

        personalizedRecommendation("chickenFrequency","daily",new HabitItem("food","Adopt a More Plant-Based Diet",R.drawable.diet,"hi"));
        personalizedRecommendation("fishFrequency","daily",new HabitItem("food","Adopt a More Plant-Based Diet",R.drawable.diet,"hi"));

        personalizedRecommendation("porkFrequency","daily",new HabitItem("food","Adopt a More Plant-Based Diet",R.drawable.diet,"hi"));
        personalizedRecommendation("porkFrequency","frequently",new HabitItem("food","Adopt a More Plant-Based Diet",R.drawable.diet,"hi"));

        personalizedRecommendation("beefFrequency","daily",new HabitItem("food","Adopt a More Plant-Based Diet",R.drawable.diet,"hi"));
        personalizedRecommendation("beefFrequency","frequently",new HabitItem("food","Adopt a More Plant-Based Diet",R.drawable.diet,"hi"));

        personalizedRecommendation("longHaulFlights","3-5 flights",new HabitItem("transportation","Limit Air Travel",R.drawable.plane, "mid"));
        personalizedRecommendation("publicTransportFrequency","3-5 flights",new HabitItem("transportation","Use Public Transportation More Often",R.drawable.bus, "mid"));

        personalizedRecommendation("renewableEnergyUse","No",new HabitItem("energy","Reduce Electricity Consumption",R.drawable.limitelec,"mid"));

        personalizedRecommendation("isUsingVehicle","yes",new HabitItem("transportation","Use Public Transportation More Often",R.drawable.bus, "mid"));

        personalizedRecommendation("homeHeatingType","Natural Gas",new HabitItem("energy","Reduce Gas Usage",R.drawable.gas,"mid"));
        personalizedRecommendation("monthlyElectricityBill","Over $200",new HabitItem("energy","Reduce Gas Usage",R.drawable.gas,"mid"));


    }

    public void personalizedRecommendation(String question, String ans, HabitItem habitItem){
        String userAnswer;

        if(userAnswersMap == null || userAnswersMap.get(question) == null) return;

        userAnswer = Objects.requireNonNull(userAnswersMap.get(question)).toString();

        if(userAnswer.equalsIgnoreCase(ans) && !containsHabit(habitItem)){
            habitRecomList.add(habitItem);
        }
    }

    public boolean containsHabit(HabitItem habitItem){

        if(habitRecomList == null) return false;
        if(habitRecomList.isEmpty()) return false;

        for(HabitItem habit: habitRecomList){
            if(habit.getHabit().equalsIgnoreCase(habitItem.getHabit())){
                return true;
            }
        }
        return false;
    }
}
