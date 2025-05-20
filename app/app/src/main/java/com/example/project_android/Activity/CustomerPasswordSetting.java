package com.example.project_android.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomerPasswordSetting extends AppCompatActivity {
    private ImageView btnBack;
    private TextInputEditText password_recent,password_new,password_valid;
    private Button btn_update_password;
    private RetrofitService retrfitService;
    private int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_password_setting);

        btnBack = findViewById(R.id.img_password_setting_customer);
        password_recent = findViewById(R.id.password_recent);
        password_new = findViewById(R.id.password_new);
        password_valid = findViewById(R.id.password_valid);
        btn_update_password = findViewById(R.id.btn_update_password);

        retrfitService = new RetrofitService();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String newPassword = password_new.getText().toString();
                String validPassword = password_valid.getText().toString();
                String passwordRecent = password_recent.getText().toString();
                String password = hashPassword(passwordRecent);

                if(newPassword.isEmpty()){
                    Toast.makeText(CustomerPasswordSetting.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else if(!newPassword.equals(validPassword)){
                    Toast.makeText(CustomerPasswordSetting.this, "Không khớp mật khẩu", Toast.LENGTH_SHORT).show();

                }
                else {
                    Call<Boolean> call = retrfitService.getApiService().checkPassword(id, password);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if (response.isSuccessful()) {
                                Boolean check = response.body();
                                if (check) {
                                    if (newPassword.equals(validPassword)) {
                                        String p = hashPassword(newPassword);
                                        Toast.makeText(CustomerPasswordSetting.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        Call<Integer> call_add = retrfitService.getApiService().changePassword(id, p);
                                        call_add.enqueue(new Callback<Integer>() {
                                            @Override
                                            public void onResponse(Call<Integer> call_add, Response<Integer> response) {
                                                if (response.isSuccessful()) {
                                                    finish();
                                                } else {
                                                    Toast.makeText(CustomerPasswordSetting.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Integer> call_add, Throwable t) {
                                                Toast.makeText(CustomerPasswordSetting.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(CustomerPasswordSetting.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(CustomerPasswordSetting.this, "Sai mật khẩu" + response.message(), Toast.LENGTH_SHORT).show();
                                }

                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(CustomerPasswordSetting.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
        


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}