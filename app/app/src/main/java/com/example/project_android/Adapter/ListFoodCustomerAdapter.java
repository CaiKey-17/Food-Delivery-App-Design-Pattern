package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Fragment.ListFoodCustomer;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListFoodCustomerAdapter extends RecyclerView.Adapter<ListFoodCustomer> {

    Context context;
    List<Dish> items;
    private OnItemClickListener listener;
    private int id;



    public ListFoodCustomerAdapter(Context context, List<Dish> dishList, OnItemClickListener listener, int id) {
        this.context = context;
        this.items = dishList;
        this.listener = listener;
        this.id = id;
    }

    @NonNull
    @Override
    public ListFoodCustomer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food_customer, parent, false);
        return new ListFoodCustomer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFoodCustomer holder, int position) {
        Dish groupDish = items.get(position);

        holder.name.setText(items.get(position).getName());
        holder.star.setText(items.get(position).getRating()+"/5");

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(groupDish.getPrice()) + "đ";

        holder.price.setText(formattedTotal);

        String imageUrl = groupDish.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(holder.imageView.getContext())
                    .load(fullImageUrl)
                    .placeholder(R.drawable.rice)
                    .error(R.drawable.rice)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.detailDishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(groupDish, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}