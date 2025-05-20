package com.example.project_android.Fragment;

import android.os.Bundle;

import android.util.Log;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Adapter.VoucherRestaurantValidAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Voucher_restaurant;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CustomerVoucherRestaurant extends Fragment {
    private RecyclerView list_voucherRes_valid;
    private static final String ARG_ID = "id";
    private int id;
    private ProgressBar progressBar;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CustomerVoucherRestaurant() {
    }

    public static CustomerVoucherRestaurant newInstance(int id) {
        CustomerVoucherRestaurant fragment = new CustomerVoucherRestaurant();
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
        View view = inflater.inflate(R.layout.fragment_customer_voucher_restaurant, container, false);
        list_voucherRes_valid = view.findViewById(R.id.list_voucherRes_valid);
        progressBar = view.findViewById(R.id.progressBar);
        list_voucherRes_valid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        loadListMenu1(id);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadListMenu1(id);
    }

    private void loadListMenu1(int id) {
        progressBar.setVisibility(View.VISIBLE);

        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        api.listVoucherRestaurantOfCustomer(id).enqueue(new Callback<List<Voucher_restaurant>>() {
            @Override
            public void onResponse(Call<List<Voucher_restaurant>> call, Response<List<Voucher_restaurant>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Voucher_restaurant> dishes = response.body();

                    populateListView1(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Voucher_restaurant>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView1(List<Voucher_restaurant> dishes) {
        VoucherRestaurantValidAdapter adapter = new VoucherRestaurantValidAdapter(getContext(), dishes, new OnItemClickListener<Voucher_restaurant>() {
            @Override
            public void onItemClick(Voucher_restaurant item, int position) {

            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        }, id, progressBar);
        list_voucherRes_valid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}