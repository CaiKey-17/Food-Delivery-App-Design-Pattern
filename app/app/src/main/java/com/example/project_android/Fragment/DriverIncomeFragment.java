package com.example.project_android.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.project_android.Activity.DriverListHistoryActivity;
import com.example.project_android.R;

public class DriverIncomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private LinearLayout layoutClickRide, layoutClickMoney;

    public DriverIncomeFragment() {
    }

    public static DriverIncomeFragment newInstance(String param1, String param2) {
        DriverIncomeFragment fragment = new DriverIncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_income, container, false);

        layoutClickRide = view.findViewById(R.id.layoutClickRide);
        layoutClickMoney = view.findViewById(R.id.layoutClickMoney);

        layoutClickRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DriverListHistoryActivity.class);
                startActivity(intent);

            }
        });

        layoutClickMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Tiền thu nhập clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
