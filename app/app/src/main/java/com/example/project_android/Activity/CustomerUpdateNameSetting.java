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

public class CustomerUpdateNameSetting extends AppCompatActivity {

    private ImageView btnBack;
    private EditText name_update;
    private Button btn_update_name;
    private int id = -1;
    private String nameNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update_name_setting);

        btnBack = findViewById(R.id.img_update_name_customer);
        name_update = findViewById(R.id.name_update);
        btn_update_name = findViewById(R.id.btn_update_name);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        nameNum = sharedPreferences.getString("name", "");
        name_update.setText(nameNum);

        btn_update_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = name_update.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", num);
                editor.apply();
                Toast.makeText(CustomerUpdateNameSetting.this, "Đổi tên thành công", Toast.LENGTH_SHORT).show();

                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<Integer> call = api.changeName(id, num);
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}