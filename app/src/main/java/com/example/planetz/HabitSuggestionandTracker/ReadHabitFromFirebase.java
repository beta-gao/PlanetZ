package com.example.planetz.HabitSuggestionandTracker;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class ReadHabitFromFirebase {
    List<Object> userAnswers;
    Map<String, Object> userAnswersMap;


    /*
    public void removeHabitFromFirebase(List<HabitTrackerItem> habitTrackerList, HabitTrackerItem habit, int position, Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("habitTrackerList").document(userId)
                .update("habitTrackerList", FieldValue.arrayRemove(habit))
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Habit removed successfully!", Toast.LENGTH_SHORT).show();
                    habitTrackerList.remove(position);
                    notifyItemRemoved(position);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to remove habit. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }*/

    public void readUserAnswer(Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userId = "s7UIzvEmdGKg2edwFCuE";

        db.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        userAnswersMap = document.getData();
                    } else {
                        Toast.makeText(context,"User haven't taken Survey", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
