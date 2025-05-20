package com.example.project_android.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.project_android.Adapter.AdapterViewPager;
import com.example.project_android.Fragment.DriverBalanceFragment;
import com.example.project_android.Fragment.DriverBonusFragment;
import com.example.project_android.Fragment.DriverIncomeFragment;
import com.example.project_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainDriverIncomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private Button backButton, helpButton, settingButton;
    private TextView textState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_driver_income);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        backButton = findViewById(R.id.backButton);
        textState = findViewById(R.id.textState);
        helpButton = findViewById(R.id.helpButton);
        settingButton = findViewById(R.id.settingsButton);

        fragmentArrayList.add(new DriverIncomeFragment());
        fragmentArrayList.add(new DriverBonusFragment());
        fragmentArrayList.add(new DriverBalanceFragment());


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
                        textState.setText("Thu nhập");
                        bottomNavigationView.setSelectedItemId(R.id.itIncome);
                        break;
                    case 1:
                        textState.setText("Phần thưởng");
                        bottomNavigationView.setSelectedItemId(R.id.itBonus);
                        break;
                    case 2:
                        textState.setText("Số dư");
                        bottomNavigationView.setSelectedItemId(R.id.itBalance);
                        break;
                }
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (item.getItemId() == R.id.itIncome) {
                    textState.setText("Thu nhập");
                    viewPager.setCurrentItem(0);
                    return true;
                }
                if (item.getItemId() == R.id.itBonus) {
                    textState.setText("Phần thưởng");
                    viewPager.setCurrentItem(1);
                    return true;
                }
                if (item.getItemId() == R.id.itBalance) {
                    textState.setText("Số dư");
                    viewPager.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });
    }

}