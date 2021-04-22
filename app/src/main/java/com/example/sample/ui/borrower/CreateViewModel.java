package com.example.sample.ui.borrower;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sample.model.BorrowerResponse;

public class CreateViewModel extends ViewModel {

    private final MutableLiveData<BorrowerResponse.Borrower> borrowerData;

    public CreateViewModel() {
        borrowerData = new MutableLiveData<>();
        borrowerData.setValue(new BorrowerResponse.Borrower());
    }

    public void updateBorrowerContent(BorrowerResponse.Borrower borrower) {
        borrowerData.postValue(borrower);
    }

    public LiveData<BorrowerResponse.Borrower> getBorrower() {
        return borrowerData;
    }
}