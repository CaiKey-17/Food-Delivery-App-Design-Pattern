package com.example.project_android.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class VoucherRestaurantActiveAdapter extends RecyclerView.Adapter<VoucherRestaurantActiveAdapter.VoucherActiveViewHolder> {

    private List<Voucher_restaurant> voucherList;
    private Context context;

    public VoucherRestaurantActiveAdapter(Context context, List<Voucher_restaurant> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }

    @NonNull
    @Override
    public VoucherActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_voucher, parent, false);
        return new VoucherActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherActiveViewHolder holder, int position) {
        Voucher_restaurant voucherRestaurant = voucherList.get(position);

        holder.tx_voucher_name_active.setText(voucherRestaurant.getName());
        holder.tx_voucher_quantity_active.setText(String.valueOf(voucherRestaurant.getQuantity()));

        if (voucherRestaurant.getExpiryAsLocalDateTime() != null) {
            LocalDateTime expiryDate = voucherRestaurant.getExpiryAsLocalDateTime();
            LocalDateTime currentDate = LocalDateTime.now();

            if (expiryDate.isAfter(currentDate)) {
                long daysLeft = ChronoUnit.DAYS.between(currentDate.toLocalDate(), expiryDate.toLocalDate());
                holder.tx_voucher_expiry_active.setText(daysLeft + " ngày nữa");
            } else {
                holder.tx_voucher_expiry_active.setText("Đã hết hạn");
            }
        } else {
            holder.tx_voucher_expiry_active.setText("Không xác định");
        }

    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public static class VoucherActiveViewHolder extends RecyclerView.ViewHolder {
        TextView tx_voucher_name_active, tx_voucher_quantity_active, tx_voucher_expiry_active;

        public VoucherActiveViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_voucher_name_active = itemView.findViewById(R.id.tx_voucher_name_active);
            tx_voucher_quantity_active = itemView.findViewById(R.id.tx_voucher_quantity_active);
            tx_voucher_expiry_active = itemView.findViewById(R.id.tx_voucher_expiry_active);
        }
    }
}
