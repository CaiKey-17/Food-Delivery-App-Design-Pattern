package com.example.project_android.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerUpdateAddress extends AppCompatActivity {
    private int id = -1;
    private EditText edt_diachi;
    private Button btn_updateAddress;
    private ImageView img_update_phone_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update_address);

        edt_diachi = findViewById(R.id.edt_diachi);
        btn_updateAddress = findViewById(R.id.btn_updateAddress);
        img_update_phone_customer = findViewById(R.id.img_update_phone_customer);


        img_update_phone_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        loadInfor();


        btn_updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = edt_diachi.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("address", address);
                editor.apply();
                Toast.makeText(CustomerUpdateAddress.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<Integer> call1 = api.changeAddress(id, address);
                call1.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call1, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            finish();
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
    }

    public void loadInfor() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String address = sharedPreferences.getString("address", "");
        edt_diachi.setText(address);
    }
}