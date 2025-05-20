package com.example.project_android.Interface;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActiveStatusViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isActive = new MutableLiveData<>(false);


    public LiveData<Boolean> getActiveStatus() {
        return isActive;
    }
    
    public void setActiveStatus(boolean status) {
        isActive.setValue(status);
    }




}

