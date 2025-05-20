package com.example.project_android.Fragment;

import android.os.Bundle;

import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_android.Adapter.DriverAdapter;
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

public class DriverListFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private DriverAdapter adapter;
    private List<Driver> driverList;

    public DriverListFragment() {
    }

    public static DriverListFragment newInstance(String param1, String param2) {
        DriverListFragment fragment = new DriverListFragment();
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

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDriverList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        searchView = view.findViewById(R.id.searchView);
        loadListMenu();
//        // Thiết lập RecyclerView và Adapter cho danh sách tài xế
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new DriverAdapter(getContext(), driverList);
//        recyclerView.setAdapter(adapter);
//
//        // Lắng nghe sự thay đổi của ô tìm kiếm
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                adapter.filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.filter(newText);
//                return false;
//            }
//        });

        return view;
    }


    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listAllShiper().enqueue(new Callback<List<Shiper>>() {
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
        DriverAdapter adapter = new DriverAdapter(getContext(), dishes, new OnItemClickListener<Shiper>() {
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