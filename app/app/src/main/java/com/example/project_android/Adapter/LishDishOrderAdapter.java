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
import com.example.project_android.Activity.CustomerOrderFood;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class LishDishOrderAdapter  extends RecyclerView.Adapter<LishDishOrderAdapter.DishCartViewHolder> {

    private List<OrderDetail> donhangs;
    private Context context;
    private OnItemClickListener<OrderDetail> listener;
    private RetrofitService retrfitService;
    private double total=0;

    public LishDishOrderAdapter(Context context, List<OrderDetail> donhangs, OnItemClickListener<OrderDetail> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;

    }




    @NonNull
    @Override
    public LishDishOrderAdapter.DishCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_order, parent, false);
        return new LishDishOrderAdapter.DishCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LishDishOrderAdapter.DishCartViewHolder holder, int position) {
        OrderDetail dish = donhangs.get(position);
        total+=dish.getTotal();
        holder.tv_name_cart.setText(dish.getName());

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(dish.getPrice()) + "đ";
        String formattedTotal1 = numberFormat.format(dish.getTotal()) + "đ";

        holder.tv_price_cart.setText(formattedTotal);

        holder.tv_quantity.setText(dish.getQuantity()+"");
        holder.tv_tong.setText(formattedTotal1);

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
            holder.dishImage.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.linear_order.setOnClickListener(new View.OnClickListener() {
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

    public static class DishCartViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_cart, tv_price_cart,tv_quantity,tv_tong;
        ImageView dishImage;
        LinearLayout linear_order;
        Button btn_xoa_cart;

        public DishCartViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_cart = itemView.findViewById(R.id.tv_name_cart);
            tv_price_cart = itemView.findViewById(R.id.tv_price_cart);
            tv_quantity = itemView.findViewById(R.id.tv_quantity_cart);
            dishImage = itemView.findViewById(R.id.img_listfood_hor);
            tv_tong = itemView.findViewById(R.id.tv_tong);

            linear_order = itemView.findViewById(R.id.linear_order);
        }
    }
}

