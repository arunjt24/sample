package com.example.sample.ui.loan;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sample.model.CollectionsResponse;
import com.example.sample.model.LoanResponse;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    MutableLiveData<ArrayList<LoanResponse.Loan>> stringLiveData;
    ArrayList<LoanResponse.Loan> stringArrayList;

    public ListViewModel() {
        stringLiveData = new MutableLiveData<>();
        stringLiveData.setValue(new ArrayList<>());
    }

    public void updateData(ArrayList<LoanResponse.Loan> list) {
        stringLiveData.postValue(list);
    }

    public MutableLiveData<ArrayList<LoanResponse.Loan>> getStringLiveData() {
        return stringLiveData;
    }

}