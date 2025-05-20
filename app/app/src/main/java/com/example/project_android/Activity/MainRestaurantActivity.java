package com.example.project_android.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.project_android.Adapter.AdapterViewPager;
import com.example.project_android.Fragment.RestaurantMenuFragment;
import com.example.project_android.Fragment.RestaurantOrderFragment;
import com.example.project_android.Fragment.RestaurantSettingFragment;
import com.example.project_android.Fragment.RestaurantVoucherFragment;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainRestaurantActivity extends AppCompatActivity  {

    private ViewPager2 paperMain;
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private Double lati = 0.0;
    private int id = -1;
    private Double longi = 0.0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_restaurant);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermissionAndFetchLocation();
        paperMain = findViewById(R.id.paperMainRestaurent);
        bottomNavigationView = findViewById(R.id.bottomNavigationRestaurent);


        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);

        Intent intent = getIntent();
        String navigateTo = intent.getStringExtra("navigateTo");

        fragmentArrayList.add(RestaurantOrderFragment.newInstance(id));
        fragmentArrayList.add(RestaurantMenuFragment.newInstance(id));
        fragmentArrayList.add(RestaurantVoucherFragment.newInstance(id));
        fragmentArrayList.add(RestaurantSettingFragment.newInstance(id));

        AdapterViewPager adapterViewPagerRestaurant = new AdapterViewPager(this, fragmentArrayList);
        paperMain.setAdapter(adapterViewPagerRestaurant);
        if ("menu".equals(navigateTo)) {
            paperMain.setCurrentItem(1);
            bottomNavigationView.setSelectedItemId(R.id.btn_menu);
        }
        paperMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.btn_order);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.btn_menu);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.btn_mailbox);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.btn_setting);
                        break;
                }
                super.onPageSelected(position);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.btn_order) {
                    paperMain.setCurrentItem(0);
                    return true;
                } else if (item.getItemId() == R.id.btn_menu) {
                    paperMain.setCurrentItem(1);
                    return true;
                } else if (item.getItemId() == R.id.btn_mailbox) {
                    paperMain.setCurrentItem(2);
                    return true;
                } else if (item.getItemId() == R.id.btn_setting) {
                    paperMain.setCurrentItem(3);
                    return true;
                }
                return false;
            }
        });

    }


    private void checkLocationPermissionAndFetchLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {

            fetchCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {

                fetchCurrentLocation();
            } else {

                Toast.makeText(this, "Location permission is required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchCurrentLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(MainRestaurantActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            lati = location.getLatitude();
                            longi = location.getLongitude();
                            RetrofitService retrofitService = new RetrofitService();
                            APIUser api = retrofitService.getApiService();


                            Call<Integer> call = api.updatePosition(id, lati, longi);
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful()) {
                                    } else {
                                        Toast.makeText(MainRestaurantActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(MainRestaurantActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(MainRestaurantActivity.this,
                                    "Unable to get current location",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}