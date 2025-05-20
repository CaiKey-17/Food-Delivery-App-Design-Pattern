package com.example.project_android.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishList;
    private Context context;
    private OnItemClickListener listener;

    private List<Dish> originalList;

//    public DishAdapter(Context context, List<Dish> dishList, OnItemClickListener listener) {
//        this.context = context;
//        this.dishList = dishList;
//        this.listener = listener;
//
//    }

    public DishAdapter(Context context, List<Dish> dishList, OnItemClickListener<Dish> listener) {
        this.context = context;
        this.dishList = new ArrayList<>(dishList);
        this.originalList = new ArrayList<>(dishList);
        this.listener = listener;
    }

    public void filter(String query) {
        dishList.clear();
        if (query.isEmpty()) {
            dishList.addAll(originalList);
        } else {
            for (Dish dish : originalList) {
                if (dish.getName().toLowerCase().contains(query.toLowerCase())) {
                    dishList.add(dish);
                }
            }
        }
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishList.get(position);


        holder.dishName.setText(dish.getName());
        holder.dishQuantity.setText("Số lượng: " + dish.getQuantity());

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(dish.getPrice()) + "đ";

        holder.dishPrice.setText("Giá: "+formattedTotal);

        String imageUrl = dish.getImage();
        Log.d("a","b: "+imageUrl);
        if (imageUrl != null) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.dishImage);
        } else {
            // Xử lý trường hợp imageUrl là null
            Log.e("DishAdapter", "Image URL is null for dish: " + dish.getName());
            holder.dishImage.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.detailDishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(dish, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, dishQuantity, dishPrice;
        ImageView dishImage;
        LinearLayout detailDishLayout;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishNameTextView);
            dishQuantity = itemView.findViewById(R.id.dishQuantityTextView);
            dishPrice = itemView.findViewById(R.id.dishPriceTextView);
            dishImage = itemView.findViewById(R.id.dishImageView);
            detailDishLayout = itemView.findViewById(R.id.detail_dish);
        }
    }
}
