package com.app.todolist.tutorial;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.todolist.R;


public class PrefManager {

    // Shared preferences file name
    private static final String PREF_NAME = "privacy_friendly_todo";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    // shared pref mode
    private final int PRIVATE_MODE = 0;

    public PrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setFirstTimeValues(Context context) {
        PreferenceManager.setDefaultValues(context, R.xml.settings, false);
    }

}
