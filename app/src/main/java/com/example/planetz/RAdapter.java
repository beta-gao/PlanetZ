package com.example.planetz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RViewHolder>{

    List<HabitItem> HabitList;
    Context context;
    SelectHabitOnClickListener selectListener;


    public RAdapter(List<HabitItem> habitList, Context c, SelectHabitOnClickListener selectListener) {
        this.HabitList = habitList;
        this.context = c;
        this.selectListener = selectListener;
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

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    selectListener.onHabitSelected(HabitList.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return HabitList.size();
    }

    public void resetList(List<HabitItem> workingList) {
        this.HabitList = workingList;
        notifyDataSetChanged();
    }
}
