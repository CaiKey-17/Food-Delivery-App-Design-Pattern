package com.example.project_android.Activity;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.project_android.Adapter.AdapterViewPager;
import com.example.project_android.Fragment.DriverInfoFragment;
import com.example.project_android.Fragment.DriverNotiFragment;
import com.example.project_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DriverInfoActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_info);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        backButton = findViewById(R.id.backButton);

        fragmentArrayList.add(new DriverInfoFragment());
        fragmentArrayList.add(new DriverNotiFragment());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);
        viewPager.setAdapter(adapterViewPager);


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_driver_info);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
                        break;
                }
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.bottom_driver_info) {
                    viewPager.setCurrentItem(0);
                    return true;
                }
                if (item.getItemId() == R.id.navigation_notifications) {
                    viewPager.setCurrentItem(1);
                    return true;
                }
                return false;
            }
        });
    }
}