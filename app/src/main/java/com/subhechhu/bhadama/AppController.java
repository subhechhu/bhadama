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



    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static SharedPreferences getSharedPreference(){
        return getInstance().getSharedPreferences(mInstance.getString(R.string.login_pref), 0);
    }

    public static void storePreferenceBoolean(String key, boolean boolValue){
        SharedPreferences.Editor spEditor = getSharedPreference().edit();
        spEditor.putBoolean(key, boolValue);
        spEditor.apply();
    }
    public static boolean getBoolean(String key){
        return getSharedPreference().getBoolean(key,false);
    }
}
