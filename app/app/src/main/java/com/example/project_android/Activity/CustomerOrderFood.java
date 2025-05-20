



package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.Manifest;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.LishDishOrderAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.OrderDetail;
import com.example.project_android.Model.Restaurant;
import com.example.project_android.Model.SellingOrder;
import com.example.project_android.Pattern.OrderObserver;
import com.example.project_android.Pattern.OrderManager;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.google.android.gms.tasks.OnSuccessListener;
import android.location.Location;

import static android.content.pm.PackageManager.*;

public class CustomerOrderFood extends AppCompatActivity implements OrderObserver {
    private int id = -1,check=0;
    private RecyclerView recyclerView;
    private double total = 0;
    private TextView tong,tong1,tenquan,tv_price_voucher,phi,phiNH;
    private RetrofitService retrfitService;
    private String name_restaurant = "";
    private LinearLayout btn_add_voucher,show_voucher,tinhphi;
    private RadioButton position_now;
    private CheckBox banking,cash;
    private Button button6;
    private int checkPayment = -1;
    private String orderId = "";
    private  Double sum = 0.0;
    private static final int REQUEST_CODE_ADD_VOUCHER = 17;
    private Double lati=0.0;

    private Double longi=0.0;
    private ImageView btn_back;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_food);
        recyclerView = findViewById(R.id.list_dish_order);
        tong = findViewById(R.id.tong);
        tong1 = findViewById(R.id.tong1);
        tenquan = findViewById(R.id.tenquan);
        btn_add_voucher = findViewById(R.id.btn_add_voucher);
        tv_price_voucher = findViewById(R.id.tv_price_voucher);
        show_voucher = findViewById(R.id.show_voucher);
        position_now = findViewById(R.id.position_now);
        banking = findViewById(R.id.banking);
        cash = findViewById(R.id.cash);
        button6 = findViewById(R.id.button6);
        btn_back = findViewById(R.id.btn_back);
        phi = findViewById(R.id.phi);
        phiNH = findViewById(R.id.phiNH);
        tinhphi = findViewById(R.id.tinhphi);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        show_voucher.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerOrderFood.this, LinearLayoutManager.VERTICAL, false));
        retrfitService = new RetrofitService();


        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        loadListMenu();
        loadNamRest();
        loadNamOrderId();

        btn_add_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerOrderFood.this, CustomerAddVoucher.class);
                intent1.putExtra("total",total);

                startActivityForResult(intent1, REQUEST_CODE_ADD_VOUCHER);
            }
        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        position_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermissionAndFetchLocation();
            }
        });


        banking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cash.setChecked(false);
                checkPayment = 1;
                tinhphi.setVisibility(View.VISIBLE);
                double fee = total*0.3;
                double feeN = (total+fee)*0.01;
                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                String formattedTotal = numberFormat.format(total) + "đ";
                String phigiao = "+ "+ numberFormat.format(fee) + "đ";
                String phiBaking = "+ "+ numberFormat.format(feeN) + "đ";
                String re = numberFormat.format(total+fee+feeN) + "đ";

                tong.setText(formattedTotal);
                phi.setText(phigiao);
                phiNH.setText(phiBaking);
                tong1.setText(re);

            } else if (!cash.isChecked()) {
                tinhphi.setVisibility(View.GONE);
                double fee = total*0.3;
                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                String re = numberFormat.format(total+fee) + "đ";
                tong1.setText(re);
            }
        });

        cash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                banking.setChecked(false);
                checkPayment = 2;
                tinhphi.setVisibility(View.GONE);

                double fee = total*0.3;
                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                String re = numberFormat.format(total+fee) + "đ";
                tong1.setText(re);

            } else if (!banking.isChecked()) {
                tinhphi.setVisibility(View.GONE);


            }
        });

        loadPhoneAndAddress();


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!position_now.isChecked()) {
                    Toast.makeText(CustomerOrderFood.this, "Vui lòng chọn địa điểm nhận hàng.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkPayment == -1) {
                    Toast.makeText(CustomerOrderFood.this, "Vui lòng chọn phương thức thanh toán.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (check == 0) {
                    Toast.makeText(CustomerOrderFood.this, "Vui lòng cập nhật địa chỉ và số điện thoại.", Toast.LENGTH_SHORT).show();
                    return;
                }



                Call<String> call = retrfitService.getApiService().confirm(orderId,checkPayment,lati,longi);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CustomerOrderFood.this, "Đặt đơn thành công", Toast.LENGTH_LONG).show();
                            OrderManager.getInstance().registerObserver(CustomerOrderFood.this);
                            OrderManager.getInstance().fetchOrders(id);
                            finish();

                        } else {
                            Toast.makeText(CustomerOrderFood.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(CustomerOrderFood.this, "Đặt đơn thành công", Toast.LENGTH_SHORT).show();
                        OrderManager.getInstance().registerObserver(CustomerOrderFood.this);
                        OrderManager.getInstance().fetchOrders(id);

                        finish();
                    }
                });
            }
        });
    }

    private void loadPhoneAndAddress() {
            RetrofitService retrofitService = new RetrofitService();
            APIUser api = retrofitService.getApiService();

            Call<Integer> call1 = api.checkPhoneAndAddress(id);
            call1.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        check = response.body();
                    } else {
                        System.out.println("Error: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                    System.out.println("Failure: " + t.getMessage());
                }
            });
    }

    private void checkLocationPermissionAndFetchLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {

            fetchCurrentLocation();
        }
    }

    private void fetchCurrentLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lati = location.getLatitude();
                            longi = location.getLongitude();

                        } else {
                            Toast.makeText(CustomerOrderFood.this,
                                    "Unable to get current location",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {

                fetchCurrentLocation();
            } else {

                Toast.makeText(this, "Location permission is required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listDishInCart(id).enqueue(new Callback<List<OrderDetail>>() {
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
        LishDishOrderAdapter adapter = new LishDishOrderAdapter(CustomerOrderFood.this, dishes, new OnItemClickListener<OrderDetail>() {
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
    }
    public void updateTotal(List<OrderDetail> dishes) {
        total = 0;
        for (OrderDetail dish : dishes) {
            total += dish.getTotal();
        }
        double fee = total*0.3;

        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotal = numberFormat.format(total) + "đ";
        String phigiao = "+ "+ numberFormat.format(fee) + "đ";

        String re = numberFormat.format(total+fee) + "đ";

        tong.setText(formattedTotal);
        phi.setText(phigiao);

        tong1.setText(re);

    }


    private void loadNamRest() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.getNameRestaurant(id).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call_id, Response<Restaurant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Restaurant restaurant = response.body();

                    tenquan.setText(restaurant.getName());
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.e("API Error", "Failed to load restaurant name", t);
                Toast.makeText(CustomerOrderFood.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNamOrderId() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.getOrderId(id).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call_id, Response<Map<String, String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, String> data = response.body();

                    orderId = data.get("orderId");

                    if (orderId != null) {
                        Log.d("Order ID", "Order ID: " + orderId);
                    } else {
                        Log.e("API Error", "orderId not found in the response.");
                    }
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                Log.e("API Error", "Failed to load order ID", t);
                Toast.makeText(CustomerOrderFood.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_VOUCHER) {
            if (resultCode == RESULT_OK) {
                Call<Map<String, Double>> call2 = retrfitService.getApiService().getSumVoucher(orderId);
                call2.enqueue(new Callback<Map<String, Double>>() {
                    @Override
                    public void onResponse(Call<Map<String, Double>> call2, Response<Map<String, Double>> response) {
                        if (response.isSuccessful()) {
                            sum = response.body().get("orderId");
                            if (sum > total) {

                                Toast.makeText(CustomerOrderFood.this, "Voucher không thể áp dụng.", Toast.LENGTH_LONG).show();
                                Intent intent1 = new Intent(CustomerOrderFood.this, CustomerAddVoucher.class);
                                startActivityForResult(intent1, REQUEST_CODE_ADD_VOUCHER);
                            } else {


                                NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
                                String voucher = numberFormat.format(sum) + "đ";


                                tv_price_voucher.setText("- " + voucher);
                                show_voucher.setVisibility(View.VISIBLE);
                                Double temp = total - sum;

                                double fee = total*0.3;

                                double feeN = (temp+fee)*0.01;
                                String phiBaking = "+ "+ numberFormat.format(feeN) + "đ";

                                String re = numberFormat.format(temp+fee+feeN) + "đ";
                                phiNH.setText(phiBaking);
                                tong1.setText(re);
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, Double>> call2, Throwable t) {
                    }
                });

            }
        }
    }

    @Override
    public void onOrdersUpdated(List<SellingOrder> orders) {

        Toast.makeText(this, "Bạn đang có " + orders.size() + " đơn hàng!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable t) {
        Toast.makeText(this, "Lỗi khi tải đơn hàng: " + t.getMessage(), Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        OrderManager.getInstance().unregisterObserver(this);
    }

}