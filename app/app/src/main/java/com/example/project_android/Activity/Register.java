package com.example.project_android.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.telephony.SmsManager;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project_android.Model.*;
import com.example.project_android.R;
import com.example.project_android.Retrofit.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Register extends AppCompatActivity {
    private static final int SMS_PERMISSION_CODE = 100;
    private String message = " là mã xác minh của bạn";
    private ImageView imageView2;
    private CheckBox checkBox3;
    private String otp = "";
    private String phone = "";
    private EditText full_name, phone_number, password, password_valid;
    private Button button;
    private RetrofitService retrfitService;
    private LinearLayout btn_seclect_account;
    private TextView back_login, select_account;

    private boolean isPasswordVisible = false;
    private boolean isPasswordValidVisible = false;

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

    private void showAccountSelectionDialog() {
        String[] accounts = {"Admin", "Nhà hàng", "Tài xế", "Khách hàng"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn loại tài khoản");
        builder.setItems(accounts, (dialog, which) -> {
            select_account.setText(accounts[which]);

            select_account.setTextColor(getResources().getColor(R.color.mauchu));
        });

        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        retrfitService = new RetrofitService();
        APIUser api = retrfitService.getApiService();

        full_name = findViewById(R.id.edt_sdt);
        phone_number = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        password_valid = findViewById(R.id.password_valid);
        button = findViewById(R.id.button);
        back_login = findViewById(R.id.back_login);
        select_account = findViewById(R.id.tv_select_account);
        btn_seclect_account = findViewById(R.id.btn_seclect_account);
        imageView2 = findViewById(R.id.imageView2);
        checkBox3 = findViewById(R.id.checkBox3);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (password.getWidth() - password.getCompoundDrawables()[2].getBounds().width())) {
                        isPasswordVisible = !isPasswordVisible;
                        if (isPasswordVisible) {
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_person_24, 0, R.drawable.password_unshow, 0);
                        } else {
                            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_person_24, 0, R.drawable.password_show, 0);
                        }
                        password.setSelection(password.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        password_valid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (password_valid.getWidth() - password_valid.getCompoundDrawables()[2].getBounds().width())) {
                        isPasswordValidVisible = !isPasswordValidVisible;
                        if (isPasswordValidVisible) {
                            password_valid.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            password_valid.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_reset_24, 0, R.drawable.password_unshow, 0);
                        } else {
                            password_valid.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password_valid.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_lock_reset_24, 0, R.drawable.password_show, 0);
                        }
                        password_valid.setSelection(password_valid.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });


        checkSmsPermission();

        btn_seclect_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAccountSelectionDialog();
            }
        });


        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = full_name.getText().toString();
                phone = phone_number.getText().toString();
                String pass = password.getText().toString();
                String pass_v = password_valid.getText().toString();
                String chooseRole = select_account.getText().toString();


                if (name.isEmpty() || phone.isEmpty() || pass.isEmpty() || pass_v.isEmpty() || !checkBox3.isChecked()) {
                    Toast.makeText(Register.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                if (!name.isEmpty() && !phone.isEmpty() && !pass.isEmpty() && !pass_v.isEmpty() && checkBox3.isChecked()) {
                    if (pass.equals(pass_v)) {
                        Random random = new Random();
                        otp = "";
                        for (int i = 0; i < 6; i++) {
                            int digit = random.nextInt(10);
                            otp += String.valueOf(digit);
                        }
                        String hashedPassword = hashPassword(pass);
                        int role = 4;
                        if (chooseRole.equals("Khách hàng")) {
                            role = 4;
                        }
                        if (chooseRole.equals("Tài xế")) {
                            role = 3;
                        }
                        if (chooseRole.equals("Nhà hàng")) {
                            role = 2;
                        }
                        if (chooseRole.equals("Admin")) {
                            role = 1;
                        }


                        Call<User> call = retrfitService.getApiService().addUser(role, name, phone, hashedPassword, otp);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(Register.this, OTP.class);
                                    intent.putExtra("phoneN", phone);
                                    intent.putExtra("otpN", otp);
                                    sentOtp(otp);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Register.this, "Đã tồn tại tài khoản" + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(Register.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(Register.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}