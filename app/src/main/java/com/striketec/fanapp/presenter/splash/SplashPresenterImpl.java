package com.striketec.fanapp.presenter.splash;

import android.os.Handler;

import com.striketec.fanapp.model.splash.SplashModel;
import com.striketec.fanapp.model.splash.SplashModelImpl;
import com.striketec.fanapp.view.splash.SplashActivityInteractor;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public class SplashPresenterImpl implements SplashPresenter, SplashModel.OnSplashFinishedListener {

    private SplashActivityInteractor mSplashActivityInteractor;
    private SplashModel mSplashModel;

    public SplashPresenterImpl(SplashActivityInteractor mSplashActivityInteractor) {
        this.mSplashActivityInteractor = mSplashActivityInteractor;
        mSplashModel = new SplashModelImpl();
    }

    @Override
    public void loadAppConfiguration() {
//        mSplashModel.loadAppConfiguration(this);

        // Hold Splash screen for couple of seconds, if no web API called for now.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSplashActivityInteractor != null) {
                    mSplashActivityInteractor.navigateToLogin();
                }
            }
        }, 2000);

        /**
         * here, we can implement logic for which screen will be visible after Splash whether it is login or home page. For now it is going to Login page.
         */
    }

    @Override
    public void onDestroy() {
        mSplashActivityInteractor = null;
    }

    @Override
    public void onResponseSuccess() {
        if (mSplashActivityInteractor != null) {
            mSplashActivityInteractor.hideProgress();
            // check here whether it should navigate to the Login page or Home page.
            mSplashActivityInteractor.navigateToLogin();
        }
    }

    @Override
    public void onResponseError(String errorMessage) {
        if (mSplashActivityInteractor != null) {
            mSplashActivityInteractor.hideProgress();
            // check here whether it should navigate to the Login page or Home page.
            mSplashActivityInteractor.setWebApiError(errorMessage);
        }

    }
}
