package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project_android.Adapter.DishHomeAdapter;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShowDetail extends AppCompatActivity {
    private TextView tx_name_dish;
    private TextView tx_price_dish;
    private TextView tx_mota_dish;
    private TextView tx_soluong_dish, tx_quantity;
    private CardView btn_back_home;
    private ImageView image_dish;
    private Button btn_addToCart;
    private ImageButton btn_cong, btn_tru;
    private RetrofitService retrfitService;
    private int id_user = -1;
    private int id = -1;
    private RecyclerView recyclerView;

    private int id_restaurant = -1;
    private int quantity = 1;
    private int quantity_restaurant = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        tx_name_dish = findViewById(R.id.tx_name_dish);
        tx_price_dish = findViewById(R.id.tx_price_dish);
        tx_mota_dish = findViewById(R.id.tx_mota_dish);
        tx_soluong_dish = findViewById(R.id.tx_soluong_dish);
        image_dish = findViewById(R.id.image_dish);
        btn_back_home = findViewById(R.id.btn_back_home);
        tx_quantity = findViewById(R.id.tv_quantity);
        btn_addToCart = findViewById(R.id.btn_addToCart);
        btn_cong = findViewById(R.id.btn_cong);
        btn_tru = findViewById(R.id.btn_tru);
        recyclerView = findViewById(R.id.list_dish_restaurant);

        recyclerView.setLayoutManager(new LinearLayoutManager(ShowDetail.this, LinearLayoutManager.HORIZONTAL, false));


        btn_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity < quantity_restaurant) {
                    quantity++;
                    tx_quantity.setText(quantity + "");
                }
            }
        });

        btn_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 1) {
                    quantity--;
                    tx_quantity.setText(quantity + "");
                }
            }
        });


        retrfitService = new RetrofitService();

        btn_back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent = getIntent();
        id = intent.getIntExtra("id_item", -1);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id_user = sharedPreferences.getInt("userId", -1);
        loadListMenu();



        Call<Integer> call_id = retrfitService.getApiService().getIdRestaurant(id);
        call_id.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call_id, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    id_restaurant = response.body();
                    loadListMenu();
                } else {
                    System.out.println("User not found or error occurred");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                t.printStackTrace();
            }
        });


        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.valueOf(tx_soluong_dish.getText().toString());
                if (temp <= 0) {
                    Toast.makeText(ShowDetail.this, "Hết hàng", Toast.LENGTH_SHORT).show();

                } else {
                    quantity = Integer.valueOf(tx_quantity.getText().toString());
                    Call<String> call_add = retrfitService.getApiService().addDishToCart(id_user, id_restaurant, id, quantity);
                    call_add.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call_add, Response<String> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ShowDetail.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ShowDetail.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call_add, Throwable t) {
                            Toast.makeText(ShowDetail.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.detailDish(id).enqueue(new Callback<Dish>() {
            @Override
            public void onResponse(Call<Dish> call, Response<Dish> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Dish dish = response.body();
                    quantity_restaurant = dish.getQuantity();
                    tx_name_dish.setText(dish.getName());


                    NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                    String formattedTotal = numberFormat.format(dish.getPrice()) + "đ";

                    tx_price_dish.setText(formattedTotal);

                    tx_mota_dish.setText(dish.getDescribe_dish());
                    tx_soluong_dish.setText(dish.getQuantity() + "");


                    String imageUrl = IP.network + dish.getImage();
                    Glide.with(ShowDetail.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(image_dish);

                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<Dish> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });

    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listRestaurantDish(id_restaurant).enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dish> dishes = response.body();
                    for (Dish dish : dishes) {
                        Log.d("Dish", dish.toString());
                    }
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<Dish> dishes) {
        DishHomeAdapter adapter = new DishHomeAdapter(ShowDetail.this, dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(ShowDetail.this, ShowDetail.class);
                intent.putExtra("id_item", item.getId());
                startActivity(intent);
            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        }, id_user);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}