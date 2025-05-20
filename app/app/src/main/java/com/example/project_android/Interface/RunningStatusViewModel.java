package com.example.project_android.Interface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RunningStatusViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isRunning = new MutableLiveData<>(false);


    public LiveData<Boolean> getRunningStatus() {
        return isRunning;
    }


    public void setRunningStatus(boolean status) {
        isRunning.setValue(status);
    }
}
