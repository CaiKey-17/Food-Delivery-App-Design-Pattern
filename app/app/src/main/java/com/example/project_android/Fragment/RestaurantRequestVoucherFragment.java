package com.example.project_android.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Activity.RestaurantAddVoucher;
import com.example.project_android.Adapter.VoucherRestaurantRequestAdapter;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class RestaurantRequestVoucherFragment extends Fragment {
    private static final String ARG_ID = "id";
    private int mId;
    private RecyclerView recyclerView;
    private Button btn_add_voucher;
    private VoucherRestaurantRequestAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RestaurantRequestVoucherFragment() {
    }


    public static RestaurantRequestVoucherFragment newInstance(int id) {
        RestaurantRequestVoucherFragment fragment = new RestaurantRequestVoucherFragment();
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
        View view = inflater.inflate(R.layout.fragment_restaurant_request_voucher, container, false);
        recyclerView = view.findViewById(R.id.list_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_add_voucher = view.findViewById(R.id.btn_add_voucher);

        btn_add_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RestaurantAddVoucher.class);
                intent.putExtra("id", mId);
                startActivityForResult(intent, 1);
            }
        });
        loadListMenu(mId);
        return view;
    }

    private void loadListMenu(int id) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listVoucherRequest(id).enqueue(new Callback<List<Voucher_restaurant>>() {
            @Override
            public void onResponse(Call<List<Voucher_restaurant>> call, Response<List<Voucher_restaurant>> response) {
                Log.d("API Response", "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher_restaurant> dishes = response.body();
                    populateListView(dishes, id);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Voucher_restaurant>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<Voucher_restaurant> dishes, int id) {
        adapter = new VoucherRestaurantRequestAdapter(getContext(), dishes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            loadListMenu(mId);

            adapter.notifyDataSetChanged();
        }
    }


}