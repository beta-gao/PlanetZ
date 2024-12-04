package com.example.planetz.DailyActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.planetz.R;

import java.util.List;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ActivityLogViewHolder> {

    private final List<ActivityLog> activityLogs;

    public ActivityLogAdapter(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    @NonNull
    @Override
    public ActivityLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_log, parent, false);
        return new ActivityLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityLogViewHolder holder, int position) {
        ActivityLog activityLog = activityLogs.get(position);
        holder.activityTypeView.setText(activityLog.getActivityType());
        holder.activityEmissionView.setText(String.format("%.2f kg CO2e", activityLog.getValue()));
    }

    @Override
    public int getItemCount() {
        return activityLogs.size();
    }

    public static class ActivityLogViewHolder extends RecyclerView.ViewHolder {
        public TextView activityTypeView;
        public TextView activityEmissionView;

        public ActivityLogViewHolder(@NonNull View itemView) {
            super(itemView);
            activityTypeView = itemView.findViewById(R.id.activityType);
            activityEmissionView = itemView.findViewById(R.id.activityEmission);
        }
    }
}
