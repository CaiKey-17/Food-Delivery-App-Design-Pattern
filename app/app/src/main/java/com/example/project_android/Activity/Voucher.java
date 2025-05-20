package com.example.project_android.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.VoucherAddResAdapter;
import com.example.project_android.Adapter.VoucherAddSysAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.VoucherSystem;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class Voucher extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private int id = -1;
    private ImageView imageView12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        recyclerView= findViewById(R.id.list_voucher_systemAdd);
        recyclerView1= findViewById(R.id.list_voucher_restaurantAdd);
        imageView12= findViewById(R.id.imageView12);

        recyclerView.setLayoutManager(new LinearLayoutManager(Voucher.this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setLayoutManager(new LinearLayoutManager(Voucher.this, LinearLayoutManager.VERTICAL, false));

        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        loadListMenu();
    }


    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        api.listVoucherRestaurantAdd().enqueue(new Callback<List<Voucher_restaurant>>() {
            @Override
            public void onResponse(Call<List<Voucher_restaurant>> call, Response<List<Voucher_restaurant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher_restaurant> dishes = response.body();
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Voucher_restaurant>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });

        APIUser api1 = retrofitService.getApiService();
        api1.listVoucherSystemAdd().enqueue(new Callback<List<VoucherSystem>>() {
            @Override
            public void onResponse(Call<List<VoucherSystem>> call, Response<List<VoucherSystem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VoucherSystem> dishes = response.body();

                    populateListView1(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<VoucherSystem>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }



    private void populateListView(List<Voucher_restaurant> dishes) {
        VoucherAddResAdapter adapter = new VoucherAddResAdapter(this, dishes, new OnItemClickListener<Voucher_restaurant>() {
            @Override
            public void onItemClick(Voucher_restaurant item, int position) {

            }
            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        },id);
        recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void populateListView1(List<VoucherSystem> dishes) {
        VoucherAddSysAdapter adapter = new VoucherAddSysAdapter(this, dishes, new OnItemClickListener<VoucherSystem>() {
            @Override
            public void onItemClick(VoucherSystem item, int position) {

            }
            @Override
            public void onVoucherSelected(int voucherId) {
            }

            @Override
            public void onPriceSelected(double price) {

            }
        },id);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}