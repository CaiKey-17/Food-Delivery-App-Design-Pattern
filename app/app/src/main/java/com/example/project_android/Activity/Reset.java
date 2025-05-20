package com.example.project_android.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.project_android.Model.User;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class Reset extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 100;
    private String message = " là mật khẩu mới của bạn, vui lòng không chia sẻ cho bất kỳ ai";
    private EditText inputmobile, edt_email;
    private ImageView btn_backLogin;
    private Button button_send;
    private String phone = "", email = "";
    private String otp = "";
    private RetrofitService retrfitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        inputmobile = findViewById(R.id.edt_sdt);
        edt_email = findViewById(R.id.edt_email);
        button_send = findViewById(R.id.button_send);
        btn_backLogin = findViewById(R.id.btn_backLogin);
        retrfitService = new RetrofitService();
        checkSmsPermission();

        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = inputmobile.getText().toString();
                email = edt_email.getText().toString();
                if (!phone.isEmpty() && !email.isEmpty()) {
                    Toast.makeText(Reset.this, "Chỉ chọn 1 trong 2 hình thức", Toast.LENGTH_SHORT).show();
                }
                if (phone.isEmpty() && email.isEmpty()) {
                    Toast.makeText(Reset.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }

                if (!phone.isEmpty() && email.isEmpty()) {
                    String passHash = Register.hashPassword("1111");
                    Call<ResponseBody> call = retrfitService.getApiService().reset(phone, passHash);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                otp = "1111";
                                sentOtp(otp);
                                Intent intent = new Intent(Reset.this, Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Reset.this, "Không tồn tại tài khoản" + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(Reset.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                if (phone.isEmpty() && !email.isEmpty()) {
                    Toast.makeText(Reset.this, "Ok", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }

    private void checkSmsPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }
    }

    private void sentOtp(String otp) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(otp + message);
            smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
            Toast.makeText(this, "Gửi SMS thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi gửi SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sentOtp(otp);
            } else {
                Toast.makeText(this, "Quyền gửi SMS bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}