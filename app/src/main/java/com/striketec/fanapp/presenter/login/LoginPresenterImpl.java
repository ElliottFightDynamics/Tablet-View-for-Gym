package com.striketec.fanapp.presenter.login;

import android.text.TextUtils;
import android.util.Patterns;

import com.striketec.fanapp.model.api.RestUrl;
import com.striketec.fanapp.model.api.response.ResponseObject;
import com.striketec.fanapp.model.login.LoginModel;
import com.striketec.fanapp.model.login.LoginModelImpl;
import com.striketec.fanapp.model.login.LoginReqInfo;
import com.striketec.fanapp.model.signup.dto.NewUserInfo;
import com.striketec.fanapp.utils.SharedPrefUtils;
import com.striketec.fanapp.view.login.LoginActivity;
import com.striketec.fanapp.view.login.LoginActivityInteractor;

/**
 * Created by Sukhbirs on 15-11-2017.
 * This is presenter implementation class for Login screen.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginFinishedListener {

    private LoginActivityInteractor mLoginActivityInteractor;
    private LoginModel mLoginModel;
    private LoginActivity mLoginActivity;

    public LoginPresenterImpl(LoginActivityInteractor mLoginActivityInteractor) {
        this.mLoginActivityInteractor = mLoginActivityInteractor;
        this.mLoginActivity = (LoginActivity) mLoginActivityInteractor;
        mLoginModel = new LoginModelImpl(this);
    }

    @Override
    public void validateCredentials(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            mLoginActivityInteractor.setEmailError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mLoginActivityInteractor.setEmailFormatError();
        } else if (TextUtils.isEmpty(password)) {
            mLoginActivityInteractor.setPasswordError();
        } else {
            if (mLoginActivityInteractor != null) {
                mLoginActivityInteractor.showProgress();
            }
            LoginReqInfo loginReqInfo = new LoginReqInfo();
            loginReqInfo.setEmail(email);
            loginReqInfo.setPassword(password);
            mLoginModel.login(loginReqInfo, this);
        }
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        if (mLoginActivityInteractor != null) {
            mLoginActivityInteractor.hideProgress();
            if (whichApi != null) {
                if (whichApi.equals(RestUrl.LOGIN)) {
                    if (responseObject != null) {
                        ResponseObject<NewUserInfo> loginResponseObject = (ResponseObject<NewUserInfo>) responseObject;
                        if (loginResponseObject != null) {
                            // save required details (like email id, password, authorization token) into SharedPreferences.
                            SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(mLoginActivity);
                            sharedPrefUtils.setLoginDetails(null, null, loginResponseObject.getmToken());
                        }
                    }
                }
            }
            mLoginActivityInteractor.navigateToHome();
        }
    }

    @Override
    public void onResponseError(String errorMessage) {
        if (mLoginActivityInteractor != null) {
            mLoginActivityInteractor.hideProgress();
            mLoginActivityInteractor.setWebApiError(errorMessage);
        }
    }

    @Override
    public void onDestroy() {
        mLoginActivityInteractor = null;
    }
}
