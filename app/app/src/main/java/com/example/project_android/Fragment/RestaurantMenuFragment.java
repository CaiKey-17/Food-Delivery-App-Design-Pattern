package com.example.project_android.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_android.Activity.RestaurantMenuAdd;
import com.example.project_android.Activity.RestaurantMenuUpdate;
import com.example.project_android.Adapter.DishAdapter;
import com.example.project_android.Interface.OnItemClickListener;
import com.example.project_android.Model.Dish;
import com.example.project_android.R;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class RestaurantMenuFragment extends Fragment {
    private static final int ADD_DISH_REQUEST = 1;
    private RecyclerView recyclerView;

    private static final String ARG_ID = "id";
    private int id;
    private Button buttonAdd;
    private EditText editTextText;
    private LinearLayout detail_dish;
    private DishAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;





    public RestaurantMenuFragment() {
    }


    public static RestaurantMenuFragment newInstance(int id) {
        RestaurantMenuFragment fragment = new RestaurantMenuFragment();
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

    private void filterDishes(String query) {
        if (adapter != null) {
            adapter.filter(query);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);
        buttonAdd = view.findViewById(R.id.buttonAdd);
        detail_dish = view.findViewById(R.id.detail_dish);
        editTextText = view.findViewById(R.id.editTextText);

        recyclerView = view.findViewById(R.id.recyclerView4);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RestaurantMenuAdd.class);
                startActivity(intent);
            }
        });

        loadListMenu(id);

        // Thêm bộ lọc tìm kiếm
        editTextText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterDishes(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }


    private void loadListMenu(int id) {
        RetrofitService retrofitService = new RetrofitService();
        APIUser api = retrofitService.getApiService();

        api.listRestaurantDish(id).enqueue(new Callback<List<Dish>>() {
            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dish> dishes = response.body();
                    populateListView(dishes);
                } else {
                    Log.e("API Error", "Response unsuccessful or body is null.");
                }
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable throwable) {
                Log.e("API Error", "Failed to load dishes", throwable);
            }
        });
    }


    private void populateListView(List<Dish> dishes) {
        adapter = new DishAdapter(getActivity(), dishes, new OnItemClickListener<Dish>() {
            @Override
            public void onItemClick(Dish item, int position) {
                Intent intent = new Intent(getActivity(), RestaurantMenuUpdate.class);
                int id_item = item.getId();
                intent.putExtra("id_item", id_item);
                startActivityForResult(intent, 1);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_DISH_REQUEST) {
            Log.d("Result", "Request Code: " + requestCode + ", Result Code: " + resultCode);
            if (resultCode == RESULT_OK) {
                loadListMenu(id);
                adapter.notifyDataSetChanged();
                if (data != null && data.hasExtra("id")) {
                    int returnedId = data.getIntExtra("id", -1);
                    Log.d("Result", "Returned ID: " + returnedId);
                }
                loadData();
            } else {
                Log.d("Result", "Result Code is not OK.");
            }
        }
    }


    private void loadData() {
        loadListMenu(id);
    }
}