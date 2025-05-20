package com.example.project_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.project_android.Model.Restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

public class AdminRestaurantAdapter extends RecyclerView.Adapter<AdminRestaurantAdapter.ViewHolder>{

    private List<Restaurant> donhangs;
    private Context context;
    private OnItemClickListener<Restaurant> listener;

    public AdminRestaurantAdapter(Context context, List<Restaurant> donhangs, OnItemClickListener<Restaurant> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Restaurant restaurant = donhangs.get(position);
        holder.nameTextView.setText(restaurant.getName());
        holder.addressTextView.setText(restaurant.getAddress());


        String imageUrl = restaurant.getImage();
        if (imageUrl != null) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.image_res);
        } else {

            holder.image_res.setImageResource(R.drawable.ic_launcher_background); // Hoặc hình ảnh mặc định
        }
    }

    @Override
    public int getItemCount() {
        return donhangs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        ImageView image_res;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.restaurant_name);
            addressTextView = itemView.findViewById(R.id.restaurant_address);
            image_res = itemView.findViewById(R.id.image_res);
        }
    }
}
