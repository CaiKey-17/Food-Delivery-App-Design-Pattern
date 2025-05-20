package com.example.project_android.Adapter;

import android.content.Context;
import android.util.Log;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class VoucherAddSysAdapter extends RecyclerView.Adapter<VoucherAddSysAdapter.VoucherViewHolder> {

    private List<VoucherSystem> vouchers;
    private Context context;
    private OnItemClickListener<VoucherSystem> listener4;
    private RetrofitService retrofitService;
    private int id;

    public VoucherAddSysAdapter(Context context, List<VoucherSystem> vouchers, OnItemClickListener<VoucherSystem> listener4,int id) {
        this.context = context;
        this.vouchers = vouchers;
        this.listener4 = listener4;
        this.id = id;

    }

    @NonNull
    @Override
    public VoucherAddSysAdapter.VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_add, parent, false);
        return new VoucherAddSysAdapter.VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAddSysAdapter.VoucherViewHolder holder, int position) {
        VoucherSystem voucher = vouchers.get(position);
        holder.nameAdd.setText(voucher.getName());
        holder.quantityAdd.setText("Số lượng còn lại: "+voucher.getQuantity());

        setExpiry(holder, voucher.getExpiryAsLocalDateTime());

        retrofitService = new RetrofitService();
        checkVoucherStatus(holder, voucher);
        holder.btn_VoucherAdd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Bật -> Đổi màu thành xám
                holder.btn_VoucherAdd.setBackgroundTintList(context.getResources().getColorStateList(com.google.android.libraries.places.R.color.quantum_grey100));
                holder.btn_VoucherAdd.setEnabled(false);
                Call<String> call = retrofitService.getApiService().getVoucherSys(id,voucher.getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            reloadVoucherData();
                            holder.btn_VoucherAdd.setEnabled(false);

                        } else {
                            holder.btn_VoucherAdd.setChecked(false);
                            holder.btn_VoucherAdd.setEnabled(true);
                            Toast.makeText(context, "Lỗi khi nhận voucher", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        holder.btn_VoucherAdd.setChecked(false);
                        holder.btn_VoucherAdd.setEnabled(true);
                        Toast.makeText(context, "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.btn_VoucherAdd.setChecked(true);
                holder.btn_VoucherAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.chudao));
            }
        });
    }

    private void reloadVoucherData() {
        retrofitService.getApiService().listVoucherSystemAdd().enqueue(new Callback<List<VoucherSystem>>() {
            @Override
            public void onResponse(Call<List<VoucherSystem>> call, Response<List<VoucherSystem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VoucherSystem> newVouchers = response.body();
                    updateVouchers(newVouchers);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<VoucherSystem>> call, Throwable t) {
                Log.e("API Error", "Failed to reload vouchers", t);
            }
        });
    }

    public void updateVouchers(List<VoucherSystem> newVouchers) {
        this.vouchers = newVouchers;
        notifyDataSetChanged();
    }
    private void checkVoucherStatus(VoucherAddSysAdapter.VoucherViewHolder holder, VoucherSystem voucher) {
        retrofitService.getApiService().checkSys(id, voucher.getId()).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean isVoucherExists = response.body();
                    if (isVoucherExists) {

                        holder.btn_VoucherAdd.setChecked(true);
                        holder.btn_VoucherAdd.setEnabled(false);
                        holder.btn_VoucherAdd.setBackgroundTintList(context.getResources().getColorStateList(com.google.android.libraries.places.R.color.quantum_grey100));
                    } else {

                        holder.btn_VoucherAdd.setChecked(false);
                        holder.btn_VoucherAdd.setEnabled(true);
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }
    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    private void setExpiry(VoucherViewHolder holder, LocalDateTime expiryDate) {
        if (expiryDate != null) {
            LocalDateTime currentDate = LocalDateTime.now();
            if (expiryDate.isAfter(currentDate)) {
                long daysLeft = ChronoUnit.DAYS.between(currentDate.toLocalDate(), expiryDate.toLocalDate());
                holder.expiryAdd.setText("Hạn sử dụng: "+daysLeft + " ngày nữa");
            } else {
                holder.expiryAdd.setText("Hạn sử dụng: "+"Đã hết hạn");
            }
        } else {
            holder.expiryAdd.setText("Hạn sử dụng: "+"Không xác định");
        }

    }

    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView nameAdd, quantityAdd,expiryAdd;
        LinearLayout voucher_add;
        ToggleButton btn_VoucherAdd;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            nameAdd = itemView.findViewById(R.id.nameAdd);
            quantityAdd = itemView.findViewById(R.id.quantityAdd);
            expiryAdd = itemView.findViewById(R.id.expiryAdd);
            btn_VoucherAdd = itemView.findViewById(R.id.btn_VoucherAdd);
            voucher_add = itemView.findViewById(R.id.voucher_add);
        }
    }
}
