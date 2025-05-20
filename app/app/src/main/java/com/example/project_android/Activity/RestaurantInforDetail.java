package com.example.project_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.bumptech.glide.Glide;
import com.example.project_android.Config.IP;
import com.example.project_android.Model.User;
import com.example.project_android.Model.Shiper;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class RestaurantInforDetail extends AppCompatActivity {
    private ImageView btn_change_picture, btn_backDish;
    private EditText edt_address, edt_phoneNumber, edt_name;
    private ImageView imageView8;
    private Button button5;
    private int id = -1;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_infor_detail);
        btn_change_picture = findViewById(R.id.btn_change_picture);
        edt_address = findViewById(R.id.edt_address);
        edt_phoneNumber = findViewById(R.id.edt_phoneNumber);
        imageView8 = findViewById(R.id.imageView8);
        button5 = findViewById(R.id.button5);
        btn_backDish = findViewById(R.id.btn_backDish);
        edt_name = findViewById(R.id.edt_name);

        btn_backDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RestaurantInforDetail.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                String address = edt_address.getText().toString();
                String phone = edt_phoneNumber.getText().toString();

                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<Integer> call = api.changePhoneNumber(id, phone);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                        System.out.println("Failure: " + t.getMessage());
                    }
                });

                Call<Integer> call1 = api.changeAddress(id, address);
                call1.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call1, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RestaurantInforDetail.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call1, Throwable t) {

                        System.out.println("Failure: " + t.getMessage());
                    }
                });
                String result = edt_name.getText().toString();

                Call<Integer> call2 = api.updateNameRes(id, result);
                call2.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call1, Response<Integer> response) {
                        if (response.isSuccessful()) {

                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call1, Throwable t) {

                        System.out.println("Failure: " + t.getMessage());
                    }
                });


            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);


        loadInfor();
        loadName();


        btn_change_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantInforDetail.this, RestaurantInforDetailPicture.class);
                startActivity(intent);
            }
        });
    }

    private void loadName() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.getNameRes(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    name = response.body();
                    edt_name.setText(name);

                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }

    public void loadInfor() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        Call<User> call = api.getInfor(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    edt_address.setText(user.getAddress());
                    edt_phoneNumber.setText(user.getPhoneNumber());

                    String imageUrl = IP.network + user.getImage();
                    Glide.with(RestaurantInforDetail.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.avatar)
                            .into(imageView8);

                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                System.out.println("Failure: " + t.getMessage());
            }
        });
    }
}