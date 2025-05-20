package com.example.project_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_android.R;

public class DriverDetailActivity extends AppCompatActivity {

    private TextView tvDriverName, tvPhone, tvIdCard, tvLicensePlate;
    private ImageView ivProfileImage;
    private int driver_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail);


        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvDriverName = findViewById(R.id.tvDriverName);
        tvPhone = findViewById(R.id.tvPhone);
        tvIdCard = findViewById(R.id.tvIdCard);
        tvLicensePlate = findViewById(R.id.tvLicensePlate);


        Intent intent = getIntent();
        driver_id = intent.getIntExtra("driver_id", -1);

    }
}