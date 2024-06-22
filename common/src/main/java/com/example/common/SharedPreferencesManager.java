package com.example.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String DB_FILE = "DB_FILE";
    private SharedPreferences sharedPref;
    private static volatile SharedPreferencesManager instance = null;

    private SharedPreferencesManager(Context context) {
        this.sharedPref = context.getApplicationContext().getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
    }

    public static SharedPreferencesManager getInstance() {
        return instance;
    }
    public void putInt( String key, int value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt( String key, int defaultValue){
        return sharedPref.getInt(key, defaultValue);
    }

}
