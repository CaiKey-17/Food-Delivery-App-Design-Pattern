package com.example.project_android.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomerUpdateEmailSetting extends AppCompatActivity {

    private ImageView btnBack;
    private int id = -1;
    private String emailNum = "";
    private EditText edt_email_update, edt_verify;
    private LinearLayout hide;
    private Button btn_verify;
    private Button btn_ok;
    private String code = "";
    private String newEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update_email_setting);

        btnBack = findViewById(R.id.img_update_email_customer);
        edt_email_update = findViewById(R.id.edt_email_update);
        hide = findViewById(R.id.hide);
        edt_verify = findViewById(R.id.edt_verify);
        btn_verify = findViewById(R.id.btn_verify);
        btn_ok = findViewById(R.id.btn_ok);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        emailNum = sharedPreferences.getString("email", "");
        edt_email_update.setText(emailNum);

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_verify.setVisibility(View.GONE);
                btn_ok.setVisibility(View.VISIBLE);
                hide.setVisibility(View.VISIBLE);

                Map<String, Object> emailData = new HashMap<>();
                Random random = new Random();
                code = "";
                for (int i = 0; i < 6; i++) {
                    int digit = random.nextInt(10);
                    code += String.valueOf(digit);
                }


                newEmail = edt_email_update.getText().toString();
                emailData.put("email", newEmail);
                emailData.put("code", code);


                RetrofitService retrofitService = new RetrofitService();
                APIUser api = retrofitService.getApiService();
                Call<String> call = api.sendChangeEmailNotification(emailData);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CustomerUpdateEmailSetting.this, "Đã gửi mã qua email", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("Error: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        System.out.println("Failure: " + t.getMessage());
                    }
                });

            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = edt_verify.getText().toString();
                if (temp.equals(code)) {

                    newEmail = edt_email_update.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", newEmail);
                    editor.apply();
                    Toast.makeText(CustomerUpdateEmailSetting.this, "Đã thay đổi email thành công", Toast.LENGTH_SHORT).show();

                    RetrofitService retrofitService = new RetrofitService();
                    APIUser api = retrofitService.getApiService();


                    Call<Integer> call = api.changeEmail(id, newEmail);

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
                } else {
                    Toast.makeText(CustomerUpdateEmailSetting.this, "Không đúng mã code", Toast.LENGTH_SHORT).show();
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
}