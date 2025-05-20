package com.example.project_android.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.example.project_android.Activity.CustomerUpdateAddress;
import com.example.project_android.Activity.Login; // Đảm bảo đường dẫn chính xác

import com.example.project_android.Activity.CustomerAccountSettings;
import com.example.project_android.Activity.CustomerPasswordSetting;
import com.example.project_android.Config.IP;
import com.example.project_android.R;

import static android.content.Context.MODE_PRIVATE;

public class CustomerSettingFragment extends Fragment {

    private static final String PREFS_NAME = "AppPreferences";
    private static final String NIGHT_MODE_KEY = "NightMode";
    private Switch switchDarkmode;


    private LinearLayout information, password, changeAddress;
    private CardView cvEdit;
    private TextView textView72, textView73;
    private static final String ARG_ID = "id";
    private int id;
    private ImageView imageView22;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CustomerSettingFragment() {
    }

    public static CustomerSettingFragment newInstance(int id) {
        CustomerSettingFragment fragment = new CustomerSettingFragment();
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
        View view = inflater.inflate(R.layout.fragment_customer_setting, container, false);

        information = view.findViewById(R.id.layout_information_account);
        cvEdit = view.findViewById(R.id.cv_information_account);
        password = view.findViewById(R.id.layout_password_account);
        textView72 = view.findViewById(R.id.textView72);
        imageView22 = view.findViewById(R.id.imageView22);
        textView73 = view.findViewById(R.id.textView73);
        changeAddress = view.findViewById(R.id.changeAddress);

        switchDarkmode = view.findViewById(R.id.switch_customer_darkmode);

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isNightMode = prefs.getBoolean(NIGHT_MODE_KEY, false);

        switchDarkmode.setChecked(isNightMode);

        switchDarkmode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(NIGHT_MODE_KEY, isChecked);
            editor.apply();

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        loadInfor();
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerAccountSettings.class);
                startActivity(intent);
            }
        });

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerUpdateAddress.class);
                startActivity(intent);
            }
        });

        cvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerAccountSettings.class);
                startActivity(intent);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerPasswordSetting.class);
                startActivity(intent);
            }
        });

        Button btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadInfor();
    }


    private void logout() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(requireActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    public void loadInfor() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        textView72.setText(sharedPreferences.getString("name", ""));
        textView73.setText(sharedPreferences.getString("phone", ""));

        String imageUrl = IP.network + sharedPreferences.getString("image", "");
        Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.avatar)
                .into(imageView22);


    }


}