package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.R;

public class SuccessRatePagerAdapter extends RecyclerView.Adapter<SuccessRatePagerAdapter.ViewHolder> {
    private LayoutInflater inflater;

    public SuccessRatePagerAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            // Load layout for week
            view = inflater.inflate(R.layout.item_week, parent, false);
        } else {
            // Load layout for month
            view = inflater.inflate(R.layout.item_month, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data if necessary
    }

    @Override
    public int getItemCount() {
        return 2; // Two pages: week and month
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}