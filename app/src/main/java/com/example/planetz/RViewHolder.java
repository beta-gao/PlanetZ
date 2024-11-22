package com.example.planetz;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RViewHolder extends RecyclerView.ViewHolder{

    ImageView imgView;
    TextView HabitNameView;
    TextView CategoryView;
    public RViewHolder(@NonNull View itemView) {
        super(itemView);
        imgView = itemView.findViewById(R.id.img);
        HabitNameView = itemView.findViewById(R.id.HabitName);
        CategoryView = itemView.findViewById(R.id.Category);
    }
}
