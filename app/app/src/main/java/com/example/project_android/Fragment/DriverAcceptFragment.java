package com.example.project_android.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project_android.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class DriverAcceptFragment extends Fragment {

    private BottomSheetBehavior<View> bottomSheetBehavior;

    public DriverAcceptFragment() {
    }

    public static DriverAcceptFragment newInstance(String param1, String param2) {
        DriverAcceptFragment fragment = new DriverAcceptFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_accept, container, false);

        View bottomSheet = view.findViewById(R.id.frame_shipper_run);
        Button btnViewCustomer = view.findViewById(R.id.btnViewCustomer);
        Button btnAcceptOrder = view.findViewById(R.id.btnAcceptOrder);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setPeekHeight(400);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        btnAcceptOrder.setVisibility(View.GONE);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    btnAcceptOrder.setVisibility(View.VISIBLE);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    btnAcceptOrder.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        return view;
    }
}
