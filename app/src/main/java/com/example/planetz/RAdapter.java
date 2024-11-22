package com.example.planetz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RViewHolder>{

    List<HabitItem> HabitList;
    Context context;

    public RAdapter(List<HabitItem> habitList, Context c) {
        this.HabitList = habitList;
        this.context = c;
    }

    public void setMatch(List<HabitItem> matchList) {
        //only showing the habit matching keyword
        this.HabitList = matchList;
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RViewHolder(LayoutInflater.from(context).inflate(R.layout.singlerow,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        holder.HabitNameView.setText(HabitList.get(position).getHabit());
        holder.imgView.setImageResource(HabitList.get(position).getImg());
        holder.CategoryView.setText(HabitList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return HabitList.size();
    }
}
