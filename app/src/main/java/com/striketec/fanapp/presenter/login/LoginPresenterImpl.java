package com.striketec.fanapp.presenter.login;

import android.text.TextUtils;
import android.util.Patterns;

import com.striketec.fanapp.model.login.LoginModel;
import com.striketec.fanapp.model.login.LoginModelImpl;
import com.striketec.fanapp.view.login.LoginActivityInteractor;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginFinishedListener {

    private LoginActivityInteractor mLoginActivityInteractor;
    private LoginModel mLoginModel;

    public LoginPresenterImpl(LoginActivityInteractor mLoginActivityInteractor) {
        this.mLoginActivityInteractor = mLoginActivityInteractor;
        mLoginModel = new LoginModelImpl();
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
            mLoginModel.login(email, password, this);
        }
    }

    @Override
    public void onResponseSuccess() {
        if (mLoginActivityInteractor != null) {
            mLoginActivityInteractor.hideProgress();
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
