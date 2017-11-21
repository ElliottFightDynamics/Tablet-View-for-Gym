package com.striketec.fanapp.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.striketec.fanapp.R;
import com.striketec.fanapp.presenter.splash.SplashPresenter;
import com.striketec.fanapp.presenter.splash.SplashPresenterImpl;
import com.striketec.fanapp.view.login.LoginActivity;

/**
 * Splash Screen.
 */
public class SplashActivity extends AppCompatActivity implements SplashActivityInteractor {

    private SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSplashPresenter = new SplashPresenterImpl(this);
        mSplashPresenter.loadAppConfiguration();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setWebApiError(String errorMessage) {

    }

    @Override
    public void navigateToLogin() {
        final Intent mainIntent = new Intent(this, LoginActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    }

    @Override
    public void navigateToHome() {

    }
}
