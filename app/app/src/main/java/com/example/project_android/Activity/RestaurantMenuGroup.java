package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.*;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.MenuGroupAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.GroupDish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantMenuGroup extends AppCompatActivity {
    ImageView btn_backDish;
    RecyclerView recyclerView;
    MenuGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu_group);

        btn_backDish = findViewById(R.id.btn_backDish);
        recyclerView = findViewById(R.id.recyclerView);

        btn_backDish.setOnClickListener(view -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int id = sharedPreferences.getInt("userId", -1);


        loadListMenu(id);
    }

    private void loadListMenu(int id) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listGroupDish().enqueue(new Callback<List<GroupDish>>() {
            @Override
            public void onResponse(Call<List<GroupDish>> call, Response<List<GroupDish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GroupDish> dishes = response.body();
                    populateListView(dishes,id);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<GroupDish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }

    private void populateListView(List<GroupDish> dishes,int id) {
        adapter = new MenuGroupAdapter(dishes, new OnItemClickListener<GroupDish>() {
            @Override
            public void onItemClick(GroupDish item, int position) {
                Intent intent = new Intent(RestaurantMenuGroup.this, RestaurantMenuAdd.class);
                intent.putExtra("items_id", item.getName());
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }

            @Override
            public void onVoucherSelected(int voucherId) {
            }

            @Override
            public void onPriceSelected(double price) {
            }
        });


        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
