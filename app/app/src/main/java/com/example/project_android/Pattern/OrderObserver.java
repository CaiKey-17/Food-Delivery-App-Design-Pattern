package com.example.project_android.Pattern;

import com.example.project_android.Model.SellingOrder;

import java.util.List;

public interface OrderObserver {
    void onOrdersUpdated(List<SellingOrder> orders);
    void onError(Throwable t);
}
