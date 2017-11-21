package com.striketec.fanapp.view.login;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public interface LoginActivityInteractor {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setEmailFormatError();

    void setPasswordError();

    void setWebApiError(String errorMessage);

    void navigateToHome();
}
