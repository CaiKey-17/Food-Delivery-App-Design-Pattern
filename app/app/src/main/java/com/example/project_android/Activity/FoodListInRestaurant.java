package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.project_android.Adapter.DishHomeAdapter;
import com.example.project_android.Adapter.DishOfGroupAdapter;
import com.example.project_android.Adapter.ListDishCartAdapter;
import com.example.project_android.Config.IP;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.Model.Dish;
import com.example.project_android.Model.Restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class FoodListInRestaurant extends AppCompatActivity {

    private Button btn_shopping_cart;
    private TextView name_res, price, tv_rating, tv_distance;
    private RecyclerView list_ngang, list_doc;
    private ImageView imageView16;
    private CardView back;
    private double total = 0;

    private int id = -1;
    private int id_restaurant = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_in_restaurant);
        btn_shopping_cart = findViewById(R.id.btn_shopping_cart);
        name_res = findViewById(R.id.name_res);
        list_ngang = findViewById(R.id.list_ngang);
        list_doc = findViewById(R.id.list_doc);
        imageView16 = findViewById(R.id.imageView16);
        tv_rating = findViewById(R.id.tv_rating);
        tv_distance = findViewById(R.id.tv_distance);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        list_ngang.setLayoutManager(new LinearLayoutManager(FoodListInRestaurant.this, LinearLayoutManager.HORIZONTAL, false));
        list_doc.setLayoutManager(new LinearLayoutManager(FoodListInRestaurant.this, LinearLayoutManager.VERTICAL, false));


        Intent intent = getIntent();
        id_restaurant = intent.getIntExtra("id_item", -1);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);


        loadListMenu();
        loadListMenu2();
        loadData(id_restaurant);


        btn_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        FoodListInRestaurant.this, R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(FoodListInRestaurant.this)
                        .inflate(R.layout.activity_shopping_cart_in_restaurant, null, false);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2000
                );
                bottomSheetView.setLayoutParams(params);

                bottomSheetDialog.setCancelable(true);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                bottomSheetDialog.setOnShowListener(dialog -> {
                    BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setHideable(false);
                    behavior.setDraggable(false);
                });

                ImageView closeButton = bottomSheetView.findViewById(R.id.btn_back_bottom_sheet);
                closeButton.setOnClickListener(v -> bottomSheetDialog.dismiss());


                Bundle bundle = new Bundle();
                bundle.putInt("id", id);


                loadListMenu3(id, bottomSheetView);

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

    }


    private void loadData(int id_restaurant) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        api.detailOfRestaurant(id, id_restaurant).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Restaurant dishes = response.body();

                    name_res.setText(dishes.getName());
                    tv_rating.setText(dishes.getRating() + "");
                    tv_distance.setText(String.format("%.2f km", dishes.getDistance()));

                    String imageUrl = dishes.getImage();
                    if (imageUrl != null) {
                        String fullImageUrl = IP.network + imageUrl;
                        Glide.with(FoodListInRestaurant.this)
                                .load(fullImageUrl)
                                .placeholder(R.drawable.rice_list)
                                .into(imageView16);
                    } else {
                        imageView16.setImageResource(R.drawable.icon_google);
                    }
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void loadListMenu3(int id, View bottomSheetView) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.list_order);
        Button btn_pay = bottomSheetView.findViewById(R.id.btn_pay);
        price = bottomSheetView.findViewById(R.id.price);
        TextView textView57 = bottomSheetView.findViewById(R.id.textView57);

        textView57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListInRestaurant.this, ShoppingCartActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(FoodListInRestaurant.this));

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListInRestaurant.this, CustomerOrderFood.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        api.listDishInCart(id).enqueue(new Callback<List<OrderDetail>>() {
            @Override
            public void onResponse(Call<List<OrderDetail>> call, Response<List<OrderDetail>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderDetail> dishes = response.body();
                    ListDishCartAdapter adapter = new ListDishCartAdapter(FoodListInRestaurant.this, dishes, new OnItemClickListener<OrderDetail>() {
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
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    updateTotal(dishes);
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

    public void updateTotal(List<OrderDetail> dishes) {
        total = 0;
        for (OrderDetail dish : dishes) {
            total += dish.getTotal();
        }
        price.setText("Tổng giỏ hàng: " + total + "đ");
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
        DishHomeAdapter adapter = new DishHomeAdapter(FoodListInRestaurant.this, dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(FoodListInRestaurant.this, ShowDetail.class);
                intent.putExtra("id", id);
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
        list_ngang.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void loadListMenu2() {
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
                    populateListView2(dishes);
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


    private void populateListView2(List<Dish> dishes) {
        DishOfGroupAdapter adapter = new DishOfGroupAdapter(FoodListInRestaurant.this, dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(FoodListInRestaurant.this, ShowDetail.class);
                intent.putExtra("id", id);
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
        list_doc.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}