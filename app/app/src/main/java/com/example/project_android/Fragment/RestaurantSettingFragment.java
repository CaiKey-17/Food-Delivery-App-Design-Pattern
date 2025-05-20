package com.example.project_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.project_android.Activity.CustomerPasswordSetting;
import com.example.project_android.Activity.Login;
import com.example.project_android.Activity.RestaurantInforDetail;
import com.example.project_android.Config.IP;
import com.example.project_android.Model.User;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantSettingFragment extends Fragment {

    private static final String PREFS_NAME = "AppPreferences";
    private static final String NIGHT_MODE_KEY = "NightMode";
    private Switch switcher;
    private TextView btn_infor_detail, textView30;
    private Button btn_logout;
    private static final String ARG_ID = "id";
    private int mId;

    private ImageView imageView7;
    private TextView tv_name_res, tv_address;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RestaurantSettingFragment() {
    }


    public static RestaurantSettingFragment newInstance(int id) {
        RestaurantSettingFragment fragment = new RestaurantSettingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getInt(ARG_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_setting, container, false);
        btn_infor_detail = view.findViewById(R.id.btn_infor_detail);
        btn_logout = view.findViewById(R.id.btn_logout);
        imageView7 = view.findViewById(R.id.imageView7);
        tv_name_res = view.findViewById(R.id.tv_name_res);
        tv_address = view.findViewById(R.id.tv_address);
        textView30 = view.findViewById(R.id.textView30);

        switcher = view.findViewById(R.id.switch_restaurant_darkmode);

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isNightMode = prefs.getBoolean(NIGHT_MODE_KEY, false);

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

        btn_infor_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RestaurantInforDetail.class);
                startActivity(intent);
            }
        });


        textView30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CustomerPasswordSetting.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(view12 -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(getActivity(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            getActivity().finish();
        });
        loadInfor();
        return view;
    }


    public void loadInfor() {

        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        Call<User> call = api.getInfor(mId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    tv_name_res.setText(user.getFullName());
                    tv_address.setText(user.getAddress());
                    String imageUrl = IP.network + user.getImage();
                    Glide.with(requireActivity())
                            .load(imageUrl)
                            .placeholder(R.drawable.avatar)
                            .into(imageView7);

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

}