package com.example.project_android.Pattern;

import com.example.project_android.Model.SellingOrder;
import com.example.project_android.Retrofit.APIUser;
import com.example.project_android.Retrofit.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class OrderManager implements ISubject{

    private static OrderManager instance;
    private List<OrderObserver> observers = new ArrayList<>();

    private APIUser apiService;

    private OrderManager() {
        RetrofitService retrofitService = new RetrofitService();
        apiService = retrofitService.getApiService();
        }

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }
    @Override
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }
    @Override
    public void unregisterObserver(OrderObserver observer) {
        observers.remove(observer);
    }
    @Override
    public void fetchOrders(int id_customer) {
        Call<List<SellingOrder>> call = apiService.listOrder(id_customer);
        call.enqueue(new Callback<List<SellingOrder>>() {
            @Override
            public void onResponse(Call<List<SellingOrder>> call, Response<List<SellingOrder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (OrderObserver observer : observers) {
                        observer.onOrdersUpdated(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SellingOrder>> call, Throwable t) {
                for (OrderObserver observer : observers) {
                    observer.onError(t);
                }
            }
        });
    }
}
