package com.striketec.fanapp.model.login;

/**
 * Created by Sukhbirs on 15-11-2017.
 * This is model interface for Login screen.
 */

public interface LoginModel {

    void login(LoginReqInfo loginReqInfo, OnLoginFinishedListener onLoginFinishedListener);

    interface OnLoginFinishedListener {
        void onResponseSuccess(Object responseObject, String whichApi);

        void onResponseError(String errorMessage);
    }
}
