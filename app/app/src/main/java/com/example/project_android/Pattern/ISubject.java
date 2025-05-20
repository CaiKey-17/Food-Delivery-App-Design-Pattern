package com.example.project_android.Pattern;

public interface ISubject {
    void registerObserver(OrderObserver observer);
    void unregisterObserver(OrderObserver observer);
    void fetchOrders(int id_customer);
}
