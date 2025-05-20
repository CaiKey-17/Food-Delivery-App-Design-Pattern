package com.example.project_android.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_android.Adapter.*;
import com.example.project_android.Interface.OnItemClickListener;
//import com.example.project_android.Interface.OnItemClickListener3;
import com.example.project_android.Model.Dish;
import com.example.project_android.Model.GroupDish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCustomerActivity extends AppCompatActivity {
    private int id = -1;
    private RecyclerView recyclerView, recFood, recMenu;
    private SearchHistoryAdapter adapter;
    private List<String> searchHistoryList;
    private EditText edtSearch;
    private ImageView btn_back;
    private CardView btn_searchDish;

    private void saveSearchHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("SearchPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(searchHistoryList);
        editor.putString("search_history_" + id, json);
        editor.apply();
    }

    private void loadSearchHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("SearchPrefs", MODE_PRIVATE);
        String json = sharedPreferences.getString("search_history_" + id, null);

        if (json != null) {
            Gson gson = new Gson();
            searchHistoryList = gson.fromJson(json, ArrayList.class);
        } else {
            searchHistoryList = new ArrayList<>();
        }
    }

    public void removeSearchHistoryItem(String itemToRemove) {
        SharedPreferences sharedPreferences = getSharedPreferences("SearchPrefs", MODE_PRIVATE);
        Gson gson = new Gson();


        String key = "search_history_" + id;
        String json = sharedPreferences.getString(key, null);
        List<String> historyList = gson.fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());


        if (historyList != null && historyList.contains(itemToRemove)) {
            historyList.remove(itemToRemove);


            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(key, gson.toJson(historyList));
            editor.apply();


            searchHistoryList.remove(itemToRemove);
            adapter.notifyDataSetChanged();


            if (searchHistoryList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer);

        edtSearch = findViewById(R.id.edt_search_customerRes);
        btn_back = findViewById(R.id.back_search_customer);

        recyclerView = findViewById(R.id.rec_search_history);
        recFood = findViewById(R.id.rec_search_food);
        recMenu = findViewById(R.id.rec_search_menu);
        btn_searchDish = findViewById(R.id.btn_searchDishGroup);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        loadSearchHistory();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new SearchHistoryAdapter(this, searchHistoryList, id);
        recyclerView.setAdapter(adapter);


        if (!searchHistoryList.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }


        btn_searchDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = edtSearch.getText().toString().trim();
                if (!search.isEmpty()) {
                    searchHistoryList.add(search);
                    adapter.notifyDataSetChanged();
                    edtSearch.setText("");


                    recyclerView.setVisibility(View.VISIBLE);
                    saveSearchHistory();

                    Intent intent = new Intent(SearchCustomerActivity.this, CustomerListDishSearch.class);
                    intent.putExtra("nameDish", search);
                    startActivity(intent);
                } else {
                    Toast.makeText(SearchCustomerActivity.this, "Nhập thông tin cầm tìm", Toast.LENGTH_SHORT).show();
                }
            }
        });


        recFood.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recMenu.setLayoutManager(new GridLayoutManager(this, 2));
        loadListMenu();
        loadListMenu2();
    }


    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.listGroupDish().enqueue(new Callback<List<GroupDish>>() {
            @Override
            public void onResponse(Call<List<GroupDish>> call, Response<List<GroupDish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GroupDish> dishes = response.body();
                    for (GroupDish dish : dishes) {
                        Log.d("Dish", dish.toString());
                    }
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<GroupDish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<GroupDish> dishes) {
        MenuFoodSearchAdapter adapter = new MenuFoodSearchAdapter(SearchCustomerActivity.this, dishes, new OnItemClickListener<GroupDish>() {
            @Override
            public void onItemClick(GroupDish item, int position) {
                Intent intent = new Intent(SearchCustomerActivity.this, RiceListMenu.class);
                intent.putExtra("items_id", item.getName());
                startActivity(intent);
            }
            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        });
        recMenu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadListMenu2() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();


        api.listDishGoiY().enqueue(new Callback<List<Dish>>() {
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
        ListFoodCustomerAdapter adapter = new ListFoodCustomerAdapter(SearchCustomerActivity.this, dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(SearchCustomerActivity.this, ShowDetail.class);
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
        recFood.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}