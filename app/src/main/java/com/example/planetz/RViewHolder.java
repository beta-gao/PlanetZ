package com.example.planetz;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RViewHolder extends RecyclerView.ViewHolder{

    ImageView imgView;
    TextView HabitNameView;
    TextView CategoryView;
    RelativeLayout relativeLayout;
    public RViewHolder(@NonNull View itemView) {
        super(itemView);
        imgView = itemView.findViewById(R.id.img);
        HabitNameView = itemView.findViewById(R.id.HabitName);
        CategoryView = itemView.findViewById(R.id.Category);
        relativeLayout = itemView.findViewById(R.id.onehabitrow);
    }
}
