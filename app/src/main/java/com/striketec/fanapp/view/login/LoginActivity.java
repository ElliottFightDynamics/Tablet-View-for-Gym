package com.striketec.fanapp.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.striketec.fanapp.R;
import com.striketec.fanapp.presenter.login.LoginPresenter;
import com.striketec.fanapp.presenter.login.LoginPresenterImpl;
import com.striketec.fanapp.utils.DialogUtils;
import com.striketec.fanapp.view.forgot_password.ForgotPasswordActivity;
import com.striketec.fanapp.view.home.HomeActivity;
import com.striketec.fanapp.view.signup.SignUpActivity;

/**
 * Login Screen.
 */
public class LoginActivity extends AppCompatActivity implements LoginActivityInteractor {

    private EditText mEmailEdit, mPasswordEdit;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenterImpl(this);
        findViewByIds();
    }

    /**
     * Method to find and set layout references.
     */
    private void findViewByIds() {
        mEmailEdit = findViewById(R.id.edit_email);
        mPasswordEdit = findViewById(R.id.edit_password);
    }

    /**
     * Method to handle click event of Forgot Password?
     *
     * @param view
     */
    public void forgotPasswordClicked(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * Method to handle click event of Register button to go to Sign Up screen.
     *
     * @param view
     */
    public void registerButtonClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    /**
     * Method to handle click event of Login button.
     *
     * @param view
     */
    public void loginButtonClicked(View view) {
        String emailStr = mEmailEdit.getText().toString().trim();
        String passwordStr = mPasswordEdit.getText().toString().trim();
        mLoginPresenter.validateCredentials(emailStr, passwordStr);
    }

    @Override
    public void showProgress() {
        DialogUtils.showProgressDialog(this, getString(R.string.please_wait));
    }

    @Override
    public void hideProgress() {
        DialogUtils.dismissProgressDialog();
    }

    @Override
    public void setEmailError() {
        DialogUtils.showToast(this, getString(R.string.error_email_is_required));
        mEmailEdit.requestFocus();
    }

    @Override
    public void setEmailFormatError() {
        DialogUtils.showToast(this, getString(R.string.error_invalid_email_id_format));
        mEmailEdit.requestFocus();
    }

    @Override
    public void setPasswordError() {
        DialogUtils.showToast(this, getString(R.string.error_password_is_required));
        mPasswordEdit.requestFocus();
    }

    @Override
    public void setWebApiError(String errorMessage) {
        DialogUtils.showToast(this, errorMessage);
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onDestroy();
        super.onDestroy();
    }
}
