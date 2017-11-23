package com.striketec.fanapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This class is used to deal with SharedPreferences to store and fetch data.
 */

public class SharedPrefUtils {
    private static final String EMAIL = "login_email";
    private static final String PASSWORD = "login_password";
    private static final String TOKEN = "login_token";
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String PREF_NAME = "StrikeTec";
    private int PRIVATE_MODE = 0;

    public SharedPrefUtils(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * Method to set the details after login.
     *
     * @param email
     * @param password
     * @param token
     */
    public void setLoginDetails(String email, String password, String token) {
        if (email != null) {
            mEditor.putString(EMAIL, email);
        }
        if (password != null) {
            mEditor.putString(PASSWORD, password);

        }
        mEditor.putString(TOKEN, "Bearer " + token);
        mEditor.commit();
    }

    /**
     * Method to get Login Auth Token.
     *
     * @return
     */
    public String getToken() {
        return mSharedPreferences.getString(TOKEN, null);
    }
}
