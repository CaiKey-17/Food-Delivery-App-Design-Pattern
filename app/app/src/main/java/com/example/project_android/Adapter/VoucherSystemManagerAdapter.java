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
import com.example.project_android.Model.VoucherSystem;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class VoucherSystemManagerAdapter  extends RecyclerView.Adapter<VoucherSystemManagerAdapter.VoucherSystemManagerViewHolder> {

    private List<VoucherSystem> donhangs;
    private Context context;
    private OnItemClickListener<VoucherSystem> listener;
    private RetrofitService retrfitService;
    private double total=0;
    private int selectedPosition = -1;
    private RetrofitService retrofitService;

    public VoucherSystemManagerAdapter(Context context, List<VoucherSystem> donhangs, OnItemClickListener<VoucherSystem> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;

    }



    @NonNull
    @Override
    public VoucherSystemManagerAdapter.VoucherSystemManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_manager, parent, false);
        return new VoucherSystemManagerAdapter.VoucherSystemManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherSystemManagerAdapter.VoucherSystemManagerViewHolder holder, int position) {
        VoucherSystem voucherSystem = donhangs.get(position);

        holder.vc_name_system.setText(voucherSystem.getName());
        retrofitService = new RetrofitService();
        holder.quantityAdd.setText("Số lượng: "+voucherSystem.getQuantity());
        if (voucherSystem.getExpiryAsLocalDateTime() != null) {
            LocalDateTime expiryDate = voucherSystem.getExpiryAsLocalDateTime();
            LocalDateTime currentDate = LocalDateTime.now();

            if (expiryDate.isAfter(currentDate)) {
                long daysLeft = ChronoUnit.DAYS.between(currentDate.toLocalDate(), expiryDate.toLocalDate());
                holder.vc_hansudung.setText(daysLeft + " ngày nữa");
            } else {
                holder.vc_hansudung.setText("Đã hết hạn");
            }
        } else {
            holder.vc_hansudung.setText("Không xác định");
        }

    }



    @Override
    public int getItemCount() {
        return donhangs.size();
    }

    public static class VoucherSystemManagerViewHolder extends RecyclerView.ViewHolder {
        TextView vc_name_system,vc_hansudung,quantityAdd;

        LinearLayout voucher_system;

        public VoucherSystemManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            vc_name_system = itemView.findViewById(R.id.nameAdd);
            vc_hansudung = itemView.findViewById(R.id.expiryAdd);
            voucher_system = itemView.findViewById(R.id.voucher_add);
            quantityAdd = itemView.findViewById(R.id.quantityAdd);
        }
    }
}