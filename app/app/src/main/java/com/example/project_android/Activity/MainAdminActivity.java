package com.example.project_android.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.project_android.Adapter.AdapterViewPager;
import com.example.project_android.Fragment.AdminDetailFragment;
import com.example.project_android.Fragment.AdminSetiingFragment;
import com.example.project_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainAdminActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);


        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        fragmentArrayList.add(AdminDetailFragment.newInstance(id));
        fragmentArrayList.add(AdminSetiingFragment.newInstance(id));

        AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);
        viewPager.setAdapter(adapterViewPager);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.itDriver);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.itSetting);
                        break;

                }
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.itDriver) {
                    viewPager.setCurrentItem(0);
                    return true;
                }
                if (item.getItemId() == R.id.itSetting) {
                    viewPager.setCurrentItem(1);
                    return true;

                }

                return false;
            }
        });

    }


}