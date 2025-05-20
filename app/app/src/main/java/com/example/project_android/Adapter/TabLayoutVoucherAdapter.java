package com.example.project_android.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project_android.Fragment.CustomerVoucherRestaurant;
import com.example.project_android.Fragment.CustomerVoucherSystem;

public class TabLayoutVoucherAdapter extends FragmentStateAdapter {
    private int mId; // Thêm biến để lưu ID

    public TabLayoutVoucherAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,int id) {
        super(fragmentManager, lifecycle);
        this.mId = id;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return CustomerVoucherSystem.newInstance(mId);
            case 1:
                return CustomerVoucherRestaurant.newInstance(mId);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
