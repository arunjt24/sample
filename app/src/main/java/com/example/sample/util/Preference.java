package com.example.sample.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.sample.LoginActivity;

public class Preference {

    private static String keyUserLoggedIn = "userLoggedIn";
    private static String employeeID = "employeeID";
    private static String branchID = "branchID";

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static SharedPreferences preference;
    private static SharedPreferences.Editor editor;

    public Preference(Context context) {
        Preference.context = context;
    }

    public static void logout() {
        editor.clear();
        editor.commit();
        LoginActivity.start(Preference.context);
    }

    @SuppressLint("CommitPrefEdits")
    public void init() {
        String PREFERENCES_NAME = "com.finance";
        preference = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public static void setUserLoggedIn(String employeeId,String branchId) {
        editor.putString(employeeID, employeeId);
        editor.putString(branchID, branchId);
        editor.putBoolean(keyUserLoggedIn, true);
        editor.commit();
    }

    public static String getBranchID() {
        return preference.getString(branchID,"");
    }

    public static String getEmployeeID() {
        return preference.getString(employeeID,"");
    }

    public static Boolean isUserLoggedIn() {
        return preference.getBoolean(keyUserLoggedIn, false);
    }


}
