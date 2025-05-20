package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.VoucherRestaurantAdapter;
import com.example.project_android.Adapter.VoucherSystemAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.*;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;
import java.util.Map;

public class CustomerAddVoucher extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private int id = -1;
    private double total = 0.0;
    private int id_res = -1;
    private String id_order = "";
    private Double sum = 0.0;
    private ImageView imageView12;
    private Button btn_dungngay;
    private int selectedVoucherSystemId = -1;
    private int selectedVoucherRestaurantId = -1;
    private RetrofitService retrfitService;
    private Double value = 0.0, value1 = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_add_voucher);
        recyclerView = findViewById(R.id.list_voucher_systemAdd);
        recyclerView1 = findViewById(R.id.list_voucher_restaurantAdd);
        btn_dungngay = findViewById(R.id.btn_dungngay);
        imageView12 = findViewById(R.id.imageView12);

        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerAddVoucher.this, LinearLayoutManager.VERTICAL, false));
        recyclerView1.setLayoutManager(new LinearLayoutManager(CustomerAddVoucher.this, LinearLayoutManager.VERTICAL, false));
        retrfitService = new RetrofitService();

        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        total = intent.getDoubleExtra("total", 0.0);

        loadNamRest();
        loadListMenu(id);
        loadListMenu1(id);

        getIdOrder();


    }

    private void loadListMenu(int id) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        api.listVoucherSystemOfCustomer1(id).enqueue(new Callback<List<VoucherSystem>>() {
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
        VoucherSystemAdapter adapter = new VoucherSystemAdapter(this, dishes, new OnItemClickListener<VoucherSystem>() {
            @Override
            public void onItemClick(VoucherSystem item, int position) {

            }

            @Override
            public void onVoucherSelected(int voucherId) { // Thay đổi kiểu dữ liệu tại đây
                selectedVoucherSystemId = voucherId;
            }

            @Override
            public void onPriceSelected(double price) {
                value1 = price;
                updateTotalValue();
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void loadListMenu1(int id) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        api.listVoucherRestaurantOfCustomerInCart(id, id_res).enqueue(new Callback<List<Voucher_restaurant>>() {
            @Override
            public void onResponse(Call<List<Voucher_restaurant>> call, Response<List<Voucher_restaurant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher_restaurant> dishes = response.body();

                    populateListView1(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Voucher_restaurant>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }

    private boolean updateTotalValue() {
        double totalPrice = value + value1;
        if (totalPrice > total) {
            return false;
        }
        return true;
    }

    private void populateListView1(List<Voucher_restaurant> dishes) {
        VoucherRestaurantAdapter adapter = new VoucherRestaurantAdapter(this, dishes, new OnItemClickListener<Voucher_restaurant>() {
            @Override
            public void onItemClick(Voucher_restaurant item, int position) {

            }

            @Override
            public void onVoucherSelected(int voucherId) {
                selectedVoucherRestaurantId = voucherId;
            }

            @Override
            public void onPriceSelected(double price) {
                value = price;
                updateTotalValue();

            }
        });
        recyclerView1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadNamRest() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.getNameRestaurant(id).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call_id, Response<Restaurant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Restaurant restaurant = response.body();
                    id_res = (restaurant.getId());
                    loadListMenu1(id);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e("API Error", "Failed to load restaurant name", t);
                Toast.makeText(CustomerAddVoucher.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIdOrder() {
        Call<Map<String, String>> call = retrfitService.getApiService().getOrderId(id);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    id_order = response.body().get("orderId");
                    btn_dungngay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (selectedVoucherSystemId != -1 && selectedVoucherRestaurantId != -1) {
                                if (updateTotalValue()) {


                                    Call<String> call = retrfitService.getApiService().applyVoucherRes(id_order, selectedVoucherRestaurantId);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful()) {
                                            } else {
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                        }
                                    });

                                    Call<String> call1 = retrfitService.getApiService().applyVoucherSys(id_order, selectedVoucherSystemId);
                                    call1.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call1, Response<String> response) {
                                            if (response.isSuccessful()) {

                                            } else {
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call1, Throwable t) {
                                        }
                                    });
                                    Intent resultIntent = new Intent();
                                    setResult(RESULT_OK, resultIntent);
                                    finish();
                                } else {
                                    Toast.makeText(CustomerAddVoucher.this, "Không thể áp dụng!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            if (selectedVoucherRestaurantId != -1 && selectedVoucherSystemId == -1) {
                                if (updateTotalValue()) {
                                    Call<String> call = retrfitService.getApiService().applyVoucherRes(id_order, selectedVoucherRestaurantId);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful()) {
                                            } else {


                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                        }
                                    });
                                    Call<String> call1 = retrfitService.getApiService().applyVoucherSys(id_order, selectedVoucherSystemId);
                                    call1.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call1, Response<String> response) {
                                            if (response.isSuccessful()) {

                                            } else {
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call1, Throwable t) {
                                        }
                                    });
                                    Intent resultIntent = new Intent();
                                    setResult(RESULT_OK, resultIntent);
                                    finish();
                                } else {
                                    Toast.makeText(CustomerAddVoucher.this, "Không thể áp dụng!", Toast.LENGTH_SHORT).show();
                                }

                            }

                            if (selectedVoucherSystemId != -1 && selectedVoucherRestaurantId == -1) {
                                if (updateTotalValue()) {

                                    Call<String> call1 = retrfitService.getApiService().applyVoucherSys(id_order, selectedVoucherSystemId);
                                    call1.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call1, Response<String> response) {
                                            if (response.isSuccessful()) {

                                            } else {
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call1, Throwable t) {
                                        }
                                    });

                                    Call<String> call = retrfitService.getApiService().applyVoucherRes(id_order, selectedVoucherRestaurantId);
                                    call.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.isSuccessful()) {
                                            } else {


                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                        }
                                    });


                                    Intent resultIntent = new Intent();
                                    setResult(RESULT_OK, resultIntent);
                                    finish();
                                } else {
                                    Toast.makeText(CustomerAddVoucher.this, "Không thể áp dụng!", Toast.LENGTH_SHORT).show();
                                }


                            }


                            if (selectedVoucherSystemId == -1 && selectedVoucherRestaurantId == -1) {
                                Toast.makeText(CustomerAddVoucher.this, "Vui lòng chọn voucher", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                } else {
                    Log.e("API Error", "Response code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(CustomerAddVoucher.this, "Error: " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Toast.makeText(CustomerAddVoucher.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}