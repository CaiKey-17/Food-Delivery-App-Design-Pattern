package com.example.project_android.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Model.Trip;
import com.example.project_android.R;

import java.util.List;

public class TripHistoryAdapter extends RecyclerView.Adapter<TripHistoryAdapter.TripViewHolder> {

    private List<Trip> tripList;
    private OnItemClickListener listener;

    // Constructor
    public TripHistoryAdapter(List<Trip> tripList, OnItemClickListener listener) {
        this.tripList = tripList;
        this.listener = listener;
    }

    // ViewHolder to hold the item views
    public static class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView tripDate, tripBegin,tripEnd;

        public TripViewHolder(View itemView) {
            super(itemView);
            tripDate = itemView.findViewById(R.id.tripDate);
            tripBegin = itemView.findViewById(R.id.tripBegin);
            tripEnd = itemView.findViewById(R.id.tripEnd);
        }

        // Bind method to set click listener for each item
        public void bind(final OnItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHistoryClick(position);
                }
            });
        }
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tripDate.setText(trip.getDate());
        holder.tripBegin.setText(trip.getBegin());
        holder.tripEnd.setText(trip.getEnd());
        holder.bind(listener, position);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public interface OnItemClickListener {
        void onHistoryClick(int position);
    }
}
