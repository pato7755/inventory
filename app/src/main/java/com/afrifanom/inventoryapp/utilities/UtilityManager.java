package com.afrifanom.inventoryapp.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.afrifanom.inventoryapp.application.AfrifaApplication;

public class UtilityManager {

    public static final String MANAGER = "MANAGER";
    public static final String CASHIER = "CASHIER";
    public static final String myPreferences = "AFRIFA_PREFERENCES";



    public String getSharedPreference(Context context, String preferenceName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, "");

    }

    public void setPreferences(String preferenceName, String preferenceValue) {

        SharedPreferences sharedPreferences = AfrifaApplication.getContext().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();

    }



}
