package com.example.sample.ui.borrower;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sample.model.Borrower;

public class CreateViewModel extends ViewModel {

    private final MutableLiveData<Borrower> borrowerData;

    public CreateViewModel() {
        borrowerData = new MutableLiveData<>();
        borrowerData.setValue(new Borrower());
    }

    public void updateBorrowerContent(Borrower borrower) {
        borrowerData.postValue(borrower);
    }

    public LiveData<Borrower> getBorrower() {
        return borrowerData;
    }
}