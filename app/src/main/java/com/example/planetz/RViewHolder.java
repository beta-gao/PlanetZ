package com.example.planetz;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RViewHolder extends RecyclerView.ViewHolder{

    ImageView imgView;
    TextView HabitNameView;
    TextView categoryView;
    TextView impactView;
    LinearLayout linearLayout;

    public RViewHolder(@NonNull View itemView) {
        super(itemView);
        imgView = itemView.findViewById(R.id.img);
        HabitNameView = itemView.findViewById(R.id.HabitName);
        categoryView = itemView.findViewById(R.id.Category);
        linearLayout = itemView.findViewById(R.id.onehabitrow);
        impactView = itemView.findViewById(R.id.Impact);
    }
}
