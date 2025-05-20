package com.example.project_android.Fragment;

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
import com.example.project_android.Activity.CustomerPasswordSetting;
import com.example.project_android.Activity.Login;
import com.example.project_android.Config.IP;
import com.example.project_android.Model.User;
import com.example.project_android.R;

import static android.content.Context.MODE_PRIVATE;

import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverInfoFragment extends Fragment {
    private static final String PREFS_NAME = "AppPreferences";
    private static final String NIGHT_MODE_KEY = "NightMode";
    private Switch switchDarkmode;
    private TextView userName;
    private LinearLayout changePasswordLayout;
    private int userId = -1;
    private ImageView imageViewAvatar;
    private EditText sotien;
    private Button nap;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DriverInfoFragment() {
        // Required empty public constructor
    }


    public static DriverInfoFragment newInstance(String param1, String param2) {
        DriverInfoFragment fragment = new DriverInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_driver_info, container, false);


        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);


        userName = view.findViewById(R.id.userName);
        changePasswordLayout = view.findViewById(R.id.changePasswordLayout);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        sotien = view.findViewById(R.id.sotien);
        nap = view.findViewById(R.id.nap);

        nap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sotien.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                } else {
                    double tien = Double.valueOf(sotien.getText().toString().trim());

                    RetrofitService retrofitService = new RetrofitService();
                    APIUser api = retrofitService.getApiService();
                    Call<ResponseBody> call = api.naptien(userId, tien);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(requireContext(), "Đã nạp thành công", Toast.LENGTH_SHORT).show();
                                sotien.getText().clear();
                            } else {
                                System.out.println("Error: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            System.out.println("Failure: " + t.getMessage());
                        }
                    });
                }
            }
        });


        changePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerPasswordSetting.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            }
        });

        switchDarkmode = view.findViewById(R.id.switch_driver_darkmode);

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isNightMode = prefs.getBoolean(NIGHT_MODE_KEY, false);
        Button button = view.findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

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


        return view;
    }

    public void loadInfor() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        Call<User> call = api.getInfor(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    userName.setText(user.getFullName());
                    String imageUrl = IP.network + user.getImage();
                    if (isAdded() && getContext() != null) {
                        Glide.with(requireContext())
                                .load(imageUrl)
                                .placeholder(R.drawable.avatar)
                                .into(imageViewAvatar);
                    }


                } else {
                    System.out.println("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Failure: " + t.getMessage());
            }
        });
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
}