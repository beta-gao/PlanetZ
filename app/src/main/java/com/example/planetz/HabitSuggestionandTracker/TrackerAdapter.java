package com.example.planetz.HabitSuggestionandTracker;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetz.R;

import java.util.ArrayList;
import java.util.List;

// Inside TrackerAdapter


public class TrackerAdapter extends RecyclerView.Adapter<TrackerViewHolder>{
    List<HabitTrackerItem> habitTrackerList;
    Context context;
    RemoveHabit removeHabitMethod;
    Dialog dialog;
    Button removeHabitButton;
    Button cancelButton;

    public TrackerAdapter(List<HabitTrackerItem> habitTrackerList, Context c, RemoveHabit removeHabitMethod){
        if(habitTrackerList == null){
            habitTrackerList = new ArrayList<>();
        }
        this.habitTrackerList = habitTrackerList;
        this.context = c;
        this.removeHabitMethod = removeHabitMethod;

    }

    @NonNull
    @Override
    public TrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackerViewHolder(LayoutInflater.from(context).inflate(R.layout.single_tracker_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerViewHolder holder, int position) {

        holder.time.setText(habitTrackerList.get(position).getDaysString());
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

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(holder.itemView.getContext());
                dialog.setContentView(R.layout.removehabit);

                removeHabitButton = dialog.findViewById(R.id.agreeremove);
                cancelButton = dialog.findViewById(R.id.cancel);

                removeHabitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getBindingAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            if (removeHabitMethod != null) {
                                removeHabitMethod.onRemoveHabit(pos);
                                notifyItemRemoved(pos);
                                habitTrackerList.remove(pos);
                            }
                            notifyItemChanged(pos);
                        }
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
        });
    }

    @Override
    public int getItemCount() {
        if(habitTrackerList == null){
            return 0;
        }
        return habitTrackerList.size();
    }

    public void setHabitTrackerList(List<HabitTrackerItem> habitTrackerList) {
        this.habitTrackerList = habitTrackerList;
    }
}
