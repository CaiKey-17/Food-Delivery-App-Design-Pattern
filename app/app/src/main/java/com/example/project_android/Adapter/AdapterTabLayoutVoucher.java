package com.example.project_android.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_android.Fragment.RestaurantActivityVoucherFragment;
import com.example.project_android.Fragment.RestaurantRequestVoucherFragment;
public class AdapterTabLayoutVoucher extends FragmentStateAdapter {
    private int mId;

    public AdapterTabLayoutVoucher(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int id) {
        super(fragmentManager, lifecycle);
        this.mId = id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return RestaurantRequestVoucherFragment.newInstance(mId);
            case 1:
                return RestaurantActivityVoucherFragment.newInstance(mId);
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
