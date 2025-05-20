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
import com.example.project_android.Adapter.VoucherSystemValidAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.VoucherSystem;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CustomerVoucherSystem extends Fragment {
    private static final String ARG_ID = "id";
    private int id;
    private RecyclerView list_voucherSys_valid;
    private ProgressBar progressBar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CustomerVoucherSystem() {
    }

    public static CustomerVoucherSystem newInstance(int id) {
        CustomerVoucherSystem fragment = new CustomerVoucherSystem();
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
        View view = inflater.inflate(R.layout.fragment_customer_voucher_system, container, false);
        list_voucherSys_valid = view.findViewById(R.id.list_voucherSys_valid);

        progressBar = view.findViewById(R.id.progressBar);

        list_voucherSys_valid.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        loadListMenu(id);
        return view;


    }

    @Override
    public void onResume() {
        super.onResume();
        loadListMenu(id);
    }

    private void loadListMenu(int id) {
        progressBar.setVisibility(View.VISIBLE);

        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();
        api.listVoucherSystemOfCustomer(id).enqueue(new Callback<List<VoucherSystem>>() {
            @Override
            public void onResponse(Call<List<VoucherSystem>> call, Response<List<VoucherSystem>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<VoucherSystem> dishes = response.body();

                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<VoucherSystem>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);

                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<VoucherSystem> dishes) {
        VoucherSystemValidAdapter adapter = new VoucherSystemValidAdapter(getContext(), dishes, new OnItemClickListener<VoucherSystem>() {
            @Override
            public void onItemClick(VoucherSystem item, int position) {
            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        }, id, progressBar);
        list_voucherSys_valid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}