package com.example.planetz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecomAdapter extends RecyclerView.Adapter<RecomViewHolder>{
    Context context;
    List<HabitItem> habitRecomList;
    SelectHabitOnClickListener selectListener;


    public RecomAdapter(List<HabitItem> habitList, Context c){
        this.habitRecomList = habitList;
        this.context =c;
    }

    @NonNull
    @Override
    public RecomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecomViewHolder(LayoutInflater.from(context).inflate(R.layout.habit_recom_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecomViewHolder holder, int position) {
        holder.habitNameView.setText(habitRecomList.get(position).getHabit());
        holder.imgView.setImageResource(habitRecomList.get(position).getImg());
        holder.impactView.setText(habitRecomList.get(position).getImpact());
        holder.categoryView.setText(habitRecomList.get(position).getCategory());

    }


    @Override
    public int getItemCount() {
        return habitRecomList.size();
    }


}
