package com.striketec.fanapp.model.splash;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public class SplashModelImpl implements SplashModel {
    @Override
    public void loadAppConfiguration(OnSplashFinishedListener onSplashFinishedListener) {
        // call web API here
        onSplashFinishedListener.onResponseSuccess();
    }
}
