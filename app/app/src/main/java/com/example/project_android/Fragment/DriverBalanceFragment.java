package com.example.project_android.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project_android.R;

public class DriverBalanceFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DriverBalanceFragment() {
    }

    public static DriverBalanceFragment newInstance(String param1, String param2) {
        DriverBalanceFragment fragment = new DriverBalanceFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_balance, container, false);

        LinearLayout layoutAccountBalance = view.findViewById(R.id.layout_account_balance);
        LinearLayout layoutCashBalance = view.findViewById(R.id.layout_cash_balance);

        layoutAccountBalance.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Clicked on Account Balance", Toast.LENGTH_SHORT).show();
        });

        layoutCashBalance.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Clicked on Cash Balance", Toast.LENGTH_SHORT).show();
        });

        return view;

    }
}