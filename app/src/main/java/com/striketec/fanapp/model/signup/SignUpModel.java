package com.striketec.fanapp.model.signup;

import com.striketec.fanapp.model.signup.dto.SignUpReqInfo;

/**
 * Created by Sukhbirs on 16-11-2017.
 */

public interface SignUpModel {

    void signUp(SignUpReqInfo signUpReqInfo);

    void getCompaniesList();

    interface OnSignUpListener {
        void onResponseSuccess(Object object, String whichApi);

        void onResponseError(String errorMessage);
    }
}
