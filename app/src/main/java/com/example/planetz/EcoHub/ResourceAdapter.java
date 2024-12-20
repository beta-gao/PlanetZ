package com.example.planetz.EcoHub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planetz.R;
import com.example.planetz.model.Resource;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {

    private final List<Resource> resources;
    private Context context = null;

    public ResourceAdapter(List<Resource> resources) {
        this.context = context;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resource resource = resources.get(position);
        holder.titleTextView.setText(resource.getTitle());
        holder.descriptionTextView.setText(resource.getDescription());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resource.getUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.resourceTitle);
            descriptionTextView = itemView.findViewById(R.id.resourceDescription);
        }
    }
}