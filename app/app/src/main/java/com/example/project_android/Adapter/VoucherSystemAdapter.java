package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.VoucherSystem;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;


public class VoucherSystemAdapter  extends RecyclerView.Adapter<VoucherSystemAdapter.VoucherSystemViewHolder> {

    private List<VoucherSystem> donhangs;
    private Context context;
    private OnItemClickListener<VoucherSystem> listener;
    private RetrofitService retrfitService;
    private double total=0;
    private int selectedPosition = -1;
    private RetrofitService retrofitService;

    public VoucherSystemAdapter(Context context, List<VoucherSystem> donhangs, OnItemClickListener<VoucherSystem> listener) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;

    }
    private void checkVoucherStatus(VoucherSystemAdapter.VoucherSystemViewHolder holder, VoucherSystem voucher, int position) {
        retrofitService.getApiService().checkExpiry1(voucher.getId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isVoucherExists = response.body();
                    if (isVoucherExists) {
                        holder.voucher_system.setVisibility(View.VISIBLE);
                    } else {
                        ViewGroup parent = (ViewGroup) holder.voucher_system.getParent();
                        if (parent != null) {
                            parent.removeView(holder.voucher_system);
                            donhangs.remove(position);
                            notifyItemRemoved(position);
                        }
                    }
                } else {
                    ViewGroup parent = (ViewGroup) holder.voucher_system.getParent();
                    if (parent != null) {
                        parent.removeView(holder.voucher_system);
                        donhangs.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                ViewGroup parent = (ViewGroup) holder.voucher_system.getParent();
                if (parent != null) {
                    parent.removeView(holder.voucher_system);
                    donhangs.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });
    }



    @NonNull
    @Override
    public VoucherSystemAdapter.VoucherSystemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_customer, parent, false);
        return new VoucherSystemAdapter.VoucherSystemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherSystemAdapter.VoucherSystemViewHolder holder, int position) {
        VoucherSystem voucherSystem = donhangs.get(position);

        holder.vc_name_system.setText(voucherSystem.getName());

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String voucher = numberFormat.format(voucherSystem.getPrice()) + "đ";


        holder.priceAdd.setText("Trị giá: "+ voucher);
        retrofitService = new RetrofitService();
        checkVoucherStatus(holder, voucherSystem,position);

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

        holder.btn_apply.setChecked(position == selectedPosition);

        holder.btn_apply.setOnClickListener(v -> {
            selectedPosition = position;
            listener.onVoucherSelected(voucherSystem.getId());
            listener.onPriceSelected(voucherSystem.getPrice());
            notifyDataSetChanged();
        });

    }



    @Override
    public int getItemCount() {
        return donhangs.size();
    }

    public static class VoucherSystemViewHolder extends RecyclerView.ViewHolder {
        TextView vc_name_system,vc_hansudung,priceAdd;

        LinearLayout voucher_system;
        RadioButton btn_apply;

        public VoucherSystemViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_apply = itemView.findViewById(R.id.btn_apply);
            vc_name_system = itemView.findViewById(R.id.nameAdd);
            vc_hansudung = itemView.findViewById(R.id.expiryAdd);
            voucher_system = itemView.findViewById(R.id.voucher_system);
            priceAdd = itemView.findViewById(R.id.priceAdd);
        }
    }
}