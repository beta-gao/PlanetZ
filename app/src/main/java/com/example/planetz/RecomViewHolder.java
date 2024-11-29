package com.example.planetz;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecomViewHolder extends RecyclerView.ViewHolder{

    ImageView imgView;
    TextView habitNameView;
    TextView categoryView;
    TextView impactView;

    public RecomViewHolder(@NonNull View itemView) {
        super(itemView);
        imgView = itemView.findViewById(R.id.circleimg);
        habitNameView = itemView.findViewById(R.id.habitName);
        categoryView = itemView.findViewById(R.id.category);
        impactView = itemView.findViewById(R.id.impact);
    }
}
