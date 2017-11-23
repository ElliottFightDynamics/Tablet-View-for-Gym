package com.striketec.fanapp.model.login;

import com.striketec.fanapp.model.api.OnWebResponseListener;
import com.striketec.fanapp.model.api.WebServiceHandler;

/**
 * Created by Sukhbirs on 15-11-2017.
 * This is model implementation class for Login screen.
 */

public class LoginModelImpl implements LoginModel, OnWebResponseListener {
    private OnLoginFinishedListener mOnLoginFinishedListener;

    public LoginModelImpl(OnLoginFinishedListener mOnLoginFinishedListener) {
        this.mOnLoginFinishedListener = mOnLoginFinishedListener;
    }

    @Override
    public void login(LoginReqInfo loginReqInfo, OnLoginFinishedListener onLoginFinishedListener) {
        // call Login web API
        WebServiceHandler.getInstance().login(this, loginReqInfo);
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        mOnLoginFinishedListener.onResponseSuccess(responseObject, whichApi);
    }

    @Override
    public void onResponseError(String errorMessage) {
        mOnLoginFinishedListener.onResponseError(errorMessage);
    }
}
