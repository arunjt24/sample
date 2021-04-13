package com.example.sample.model;

import com.google.gson.annotations.SerializedName;

public class Login {
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
    @SerializedName("Username")
    String userName;
    @SerializedName("Password")
    String userPassword;

    @Override
    public String toString() {
        return "Login{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
