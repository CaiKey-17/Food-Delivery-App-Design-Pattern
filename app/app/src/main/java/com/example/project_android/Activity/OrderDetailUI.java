package com.example.project_android.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.LishDishOrderAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class OrderDetailUI extends AppCompatActivity {
    private RecyclerView list_dish_detail;
    private TextView tv_orderId;
    private int id = -1;
    private String order_id = "";
    private Button btn_accept,btn_denied;
    private ImageView imageView20;
    private int check  = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        list_dish_detail = findViewById(R.id.list_dish_detail);
        tv_orderId = findViewById(R.id.tv_orderId);
        btn_accept = findViewById(R.id.btn_accept);
        btn_denied = findViewById(R.id.btn_denied);
        imageView20 = findViewById(R.id.imageView20);


        imageView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_denied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<ResponseBody> call = api.cancel(order_id);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(view.getContext(), "Đã từ chối đơn hàng", Toast.LENGTH_SHORT).show();
                            finish();
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

        list_dish_detail.setLayoutManager(new LinearLayoutManager(OrderDetailUI.this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        order_id = intent.getStringExtra("order_id");

        tv_orderId.setText(order_id);

        loadListMenu();
        loadNameAndAddress();
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check == 0){
                    Toast.makeText(OrderDetailUI.this, "Vui lòng cập nhật tên và địa chỉ nhà hàng", Toast.LENGTH_SHORT).show();
                }
                else {

                    RetrofitService retrofitService = new RetrofitService();
                    APIUser api = retrofitService.getApiService();
                    Call<ResponseBody> call = api.accept(order_id);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(OrderDetailUI.this, "Đã chấp nhận đơn hàng", Toast.LENGTH_SHORT).show();
                                finish();
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
            }
        });


    }

    private void loadNameAndAddress() {
            RetrofitService retrofitService = new RetrofitService();
            APIUser api = retrofitService.getApiService();

            Call<Integer> call1 = api.checkNameAndAddress(id);
            call1.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        check = response.body();
                    } else {
                        System.out.println("Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                    System.out.println("Failure: " + t.getMessage());
                }
            });
    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.getDetailOrderOfRestaurant(order_id).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderDetail> dishes = response.body();
                    for(OrderDetail c: dishes){
                        Log.d("a",c.toString());
                    }
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetail>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<OrderDetail> dishes) {
        LishDishOrderAdapter adapter = new LishDishOrderAdapter(OrderDetailUI.this, dishes, new OnItemClickListener<OrderDetail>() {
            @Override
            public void onItemClick(OrderDetail item, int position) {

            }
            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        });
        list_dish_detail.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

}