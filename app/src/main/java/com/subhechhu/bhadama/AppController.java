package com.subhechhu.bhadama;

import android.app.Application;
import android.content.SharedPreferences;

public class AppController extends Application {


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }


    public static synchronized AppController getContext() {
        return mInstance;
    }

    public static SharedPreferences getSharedPreference() {
        return getContext().getSharedPreferences(mInstance.getString(R.string.login_pref), 0);
    }

    public static void storePreferenceString(String key, String stringValue) {
        SharedPreferences.Editor spEditor = getSharedPreference().edit();
        spEditor.putString(key, stringValue);
        spEditor.apply();
    }

    public static void storePreferenceBoolean(String key, boolean boolValue) {
        SharedPreferences.Editor spEditor = getSharedPreference().edit();
        spEditor.putBoolean(key, boolValue);
        spEditor.apply();
    }

    public static String getPreferenceString(String key) {
        return getSharedPreference().getString(key, "");
    }

    public static boolean getPreferenceBoolean(String key) {
        return getSharedPreference().getBoolean(key, false);
    }
}
