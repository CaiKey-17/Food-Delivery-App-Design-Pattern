package com.example.project_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Activity.FoodListInRestaurant;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class VoucherRestaurantValidAdapter  extends RecyclerView.Adapter<VoucherRestaurantValidAdapter.VoucherResViewHolder> {

    private List<Voucher_restaurant> donhangs;
    private Context context;
    private OnItemClickListener listener;
    private RetrofitService retrfitService;
    private int id;
    private RetrofitService retrofitService;
    private ProgressBar progressBar;

    public VoucherRestaurantValidAdapter(Context context, List<Voucher_restaurant> donhangs, OnItemClickListener listener,int id,ProgressBar progressBar) {
        this.context = context;
        this.donhangs = donhangs;
        this.listener = listener;
        this.id = id;
        this.progressBar = progressBar;

    }




    @NonNull
    @Override
    public VoucherRestaurantValidAdapter.VoucherResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_voucher_customer_valid, parent, false);
        return new VoucherRestaurantValidAdapter.VoucherResViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherRestaurantValidAdapter.VoucherResViewHolder holder, int position) {
        Voucher_restaurant voucherRestaurant = donhangs.get(position);

        holder.vc_name_system.setText(voucherRestaurant.getName());
        retrofitService = new RetrofitService();

        checkVoucherStatusAndQuantity(holder, voucherRestaurant);


        if (voucherRestaurant.getExpiryAsLocalDateTime() != null) {
            LocalDateTime expiryDate = voucherRestaurant.getExpiryAsLocalDateTime();
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

        holder.btn_VoucherAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodListInRestaurant.class);
                intent.putExtra("id_item", voucherRestaurant.getId_restaurant());
                context.startActivity(intent);

            }
        });



    }

    private void checkVoucherStatusAndQuantity(VoucherRestaurantValidAdapter.VoucherResViewHolder holder, Voucher_restaurant voucher) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        retrofitService.getApiService().checkExpiryAndQuantity(id, voucher.getId()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (response.isSuccessful() && response.body() != null) {
                    int result = response.body();
                    switch (result) {
                        case 1:
                            holder.btn_VoucherAdd.setEnabled(false);
                            holder.btn_VoucherAdd.setText("Hết hạn");
                            holder.dongduoi.setVisibility(View.GONE);
                            holder.btn_VoucherAdd.setBackgroundTintList(context.getResources().getColorStateList(com.google.android.libraries.places.R.color.quantum_grey100));
                            break;

                        case 2:
                            holder.btn_VoucherAdd.setEnabled(false);
                            holder.btn_VoucherAdd.setText("Hết lượt dùng");
                            holder.dongduoi.setVisibility(View.GONE);
                            holder.btn_VoucherAdd.setBackgroundTintList(context.getResources().getColorStateList(com.google.android.libraries.places.R.color.quantum_grey100));
                            break;

                        case 3:
                            holder.btn_VoucherAdd.setEnabled(true);
                            holder.btn_VoucherAdd.setBackgroundTintList(context.getResources().getColorStateList(R.color.chudao));
                            break;

                        default:
                            handleUnknownState(holder);
                            break;
                    }
                } else {
                    handleUnknownState(holder);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                handleUnknownState(holder);
            }
        });
    }

    private void handleUnknownState(VoucherRestaurantValidAdapter.VoucherResViewHolder holder) {
        holder.btn_VoucherAdd.setEnabled(false);
        holder.btn_VoucherAdd.setText("Không khả dụng");
        holder.dongduoi.setVisibility(View.GONE);
        holder.btn_VoucherAdd.setBackgroundTintList(context.getResources().getColorStateList(com.google.android.libraries.places.R.color.quantum_grey100));
    }



    @Override
    public int getItemCount() {
        return donhangs.size();
    }

    public static class VoucherResViewHolder extends RecyclerView.ViewHolder {
        TextView vc_name_system,vc_hansudung;

        LinearLayout voucher_system,dongduoi;
        Button btn_VoucherAdd;

        public VoucherResViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_VoucherAdd = itemView.findViewById(R.id.btn_apply);
            vc_name_system = itemView.findViewById(R.id.nameAdd);
            vc_hansudung = itemView.findViewById(R.id.expiryAdd);
            voucher_system = itemView.findViewById(R.id.voucher_system);
            dongduoi = itemView.findViewById(R.id.dongduoi);
        }
    }
}