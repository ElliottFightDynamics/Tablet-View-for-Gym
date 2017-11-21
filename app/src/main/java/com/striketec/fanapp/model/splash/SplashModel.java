package com.striketec.fanapp.model.splash;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public interface SplashModel {

    void loadAppConfiguration(OnSplashFinishedListener onSplashFinishedListener);

    interface OnSplashFinishedListener {
        void onResponseSuccess();

        void onResponseError(String errorMessage);
    }
}
