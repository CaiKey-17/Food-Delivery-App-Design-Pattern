package com.example.project_android.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

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

import com.example.project_android.Model.User;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class RestaurantAddVoucher extends AppCompatActivity {
    private EditText expirationDate, edt_voucher_name, edt_voucher_quantity, edt_voucher_price;
    private Button btn_request;
    private ImageView back_voucher;
    private RetrofitService retrofitService;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_voucher);
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
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy giá trị từ các EditText
                String name = edt_voucher_name.getText().toString().trim();
                String quantityString = edt_voucher_quantity.getText().toString().trim();
                String priceString = edt_voucher_price.getText().toString().trim();
                String expiryString = expirationDate.getText().toString().trim();


                if (name.isEmpty()) {
                    Toast.makeText(RestaurantAddVoucher.this, "Vui lòng nhập tên voucher", Toast.LENGTH_SHORT).show();
                    return;
                }


                int quantity = 0;
                try {
                    quantity = Integer.valueOf(quantityString);
                    if (quantity <= 0) {
                        Toast.makeText(RestaurantAddVoucher.this, "Số lượng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(RestaurantAddVoucher.this, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }


                Double price = 0.0;
                try {
                    price = Double.valueOf(priceString);
                    if (price <= 0) {
                        Toast.makeText(RestaurantAddVoucher.this, "Giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(RestaurantAddVoucher.this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (expiryString.isEmpty()) {
                    Toast.makeText(RestaurantAddVoucher.this, "Vui lòng nhập ngày hết hạn", Toast.LENGTH_SHORT).show();
                    return;
                }


                LocalDateTime expiry = LocalDateTime.parse(expiryString, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                LocalDateTime currentDate = LocalDateTime.now();


                if (expiry.isBefore(currentDate) || expiry.isEqual(currentDate)) {
                    Toast.makeText(RestaurantAddVoucher.this, "Ngày hết hạn phải lớn hơn ngày hiện tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(RestaurantAddVoucher.this, "Đã gửi yêu cầu !", Toast.LENGTH_SHORT).show();

                Call<Voucher_restaurant> call = retrofitService.getApiService().addRes(name, quantity, price, expiry, id);
                call.enqueue(new Callback<Voucher_restaurant>() {
                    @Override
                    public void onResponse(Call<Voucher_restaurant> call, Response<Voucher_restaurant> response) {
                        if (response.isSuccessful()) {
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(RestaurantAddVoucher.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Voucher_restaurant> call, Throwable t) {
                        Toast.makeText(RestaurantAddVoucher.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(RestaurantAddVoucher.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(RestaurantAddVoucher.this,
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