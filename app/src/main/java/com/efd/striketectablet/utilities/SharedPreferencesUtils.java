package com.efd.striketectablet.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vrublevskyi on 5/26/2015.
 */
public class SharedPreferencesUtils {

    public static final String PREFS = "prefs";

    public static final String BASE_URL = "base.url";


    public static void saveBaseUrl(String username, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BASE_URL, username).commit();
    }

    public static String getBaseUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(BASE_URL, null);
    }
}