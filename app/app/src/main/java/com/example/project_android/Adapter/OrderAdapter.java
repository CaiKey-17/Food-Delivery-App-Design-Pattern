package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.SellingOrder;
import com.example.project_android.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<SellingOrder> dishList;
    private Context context;
    private OnItemClickListener<SellingOrder> listener;
    public OrderAdapter(Context context, List<SellingOrder> dishList, OnItemClickListener<SellingOrder> listener) {
        this.context = context;
        this.dishList = dishList;
        this.listener = listener;
    }




    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requested_dish_restaurant, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        SellingOrder dish = dishList.get(position);
        holder.dishName.setText(dish.getId());

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(dish.getTotal()) + "đ";

        holder.tv_total.setText("Tổng: "+formattedTotal);

        holder.list_order_restaurant.setOnClickListener(new View.OnClickListener() {
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

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView dishName,tv_total;
        LinearLayout list_order_restaurant;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.tv_orderId);
            tv_total = itemView.findViewById(R.id.tv_total);
            list_order_restaurant = itemView.findViewById(R.id.list_order_restaurant);
        }
    }
}