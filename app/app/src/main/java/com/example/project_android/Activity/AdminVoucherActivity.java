package com.example.project_android.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.VoucherSystemManagerAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.VoucherSystem;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class AdminVoucherActivity extends AppCompatActivity {

    private ImageView imageBack;
    private RecyclerView voucher_system_manager;
    private Button btn_add_system;
    private int id = -1;
    VoucherSystemManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_voucher);

        imageBack = findViewById(R.id.img_admin_voucher_back);
        voucher_system_manager = findViewById(R.id.voucher_system_manager);
        btn_add_system = findViewById(R.id.btn_add_system);

        btn_add_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminVoucherActivity.this, AdminAddVoucher.class);
                startActivityForResult(intent, 1);

            }
        });

        voucher_system_manager.setLayoutManager(new LinearLayoutManager(AdminVoucherActivity.this));

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);


        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadListMenu();

    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        api.listVoucherSystemManager().enqueue(new Callback<List<VoucherSystem>>() {
            @Override
            public void onResponse(Call<List<VoucherSystem>> call, Response<List<VoucherSystem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VoucherSystem> dishes = response.body();
                    populateListView(dishes);
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


    private void populateListView(List<VoucherSystem> dishes) {
        adapter = new VoucherSystemManagerAdapter(this, dishes, new OnItemClickListener<VoucherSystem>() {
            @Override
            public void onItemClick(VoucherSystem item, int position) {

            }

            @Override
            public void onVoucherSelected(int voucherId) {
            }

            @Override
            public void onPriceSelected(double price) {

            }
        });
        voucher_system_manager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            loadListMenu();

            adapter.notifyDataSetChanged();
        }
    }
}
