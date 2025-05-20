package com.example.project_android.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Fragment.RestaurantOrderPreOrderFragment;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.SellingOrder;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderAcceptAdapter extends RecyclerView.Adapter<OrderAcceptAdapter.OrderAcceptViewHolder> {

    private List<SellingOrder> dishList;
    private Context context;
    private OnItemClickListener<SellingOrder> listener;
    private RestaurantOrderPreOrderFragment fragment;
    public OrderAcceptAdapter(Context context, List<SellingOrder> dishList, OnItemClickListener<SellingOrder> listener,RestaurantOrderPreOrderFragment fragment) {
        this.context = context;
        this.dishList = dishList;
        this.listener = listener;
        this.fragment = fragment;
    }




    @NonNull
    @Override
    public OrderAcceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preparing_dish_restaurant, parent, false);
        return new OrderAcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAcceptViewHolder holder, int position) {
        SellingOrder dish = dishList.get(position);
        holder.dishName.setText(dish.getId());

        holder.list_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(dish, position);
            }
        });

        holder.btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<ResponseBody> call = api.done(dish.getId());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Đã chấp nhận đơn hàng", Toast.LENGTH_SHORT).show();
                            Call<double[]> call1 = api.getPositionRestaurant(dish.getId());
                            call1.enqueue(new Callback<double[]>() {
                                @Override
                                public void onResponse(Call<double[]> call, Response<double[]> response) {
                                    if (response.isSuccessful()) {
                                        double[] temp  = response.body();
                                        double lat_res = temp[0];
                                        double long_res = temp[1];





                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");

                                        String orderId = dish.getId(); // Tạo orderId từ dish ID
                                        Map<String, Object> orderData = new HashMap<>();
                                        orderData.put("longitude_cus", dish.getLongitude());
                                        orderData.put("latitude_cus", dish.getLatitude());
                                        orderData.put("longitude_res", long_res);
                                        orderData.put("latitude_res", lat_res);
                                        orderData.put("status", "0");

                                        // Thêm dữ liệu vào Firebase
                                        databaseReference.child(orderId).setValue(orderData)
                                                .addOnSuccessListener(unused ->
                                                        Log.d("Firebase", "Dữ liệu đơn hàng đã được thêm thành công")
                                                )
                                                .addOnFailureListener(e ->
                                                        Log.e("Firebase", "Lỗi khi thêm dữ liệu vào Firebase: " + e.getMessage())
                                                );

                                        fragment.loadListMenu();

                                    } else {
                                        System.out.println("Error: " + response.message());
                                    }
                                }

                                @Override
                                public void onFailure(Call<double[]> call, Throwable t) {
                                    System.out.println("Failure: " + t.getMessage());
                                }
                            });



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
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class OrderAcceptViewHolder extends RecyclerView.ViewHolder {
        TextView dishName;
        LinearLayout list_accept;
        Button btn_done;

        public OrderAcceptViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.order);
            btn_done = itemView.findViewById(R.id.btn_done);
            list_accept = itemView.findViewById(R.id.list_accept);
        }
    }
}