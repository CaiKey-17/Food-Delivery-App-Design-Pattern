package com.example.project_android.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DishOfGroupAdapter  extends RecyclerView.Adapter<DishOfGroupAdapter.DishGroupViewHolder> {

    private List<Dish> dishList;
    private Context context;
    private OnItemClickListener listener;
    private RetrofitService retrfitService;
    private int id ;
    public DishOfGroupAdapter(Context context, List<Dish> dishList, OnItemClickListener listener, int id) {
        this.context = context;
        this.dishList = dishList;
        this.listener = listener;
        this.id = id;
    }




    @NonNull
    @Override
    public DishOfGroupAdapter.DishGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_in_restaurant, parent, false);
        return new DishOfGroupAdapter.DishGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishOfGroupAdapter.DishGroupViewHolder holder, int position) {
        Dish dish = dishList.get(position);

        holder.dishName.setText(dish.getName());
        retrfitService = new RetrofitService();

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(dish.getPrice()) + "đ";

        holder.dishPrice.setText(formattedTotal);

        String imageUrl = dish.getImage();
        if (imageUrl != null) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.dishImage);
        } else {
            Log.e("DishAdapter", "Image URL is null for dish: " + dish.getName());
            holder.dishImage.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.detailDishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(dish, position);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call_add = retrfitService.getApiService().addDishToCart(id, dish.getId_fk_restaurant(),dish.getId(),1);
                call_add.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call_add, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call_add, Throwable t) {
                        Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class DishGroupViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, dishPrice;
        ImageView dishImage;
        ImageButton addToCart;
        LinearLayout detailDishLayout;

        public DishGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.tx_name_home);
            dishPrice = itemView.findViewById(R.id.tx_price_home);
            dishImage = itemView.findViewById(R.id.image_home);
            addToCart = itemView.findViewById(R.id.addToCart_home);

            detailDishLayout = itemView.findViewById(R.id.item_dish);
        }
    }
}
