package com.example.planetz;

import android.view.View;
import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class TrackerViewHolder extends RecyclerView.ViewHolder{
    Button logButton;
    Button unlogButton;
    ProgressBar progressBar;
    TextView time;
    TextView habitName;

    public TrackerViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressbar);
        logButton = itemView.findViewById(R.id.logHabit);
        unlogButton = itemView.findViewById(R.id.unlogHabit);
        time = itemView.findViewById(R.id.timelog);
        habitName = itemView.findViewById(R.id.habitName);
    }
}
