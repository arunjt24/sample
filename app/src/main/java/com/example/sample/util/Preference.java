package com.example.sample.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    public static String keyUserLoggedIn = "userLoggedIn";
    private final Context context;
    private final String PREFERENCES_NAME = "com.finance.";
    private final SharedPreferences preference;
    private final SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Preference(Context context) {
        this.context = context;
        preference = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = preference.edit();
    }

    public void setUserLoggedIn(Boolean userLoggedIn) {
        editor.putBoolean(keyUserLoggedIn, userLoggedIn);
        editor.commit();
    }

    public Boolean isUserLoggedIn() {
        return preference.getBoolean(keyUserLoggedIn, false);
    }
}
