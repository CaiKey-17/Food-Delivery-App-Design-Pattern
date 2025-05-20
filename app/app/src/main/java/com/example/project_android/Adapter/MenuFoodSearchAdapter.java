package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Fragment.MenuFoodSearch;
import com.example.project_android.Interface.OnItemClickListener;
//import com.example.project_android.Interface.OnItemClickListener3;
import com.example.project_android.Model.GroupDish;
import com.example.project_android.R;

import java.util.List;

public class MenuFoodSearchAdapter extends RecyclerView.Adapter<MenuFoodSearch> {

    Context context;
    List<GroupDish> items;
    private OnItemClickListener<GroupDish> listener;


    public MenuFoodSearchAdapter(Context context, List<GroupDish> items, OnItemClickListener<GroupDish> listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuFoodSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_search_customer, parent, false);
        return new MenuFoodSearch(view) ;


    }

    @Override
    public void onBindViewHolder(@NonNull MenuFoodSearch holder, int position) {
        GroupDish groupDish = items.get(position);

        holder.name.setText(items.get(position).getName());
        String imageUrl = groupDish.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(holder.imageView.getContext())
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    GroupDish menuItem = items.get(adapterPosition);
                    if (listener != null) {
                        listener.onItemClick(menuItem, adapterPosition);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

