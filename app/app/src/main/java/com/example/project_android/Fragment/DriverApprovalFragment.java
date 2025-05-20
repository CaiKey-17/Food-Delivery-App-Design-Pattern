package com.example.project_android.Fragment;

import android.os.Bundle;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_android.Adapter.DriverApprovalAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Driver;
import com.example.project_android.Model.Shiper;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class DriverApprovalFragment extends Fragment {

    private RecyclerView recyclerView;
    private DriverApprovalAdapter adapter;
    private List<Driver> driverList;

    public DriverApprovalFragment() {
    }

    public static DriverApprovalFragment newInstance(String param1, String param2) {
        DriverApprovalFragment fragment = new DriverApprovalFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
            // Xử lý tham số nếu cần
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        loadListMenu();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_approval, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDriverApproval);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadListMenu();


        return view;
    }


    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listAllShiperRequest().enqueue(new Callback<List<Shiper>>() {
            @Override
            public void onResponse(Call<List<Shiper>> call, Response<List<Shiper>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Shiper> dishes = response.body();
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Shiper>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<Shiper> dishes) {
        DriverApprovalAdapter adapter = new DriverApprovalAdapter(getContext(), dishes, new OnItemClickListener<Shiper>() {
            @Override
            public void onItemClick(Shiper item, int position) {
//                Intent intent = new Intent(RestaurantNearMe.this, FoodListInRestaurant.class);
//                intent.putExtra("id", id);
//                intent.putExtra("id_item", item.getId());
//                intent.putExtra("name", item.getName());
//                intent.putExtra("image", item.getImage());
//                startActivity(intent);
            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}