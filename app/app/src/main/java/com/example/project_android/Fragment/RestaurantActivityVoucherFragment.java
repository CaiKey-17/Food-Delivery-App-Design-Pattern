package com.example.project_android.Fragment;

import android.os.Bundle;

import android.util.Log;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.VoucherRestaurantActiveAdapter;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class RestaurantActivityVoucherFragment extends Fragment {
    private RecyclerView recyclerView;
    private VoucherRestaurantActiveAdapter adapter;
    private static final String ARG_ID = "id";
    private int mId;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RestaurantActivityVoucherFragment() {
    }


    public static RestaurantActivityVoucherFragment newInstance(int id) {
        RestaurantActivityVoucherFragment fragment = new RestaurantActivityVoucherFragment();
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
        View view = inflater.inflate(R.layout.fragment_restaurant_activity_voucher, container, false);
        recyclerView = view.findViewById(R.id.list_active);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadListMenu(mId);
        return view;

    }

    private void loadListMenu(int id) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listVoucher(id).enqueue(new Callback<List<Voucher_restaurant>>() {
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
        adapter = new VoucherRestaurantActiveAdapter(getContext(), dishes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}