package com.example.project_android.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_android.Adapter.AdapterTabLayout;
import com.example.project_android.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class RestaurantOrderFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private static final String ARG_ID = "id";
    private int id;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RestaurantOrderFragment() {
    }


    public static RestaurantOrderFragment newInstance(int id) {
        RestaurantOrderFragment fragment = new RestaurantOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_order, container, false);
        tabLayout = view.findViewById(R.id.tablayout_order);
        viewPager2 = view.findViewById(R.id.paper_order);

        AdapterTabLayout adapterTabLayout = new AdapterTabLayout(getChildFragmentManager(), getLifecycle(), id);
        viewPager2.setAdapter(adapterTabLayout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Hiện tại");
                                break;
                            case 1:
                                tab.setText("Đang đặt");
                                break;
                            case 2:
                                tab.setText("Lịch sử");
                                break;

                            default:
                                return;
                        }
                    }
                });
        tabLayoutMediator.attach();
        return view;
    }

}