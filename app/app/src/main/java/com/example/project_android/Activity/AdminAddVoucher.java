package com.example.project_android.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_android.Model.VoucherSystem;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class AdminAddVoucher extends AppCompatActivity {
    private EditText expirationDate, edt_voucher_name, edt_voucher_quantity, edt_voucher_price;
    private Button btn_request;
    private ImageView back_voucher;
    private RetrofitService retrofitService;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_voucher);
        expirationDate = findViewById(R.id.expiration_date_edittext);
        edt_voucher_name = findViewById(R.id.edt_voucher_name);
        edt_voucher_quantity = findViewById(R.id.edt_voucher_quantity);
        edt_voucher_price = findViewById(R.id.edt_voucher_price);
        btn_request = findViewById(R.id.btn_request);
        back_voucher = findViewById(R.id.back_voucher);

        back_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        retrofitService = new RetrofitService();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_voucher_name.getText().toString().trim();
                String quantityText = edt_voucher_quantity.getText().toString().trim();
                String priceText = edt_voucher_price.getText().toString().trim();
                String expiryText = expirationDate.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(AdminAddVoucher.this, "Vui lòng nhập tên voucher", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (quantityText.isEmpty()) {
                    Toast.makeText(AdminAddVoucher.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (priceText.isEmpty()) {
                    Toast.makeText(AdminAddVoucher.this, "Vui lòng nhập giá", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (expiryText.isEmpty()) {
                    Toast.makeText(AdminAddVoucher.this, "Vui lòng nhập ngày hết hạn", Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity;
                double price;
                LocalDateTime expiry;

                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        Toast.makeText(AdminAddVoucher.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AdminAddVoucher.this, "Số lượng phải là số nguyên", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    price = Double.parseDouble(priceText);
                    if (price <= 0) {
                        Toast.makeText(AdminAddVoucher.this, "Giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AdminAddVoucher.this, "Giá phải là số thực", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    expiry = LocalDateTime.parse(expiryText, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                } catch (Exception e) {
                    Toast.makeText(AdminAddVoucher.this, "Định dạng ngày không đúng (dd/MM/yyyy HH:mm)", Toast.LENGTH_SHORT).show();
                    return;
                }

                Call<VoucherSystem> call = retrofitService.getApiService().addSys(name, quantity, price, expiry, id);
                call.enqueue(new Callback<VoucherSystem>() {
                    @Override
                    public void onResponse(Call<VoucherSystem> call, Response<VoucherSystem> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AdminAddVoucher.this, "Đã thêm voucher!", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(AdminAddVoucher.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<VoucherSystem> call, Throwable t) {
                        Toast.makeText(AdminAddVoucher.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        expirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });
    }

    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AdminAddVoucher.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AdminAddVoucher.this,
                            (timeView, selectedHour, selectedMinute) -> {
                                String selectedDateTime = String.format("%02d/%02d/%04d %02d:%02d",
                                        selectedDay, selectedMonth + 1, selectedYear, selectedHour, selectedMinute);
                                expirationDate.setText(selectedDateTime);
                            }, hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);

        datePickerDialog.show();
    }
}