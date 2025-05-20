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
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.Model.Restaurant;
import com.example.project_android.R;
import com.example.project_android.Config.IP;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class ListRestaurantAdapter  extends RecyclerView.Adapter<ListRestaurantAdapter.ListRestaurantViewHolder> {

    private List<Restaurant> donhangs;
    private Context context;
    private OnItemClickListener<Restaurant> listener;
    private RetrofitService retrfitService;

    public ListRestaurantAdapter(Context context, List<Restaurant> donhangs, OnItemClickListener<Restaurant> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;
    }




    @NonNull
    @Override
    public ListRestaurantAdapter.ListRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food_horizontal_customer, parent, false);
        return new ListRestaurantAdapter.ListRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRestaurantAdapter.ListRestaurantViewHolder holder, int position) {
        Restaurant dish = donhangs.get(position);

        holder.tv_name_cart.setText(dish.getName());
        holder.tv_star_hor.setText(dish.getRating() + "");

        //Chưa
        holder.tv_location_hor.setText(String.format("%.2f km", dish.getDistance()));

        retrfitService = new RetrofitService();
        String imageUrl = dish.getImage();
        if (imageUrl != null) {
            String fullImageUrl = IP.network + imageUrl;
            Glide.with(context)
                    .load(fullImageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.dishImage);
        } else {
            Log.e("DishAdapter", "Image URL is null for dish: " + dish.getName());
            holder.dishImage.setImageResource(R.drawable.icon_google);
        }
        holder.linear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(dish, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return donhangs.size();
    }

    public static class ListRestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_cart,tv_location_hor,tv_star_hor;
        ImageView dishImage;
        LinearLayout linear_cart;

        public ListRestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_cart = itemView.findViewById(R.id.tv_name_cart);
            tv_star_hor = itemView.findViewById(R.id.tv_star_hor);
            tv_location_hor = itemView.findViewById(R.id.tv_location_hor);
            dishImage = itemView.findViewById(R.id.img_listfood_hor);
            linear_cart = itemView.findViewById(R.id.list_restaurant);
        }
    }
}

