package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.LishDishOrderAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class OrderDetailCustomer extends AppCompatActivity {
    private RecyclerView list_dish_detail;
    private TextView tv_orderId;
    private int id = -1;
    private String order_id = "";
    private ImageView imageView20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_detail_customer);
        list_dish_detail = findViewById(R.id.list_dish_detail);
        tv_orderId = findViewById(R.id.tv_orderId);
        imageView20 = findViewById(R.id.imageView20);

        imageView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        list_dish_detail.setLayoutManager(new LinearLayoutManager(OrderDetailCustomer.this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);

        tv_orderId.setText(order_id);

        loadListMenu();


    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.getDetailOrderOfRestaurant(order_id).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderDetail> dishes = response.body();
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
        LishDishOrderAdapter adapter = new LishDishOrderAdapter(OrderDetailCustomer.this, dishes, new OnItemClickListener<OrderDetail>() {
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