package com.striketec.fanapp.presenter.login;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public interface LoginPresenter {
    void validateCredentials(String email, String password);

    void onDestroy();
}
