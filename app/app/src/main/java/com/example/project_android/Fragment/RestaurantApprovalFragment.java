package com.example.project_android.Fragment;

import android.os.Bundle;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_android.Adapter.AdminRestaurantAdapter;
import com.example.project_android.Adapter.VoucherRestaurantAccept;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class RestaurantApprovalFragment extends Fragment {

    private static final String ARG_ID = "id";
    private int id;


    private RecyclerView recyclerView;
    private AdminRestaurantAdapter adapter;

    public RestaurantApprovalFragment() {
    }


    public static RestaurantApprovalFragment newInstance(int id) {
        RestaurantApprovalFragment fragment = new RestaurantApprovalFragment();
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_approval, container, false);

        recyclerView = view.findViewById(R.id.approval_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        loadListMenu();


        return view;
    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listRequestAll().enqueue(new Callback<List<Voucher_restaurant>>() {
            @Override
            public void onResponse(Call<List<Voucher_restaurant>> call, Response<List<Voucher_restaurant>> response) {
                Log.d("API Response", "Response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher_restaurant> dishes = response.body();
                    populateListView(dishes);
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


    private void populateListView(List<Voucher_restaurant> dishes) {
        VoucherRestaurantAccept adapter = new VoucherRestaurantAccept(getContext(), dishes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}