package com.striketec.fanapp.model.login;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public class LoginModelImpl implements LoginModel {
    @Override
    public void login(String email, String password, OnLoginFinishedListener onLoginFinishedListener) {
        // call Login web API
        onLoginFinishedListener.onResponseSuccess();
    }
}
