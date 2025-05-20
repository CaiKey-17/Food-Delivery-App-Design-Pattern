package com.example.project_android.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_android.Fragment.RestaurantOrderHistoryFragment;
import com.example.project_android.Fragment.RestaurantOrderPreOrderFragment;
import com.example.project_android.Fragment.RestaurantOrderPresentFragment;

public class AdapterTabLayout extends FragmentStateAdapter {
    private int mId;

    public AdapterTabLayout(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int id) {
        super(fragmentManager, lifecycle);
        this.mId = id;

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return RestaurantOrderPresentFragment.newInstance(mId);
            case 1:
                return RestaurantOrderPreOrderFragment.newInstance(mId);
            case 2:
                return RestaurantOrderHistoryFragment.newInstance(mId);
            default:
                return null;


        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
