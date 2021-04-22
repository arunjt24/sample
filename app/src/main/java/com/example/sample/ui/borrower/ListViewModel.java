package com.example.sample.ui.borrower;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sample.model.BorrowerResponse;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    MutableLiveData<ArrayList<BorrowerResponse.Borrower>> stringLiveData;

    public ListViewModel() {
        stringLiveData = new MutableLiveData<>();
        stringLiveData.setValue(new ArrayList<>());
    }

    public void updateData(ArrayList<BorrowerResponse.Borrower> borrowerList) {
        stringLiveData.postValue(borrowerList);
    }

    public MutableLiveData<ArrayList<BorrowerResponse.Borrower>> getStringLiveData() {
        return stringLiveData;
    }

}