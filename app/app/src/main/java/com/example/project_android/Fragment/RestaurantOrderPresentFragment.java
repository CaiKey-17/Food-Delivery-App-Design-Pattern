package com.example.project_android.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Activity.OrderDetailUI;
import com.example.project_android.Adapter.OrderAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.SellingOrder;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class RestaurantOrderPresentFragment extends Fragment {
    private RecyclerView list_order_res;

    private static final String ARG_ID = "id";
    private int id;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RestaurantOrderPresentFragment() {
    }


    public static RestaurantOrderPresentFragment newInstance(int id) {
        RestaurantOrderPresentFragment fragment = new RestaurantOrderPresentFragment();
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
    public void onResume() {
        super.onResume();
        loadListMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_order_present, container, false);
        list_order_res = view.findViewById(R.id.list_order_res);

        list_order_res.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        loadListMenu();
        return view;
    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.getAllOrderOfRestaurant(id).enqueue(new Callback<List<SellingOrder>>() {
            @Override
            public void onResponse(Call<List<SellingOrder>> call, Response<List<SellingOrder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SellingOrder> dishes = response.body();

                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<SellingOrder>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<SellingOrder> dishes) {
        OrderAdapter adapter = new OrderAdapter(requireContext(), dishes, new OnItemClickListener<SellingOrder>() {
            @Override
            public void onItemClick(SellingOrder item, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailUI.class);
                intent.putExtra("id", id);
                intent.putExtra("order_id", item.getId());
                startActivity(intent);
            }

            @Override
            public void onVoucherSelected(int voucherId) {

            }

            @Override
            public void onPriceSelected(double price) {

            }
        });
        list_order_res.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}