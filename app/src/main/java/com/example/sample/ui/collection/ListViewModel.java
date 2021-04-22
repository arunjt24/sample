package com.example.sample.ui.collection;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {

    MutableLiveData<ArrayList<User>> stringLiveData;
    ArrayList<User> stringArrayList;

    public ListViewModel() {
        stringLiveData = new MutableLiveData<>();
        initData();
        stringLiveData.setValue(stringArrayList);
    }

    private void initData() {
        int amount = 1500;
        stringArrayList = new ArrayList<>();
        amount = amount + 1;
        stringArrayList.add(new User("A Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("B Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("C Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("D Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("E Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("F Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("G Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("H Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("I Arun", amount));
        amount = amount + 1;
        stringArrayList.add(new User("J Arun", amount));
    }

    public MutableLiveData<ArrayList<User>> getStringLiveData() {
        return stringLiveData;
    }

    public static class User {
        String name;

        Integer pendingAmount;

        public User(String name, Integer pendingAmount) {
            this.name = name;
            this.pendingAmount = pendingAmount;
        }

    }
}