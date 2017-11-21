package com.striketec.fanapp.view.splash;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public interface SplashActivityInteractor {
    void showProgress();

    void hideProgress();

    void setWebApiError(String errorMessage);

    void navigateToLogin();

    void navigateToHome();
}
