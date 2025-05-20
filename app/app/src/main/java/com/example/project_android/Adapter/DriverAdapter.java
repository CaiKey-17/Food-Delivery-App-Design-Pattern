package com.example.project_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_android.Activity.DriverDetailActivity;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Driver;
import com.example.project_android.Model.SellingOrder;
import com.example.project_android.Model.Shiper;
import com.example.project_android.R;

import java.util.ArrayList;
import java.util.List;
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {


    private List<Shiper> donhangs;
    private Context context;
    private OnItemClickListener<Shiper> listener;


    public DriverAdapter(Context context, List<Shiper> donhangs, OnItemClickListener<Shiper> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Shiper driver = donhangs.get(position);


        holder.tvDriverName.setText(driver.getName());
        if (driver.getAddress() != null && !driver.getAddress().isEmpty()) {
            holder.tvLicensePlate.setText(driver.getAddress());
            holder.tvLicensePlate.setVisibility(View.VISIBLE);
        } else {
            holder.tvLicensePlate.setVisibility(View.GONE);
        }


        String imageUrl = driver.getImage();
        if (imageUrl != null) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.ivProfileImage);
        } else {

            holder.ivProfileImage.setImageResource(R.drawable.ic_launcher_background); // Hoặc hình ảnh mặc định
        }

    }

    @Override
    public int getItemCount() {
        return donhangs.size();
    }



    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView tvDriverName, tvLicensePlate;
        ImageView ivProfileImage;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDriverName = itemView.findViewById(R.id.tvDriverName);
            tvLicensePlate = itemView.findViewById(R.id.tvLicensePlate);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
        }
    }
}