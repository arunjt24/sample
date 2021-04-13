package com.example.sample.ui.borrower;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sample.model.Borrower;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    MutableLiveData<ArrayList<Borrower>> stringLiveData;

    public ListViewModel() {
        stringLiveData = new MutableLiveData<>();
        stringLiveData.setValue(new ArrayList<>());
    }

    public void updateData(ArrayList<Borrower> borrowerList) {
        stringLiveData.postValue(borrowerList);
    }

    public MutableLiveData<ArrayList<Borrower>> getStringLiveData() {
        return stringLiveData;
    }

}