package com.example.project_android.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_android.R;
import com.example.project_android.Fragment.RestaurantListFragment;
import com.example.project_android.Fragment.RestaurantApprovalFragment;

public class AdminRestaurantActivity extends AppCompatActivity {

    private TextView restaurantListTab, restaurantApprovalTab;
    private int id = -1;
    private ImageView imageView21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_restaurant);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        id = sharedPreferences.getInt("userId", -1);


        restaurantListTab = findViewById(R.id.restaurant_list_tab);
        restaurantApprovalTab = findViewById(R.id.restaurant_approval_tab);
        imageView21 = findViewById(R.id.imageView21);

        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        loadFragment(new RestaurantListFragment());
        setTabSelected(restaurantListTab, restaurantApprovalTab);


        restaurantListTab.setOnClickListener(v -> {
            loadFragment(RestaurantListFragment.newInstance(id));
            setTabSelected(restaurantListTab, restaurantApprovalTab);
        });

        restaurantApprovalTab.setOnClickListener(v -> {
            loadFragment(RestaurantApprovalFragment.newInstance(id));
            setTabSelected(restaurantApprovalTab, restaurantListTab);
        });
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.restaurant_fragment_container, fragment);
        transaction.commit();
    }


    private void setTabSelected(TextView selectedTab, TextView unselectedTab) {
        selectedTab.setTextColor(getResources().getColor(R.color.selected_tab_color));
        selectedTab.setBackgroundResource(R.drawable.selected_tab_background);

        unselectedTab.setTextColor(getResources().getColor(R.color.unselected_tab_color));
        unselectedTab.setBackgroundResource(android.R.color.transparent);
    }
}
