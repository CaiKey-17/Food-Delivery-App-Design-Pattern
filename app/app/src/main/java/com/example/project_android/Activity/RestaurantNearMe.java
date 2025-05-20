package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.ListDishCartAdapter;
import com.example.project_android.Adapter.ListRestaurantAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.Model.Restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class RestaurantNearMe extends AppCompatActivity {
    private RecyclerView rec_search_menu;
    private ImageView back_search_customer;
    private int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_near_me);
        rec_search_menu = findViewById(R.id.rec_search_menu);
        back_search_customer = findViewById(R.id.back_search_customer);
        rec_search_menu.setLayoutManager(new LinearLayoutManager(RestaurantNearMe.this, LinearLayoutManager.VERTICAL, false));
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        loadListMenu();
        back_search_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listAllRestaurant(id).enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Restaurant> dishes = response.body();
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<Restaurant> dishes) {
        ListRestaurantAdapter adapter = new ListRestaurantAdapter(RestaurantNearMe.this, dishes, new OnItemClickListener<Restaurant>() {
            @Override
            public void onItemClick(Restaurant item, int position) {
                Intent intent = new Intent(RestaurantNearMe.this, FoodListInRestaurant.class);
                intent.putExtra("id_item", item.getId());
                intent.putExtra("name", item.getName());
                intent.putExtra("image", item.getImage());
                startActivity(intent);
            }
            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        });
        rec_search_menu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}