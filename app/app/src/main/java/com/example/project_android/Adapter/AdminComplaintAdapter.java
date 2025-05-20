package com.example.project_android.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Model.Complaint;
import com.example.project_android.Model.Driver;
import com.example.project_android.R;

import java.util.ArrayList;
import java.util.List;
public class AdminComplaintAdapter extends RecyclerView.Adapter<AdminComplaintAdapter.ViewHolder> {
    private List<Complaint> complaintList;
    private List<Complaint> filter;
    private Context context;

    public AdminComplaintAdapter(Context context,List<Complaint> complaintList) {
        this.complaintList = complaintList;
        this.filter = new ArrayList<>(complaintList);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint = filter.get(position);
        holder.restaurantName.setText(complaint.getRestaurantName());
        holder.complaintDate.setText(complaint.getDate().toString());
        holder.complaintContent.setText(complaint.getComplaintContent());
        holder.resolveButton.setText(complaint.isResolved() ? "Đã duyệt" : "Duyệt khiếu nại");

        holder.resolveButton.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return filter.size();
    }
    public void filter(String query) {
        filter.clear();
        if (query.isEmpty()) {
            filter.addAll(complaintList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Complaint complaint : complaintList) {
                if (complaint.getRestaurantName().toLowerCase().contains(lowerCaseQuery) ||
                        complaint.getTypeComplaint().toLowerCase().contains(lowerCaseQuery)) {
                    filter.add(complaint);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName, complaintDate, complaintContent;
        Button resolveButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            complaintDate = itemView.findViewById(R.id.complaint_date);
            complaintContent = itemView.findViewById(R.id.complaint_content);
            resolveButton = itemView.findViewById(R.id.resolve_button);
        }
    }
}