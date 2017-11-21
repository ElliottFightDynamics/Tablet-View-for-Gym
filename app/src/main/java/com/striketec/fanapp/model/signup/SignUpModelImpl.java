package com.striketec.fanapp.model.signup;

import com.striketec.fanapp.model.api.OnWebResponseListener;
import com.striketec.fanapp.model.api.WebServiceHandler;
import com.striketec.fanapp.model.signup.dto.SignUpReqInfo;

/**
 * Created by Sukhbirs on 16-11-2017.
 * This is Sign Up Model implementation that interacts with calling of web APIs, local database hit etc.
 */

public class SignUpModelImpl implements SignUpModel, OnWebResponseListener {

    private OnSignUpListener onSignUpListener;

    public SignUpModelImpl(OnSignUpListener onSignUpListener) {
        this.onSignUpListener = onSignUpListener;
    }

    @Override
    public void signUp(SignUpReqInfo signUpReqInfo) {
        // call Sign Up web API

        WebServiceHandler.getInstance().signUp(this, signUpReqInfo);
    }

    @Override
    public void getCompaniesList() {
        WebServiceHandler.getInstance().getCompaniesList(this);
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        onSignUpListener.onResponseSuccess(responseObject, whichApi);
    }

    @Override
    public void onResponseError(String errorMessage) {
        onSignUpListener.onResponseError(errorMessage);
    }
}
