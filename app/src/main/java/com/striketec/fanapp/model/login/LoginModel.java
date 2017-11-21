package com.striketec.fanapp.model.login;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public interface LoginModel {

    void login(String email, String password, OnLoginFinishedListener onLoginFinishedListener);

    interface OnLoginFinishedListener {
        void onResponseSuccess();

        void onResponseError(String errorMessage);
    }
}
