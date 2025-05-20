package com.example.project_android.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.*;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.project_android.Activity.CustomerAccountSettings;
import com.example.project_android.Activity.CustomerPasswordSetting;
import com.example.project_android.Activity.Login;
import com.example.project_android.Config.IP;
import com.example.project_android.R;

public class AdminSetiingFragment extends Fragment {
    private static final String PREFS_NAME = "AppPreferences";
    private static final String NIGHT_MODE_KEY = "NightMode";
    private Switch switcher;
    private static final String ARG_ID = "id";
    private int id;
    private ImageView admin_img_setting;
    private TextView admin_setting_tv_name, admin_setting_changepass, admin_setting_infor;

    public AdminSetiingFragment() {

    }

    public static AdminSetiingFragment newInstance(int id) {
        AdminSetiingFragment fragment = new AdminSetiingFragment();
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

        View view = inflater.inflate(R.layout.fragment_admin_setiing, container, false);
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        boolean isNightMode = prefs.getBoolean(NIGHT_MODE_KEY, false);
        switcher = view.findViewById(R.id.switch_admin_darkmode);
        admin_img_setting = view.findViewById(R.id.admin_img_setting);
        admin_setting_tv_name = view.findViewById(R.id.admin_setting_tv_name);
        admin_setting_changepass = view.findViewById(R.id.admin_setting_changepass);
        admin_setting_infor = view.findViewById(R.id.admin_setting_infor);
        String name = sharedPreferences.getString("name", "");
        String image = sharedPreferences.getString("image", "");

        admin_setting_tv_name.setText(name);

        String imageUrl = IP.network + image;
        Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(R.drawable.avatar)
                .into(admin_img_setting);


        admin_setting_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerAccountSettings.class);
                startActivity(intent);

            }
        });
        admin_setting_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerPasswordSetting.class);
                startActivity(intent);

            }
        });


        switcher.setChecked(isNightMode);

        switcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
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


        Button btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        return view;
    }

    private void logout() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(requireActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }


}