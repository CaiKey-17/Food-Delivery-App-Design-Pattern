package com.example.project_android.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_android.R;
import com.example.project_android.Fragment.DriverListFragment;
import com.example.project_android.Fragment.DriverApprovalFragment;

public class AdminDriverActivity extends AppCompatActivity {

    private TextView driverListTab;
    private TextView driverApprovalTab;
    private ImageView imageView21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_driver);

        driverListTab = findViewById(R.id.driver_list_tab);
        driverApprovalTab = findViewById(R.id.driver_approval_tab);
        imageView21 = findViewById(R.id.imageView21);

        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loadFragment(new DriverListFragment());
        setTabSelected(driverListTab, driverApprovalTab);

        driverListTab.setOnClickListener(v -> {
            loadFragment(new DriverListFragment());
            setTabSelected(driverListTab, driverApprovalTab);
        });

        driverApprovalTab.setOnClickListener(v -> {
            loadFragment(new DriverApprovalFragment());
            setTabSelected(driverApprovalTab, driverListTab);
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void setTabSelected(TextView selectedTab, TextView unselectedTab) {
        selectedTab.setTextColor(getResources().getColor(R.color.selected_tab_color));
        selectedTab.setBackgroundResource(R.drawable.selected_tab_background);

        unselectedTab.setTextColor(getResources().getColor(R.color.unselected_tab_color));
        unselectedTab.setBackgroundResource(android.R.color.transparent);
    }


}
