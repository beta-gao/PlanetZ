package com.example.planetz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Inside TrackerAdapter


public class TrackerAdapter extends RecyclerView.Adapter<TrackerViewHolder>{
    static List<HabitTrackerItem> habitTrackerList;
    Context context;


    public TrackerAdapter(List<HabitTrackerItem> habitTrackerList, Context c){
        if(habitTrackerList == null){
            habitTrackerList = new ArrayList<>();
        }
        this.habitTrackerList = habitTrackerList;
        this.context = c;
    }

    @NonNull
    @Override
    public TrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackerViewHolder(LayoutInflater.from(context).inflate(R.layout.single_tracker_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerViewHolder holder, int position) {

        holder.time.setText(habitTrackerList.get(position).getDays());
        holder.progressBar.setProgress(habitTrackerList.get(position).getProgress());
        holder.habitName.setText(habitTrackerList.get(position).getHabitName());

        holder.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    habitTrackerList.get(pos).logHabit();
                    notifyItemChanged(pos);
                }
            }

        });

        holder.unlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    habitTrackerList.get(pos).unLogHabit();
                    notifyItemChanged(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(habitTrackerList == null){
            return 0;
        }
        return habitTrackerList.size();
    }

    public void addHabit(HabitTrackerItem habit) {
        habitTrackerList.add(habit);
        notifyItemInserted(habitTrackerList.size() + 1); // Notify RecyclerView to update
    }
}
