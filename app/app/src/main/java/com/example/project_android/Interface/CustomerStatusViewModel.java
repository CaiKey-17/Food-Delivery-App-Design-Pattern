package com.example.project_android.Interface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerStatusViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isCustomer = new MutableLiveData<>(false);


    public LiveData<Boolean> getCustomerStatus() {
        return isCustomer;
    }


    public void setCustomerStatus(boolean status) {
        isCustomer.setValue(status);
    }
}
