package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("Username")
    String userName;
    @SerializedName("Password")
    String userPassword;
    @SerializedName("intBranchid")
    String Branchid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setBranchid(String branchid) {
        this.Branchid = branchid;
    }

    public String getBranchid() {
        return Branchid;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
