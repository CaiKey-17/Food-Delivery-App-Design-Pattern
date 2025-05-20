package com.example.project_android.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_android.Activity.*;
import com.example.project_android.R;

public class AdminDetailFragment extends Fragment {
    private CardView cv_driver, cv_restaurant, cv_voucher, cv_complaint;
    private static final String ARG_ID = "id";
    private int id;

    private String mParam1;
    private String mParam2;

    public AdminDetailFragment() {
    }

    public static AdminDetailFragment newInstance(int id) {
        AdminDetailFragment fragment = new AdminDetailFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_detail, container, false);
        cv_driver = view.findViewById(R.id.cv_driver);
        cv_restaurant = view.findViewById(R.id.cv_restaurant);
        cv_voucher = view.findViewById(R.id.cv_voucher);
        cv_complaint = view.findViewById(R.id.cv_complaint);

        cv_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AdminDriverActivity.class);
                startActivity(intent);

            }
        });

        cv_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AdminRestaurantActivity.class);
                startActivity(intent);

            }
        });

        cv_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AdminVoucherActivity.class);
                startActivity(intent);

            }
        });

        cv_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AdminComplaintActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }
}