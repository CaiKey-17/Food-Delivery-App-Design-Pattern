package com.example.project_android.Interface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActiveStatusViewModel1 extends ViewModel {
    private final MutableLiveData<Integer> activeStatus = new MutableLiveData<>(1);


    public LiveData<Integer> getActiveStatus() {
        return activeStatus;
    }


    public void setActiveStatus(int status) {
        activeStatus.setValue(status);
    }
}
