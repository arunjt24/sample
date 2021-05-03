package com.example.sample.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sample.model.CollectionsResponse;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    MutableLiveData<ArrayList<CollectionsResponse.Collection>> stringLiveData;
    ArrayList<CollectionsResponse.Collection> stringArrayList;

    public DashboardViewModel() {
        stringLiveData = new MutableLiveData<>();
        stringLiveData.setValue(new ArrayList<>());
    }

    public void updateData(ArrayList<CollectionsResponse.Collection> list) {
        stringLiveData.postValue(list);
    }

    public MutableLiveData<ArrayList<CollectionsResponse.Collection>> getStringLiveData() {
        return stringLiveData;
    }
}