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

public class CustomerUpdatePhoneSetting extends AppCompatActivity {
    private ImageView btnBack;
    private int id = -1;
    private String phoneNum = "";
    private EditText edt_phone;
    private Button btn_updatePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update_phone_setting);
        edt_phone = findViewById(R.id.edt_diachi);
        btn_updatePhone = findViewById(R.id.btn_updatePhone);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        phoneNum = sharedPreferences.getString("phone", "");

        edt_phone.setText(phoneNum);

        btnBack = findViewById(R.id.img_update_phone_customer);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_updatePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = edt_phone.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("phone", num);
                editor.apply();
                Toast.makeText(CustomerUpdatePhoneSetting.this, "Đổi số điện thoại thành công", Toast.LENGTH_SHORT).show();

                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<Integer> call = api.changePhoneNumber(id, num);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            finish();
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
        });

    }
}