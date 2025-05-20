package com.example.project_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;



public class VoucherRestaurantAccept extends RecyclerView.Adapter<VoucherRestaurantAccept.VoucherAcceptViewHolder> {

    private List<Voucher_restaurant> voucherList;
    private Context context;

    public VoucherRestaurantAccept(Context context, List<Voucher_restaurant> voucherList) {
        this.context = context;
        this.voucherList = voucherList;
    }



    @NonNull
    @Override
    public VoucherAcceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accept_voucher, parent, false);
        return new VoucherAcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherAcceptViewHolder holder, int position) {
        Voucher_restaurant voucherRestaurant = voucherList.get(position);

        holder.tx_voucher_name.setText(voucherRestaurant.getName());
        holder.tx_voucher_quantity.setText(String.valueOf(voucherRestaurant.getQuantity()));

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(voucherRestaurant.getPrice()) + "đ";

        holder.tx_voucher_price.setText(formattedTotal);


        if (voucherRestaurant.getExpiryAsLocalDateTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            holder.tx_voucher_expiry.setText(voucherRestaurant.getExpiryAsLocalDateTime().format(formatter));
        } else {
            holder.tx_voucher_expiry.setText("Không xác định");
        }
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<ResponseBody> call = api.deleteVoucher(voucherRestaurant.getId());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            voucherList.remove(position);
                            Toast.makeText(view.getContext(), "Đã từ chối voucher này", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            System.out.println("Voucher deleted successfully!");
                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Failure: " + t.getMessage());
                    }
                });
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<String> call = api.activeVocher(voucherRestaurant.getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            voucherList.remove(position);
                            Toast.makeText(view.getContext(), "Phê duyệt thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            // Xử lý khi có lỗi xảy ra
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println("Failure: " + t.getMessage());
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    public static class VoucherAcceptViewHolder extends RecyclerView.ViewHolder {
        TextView tx_voucher_name, tx_voucher_quantity, tx_voucher_price, tx_voucher_expiry;
        Button cancel,accept;

        public VoucherAcceptViewHolder(@NonNull View itemView) {
            super(itemView);
            tx_voucher_name = itemView.findViewById(R.id.tx_voucher_name);
            tx_voucher_quantity = itemView.findViewById(R.id.tx_voucher_quantity);
            tx_voucher_price = itemView.findViewById(R.id.tx_voucher_price);
            tx_voucher_expiry = itemView.findViewById(R.id.tx_voucher_expiry);
            cancel = itemView.findViewById(R.id.cancel);
            accept = itemView.findViewById(R.id.accept);
        }
    }
}
