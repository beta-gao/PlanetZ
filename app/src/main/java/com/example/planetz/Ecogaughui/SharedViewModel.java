package com.example.planetz.Ecogaughui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<String> timePeriod = new MutableLiveData<>();
    private final MutableLiveData<String> specificTime = new MutableLiveData<>();

    public void setTimePeriod(String timePeriod) {
        this.timePeriod.setValue(timePeriod);
    }

    public LiveData<String> getTimePeriod() {
        return timePeriod;
    }

    public void setSpecificTime(String specificTime) {
        this.specificTime.setValue(specificTime);
    }

    public LiveData<String> getSpecificTime() {
        return specificTime;
    }
}
