package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BorrowerResponse {
    @SerializedName("success")
    private Integer success;

    @SerializedName("borrowerslist")
    private ArrayList<Borrower> borrowersList;

    public ArrayList<Borrower> getBorrowersList() {
        return borrowersList;
    }

}
