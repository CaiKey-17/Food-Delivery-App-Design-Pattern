package com.example.project_android.Fragment;

import android.os.Bundle;

import android.util.Log;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_android.Adapter.AdminRestaurantAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Restaurant;
import com.example.project_android.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


public class RestaurantListFragment extends Fragment {
    private static final String ARG_ID = "id";
    private int id;


    private String mParam1;
    private String mParam2;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private AdminRestaurantAdapter adapter;

    public RestaurantListFragment() {
    }


    public static RestaurantListFragment newInstance(int id) {
        RestaurantListFragment fragment = new RestaurantListFragment();
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
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);


        recyclerView = view.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadListMenu();
        return view;
    }

    private void loadListMenu() {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listAllRestaurantAdmin().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Restaurant> dishes = response.body();
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response was unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<Restaurant> dishes) {
        AdminRestaurantAdapter adapter = new AdminRestaurantAdapter(getContext(), dishes, new OnItemClickListener<Restaurant>() {
            @Override
            public void onItemClick(Restaurant item, int position) {
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