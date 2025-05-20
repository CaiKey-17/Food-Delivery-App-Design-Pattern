package com.example.project_android.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class OTP extends AppCompatActivity {
    private String phone = "", otp_check = "", number1 = "", number2 = "", number3 = "", number4 = "", number5 = "", number6 = "", otp = "", newOtp = "";
    private EditText input1, input2, input3, input4, input5, input6;
    private Button button_send;
    private RetrofitService retrfitService;
    private TextView resend;
    private String message = " là mã xác minh của bạn";


    private static final int SMS_PERMISSION_CODE = 100;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);
        button_send = findViewById(R.id.button_send);
        resend = findViewById(R.id.resend);

        retrfitService = new RetrofitService();

        EditText[] editTexts = {input1, input2, input3, input4, input5, input6};

        for (int i = 0; i < editTexts.length; i++) {
            final int index = i;

            editTexts[index].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        if (index < editTexts.length - 1) {
                            editTexts[index + 1].requestFocus();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            editTexts[index].setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (editTexts[index].getText().toString().isEmpty() && index > 0) {
                        editTexts[index - 1].requestFocus();
                    }
                }
                return false;
            });
        }


        Intent intent = getIntent();
        phone = intent.getStringExtra("phoneN");
        otp = intent.getStringExtra("otpN");
        checkSmsPermission();
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                newOtp = "";
                for (int i = 0; i < 6; i++) {
                    int digit = random.nextInt(10);
                    newOtp += String.valueOf(digit);
                }
                otp = newOtp;
                Call<ResponseBody> call = retrfitService.getApiService().resendOTP(phone, newOtp);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String message = response.body().string();
                                Log.d("OTPActivity", "Response: " + message);
                                sentOtp(newOtp);
                            } catch (IOException e) {
                                Log.e("OTPActivity", "Error reading response: " + e.getMessage());
                            }
                        } else {
                            Toast.makeText(OTP.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("OTPActivity", "Request failed: " + t.getMessage(), t);
                        Toast.makeText(OTP.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number1 = input1.getText().toString();
                ;
                number2 = input2.getText().toString();
                ;
                number3 = input3.getText().toString();
                number4 = input4.getText().toString();
                number5 = input5.getText().toString();
                number6 = input6.getText().toString();


                otp_check = number1 + number2 + number3 + number4 + number5 + number6;

                if (otp.equals(otp_check)) {
                    Call<ResponseBody> call = retrfitService.getApiService().verifyOTP(phone, otp_check);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String message = response.body().string();
                                    Log.d("OTPActivity", "Response: " + message);
                                    Toast.makeText(OTP.this, "Xác minh thành công!", Toast.LENGTH_SHORT).show();
                                    Intent intent1 = new Intent(OTP.this, Login.class);
                                    startActivity(intent1);
                                    finish();

                                } catch (IOException e) {
                                    Log.e("OTPActivity", "Error reading response: " + e.getMessage());
                                }
                            } else {
                                Toast.makeText(OTP.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("OTPActivity", "Request failed: " + t.getMessage(), t);
                            Toast.makeText(OTP.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(OTP.this, "OTP incorrect", Toast.LENGTH_SHORT).show();
                    input1.setText("");
                    input2.setText("");
                    input3.setText("");
                    input4.setText("");
                    input5.setText("");
                    input6.setText("");

                }

            }
        });

    }
}