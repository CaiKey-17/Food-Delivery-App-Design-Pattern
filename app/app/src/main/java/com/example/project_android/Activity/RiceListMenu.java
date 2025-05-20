package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.DishOfGroupAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class RiceListMenu extends AppCompatActivity {
    private EditText edt_search_customerRes;
    private ImageView imageView13;
    private RecyclerView recyclerView;
    private DishOfGroupAdapter adapter;
    private int id = -1;
    private CardView btn_searchDishGroup;
    private FloatingActionButton giohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice_list_menu);

        recyclerView = findViewById(R.id.rec_search_rice);
        recyclerView.setLayoutManager(new LinearLayoutManager(RiceListMenu.this));

        edt_search_customerRes = findViewById(R.id.edt_search_customerRes);
        imageView13 = findViewById(R.id.imageView13);
        btn_searchDishGroup = findViewById(R.id.btn_searchDishGroup);
        giohang = findViewById(R.id.btn_giohang);

        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RiceListMenu.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String text = intent.getStringExtra("items_id");
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);

        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadListMenu(text);

        btn_searchDishGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_search_customerRes.getText().toString().trim();
                loadListMenu1(text, name);
            }
        });

    }

    private void loadListMenu(String text) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.listGroupDish(text).enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dish> dishes = response.body();
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }

    private void loadListMenu1(String text, String name) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.listGroupDishSearch(text, name).enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dish> dishes = response.body();
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<Dish> dishes) {
        DishOfGroupAdapter adapter = new DishOfGroupAdapter(this, dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(RiceListMenu.this, ShowDetail.class);
                intent.putExtra("id_item", item.getId());
                startActivity(intent);
            }
            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        }, id);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}