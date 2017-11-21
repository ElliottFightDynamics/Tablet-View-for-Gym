package com.striketec.fanapp.view.signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.signup.dto.CompanyInfo;
import com.striketec.fanapp.presenter.signup.SignUpPresenter;
import com.striketec.fanapp.presenter.signup.SignUpPresenterImpl;
import com.striketec.fanapp.utils.DialogUtils;

import java.util.List;

/**
 * Register New User or Sign Up Screen.
 */
public class SignUpActivity extends AppCompatActivity implements SignUpActivityInteractor {

    private EditText mCompanySpinnerEdit, mEmailEdit, mPasswordEdit, mRepeatPasswordEdit;
    private SignUpPresenter mSignUpPresenter;
    private List<CompanyInfo> mCompanyInfoList;
    private CompanyInfo mSelectedCompanyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mSignUpPresenter = new SignUpPresenterImpl(this);
        findViewByIds();

//        getCompaniesList();
    }

    /**
     * Method to set the layout references.
     */
    private void findViewByIds() {
        mCompanySpinnerEdit = findViewById(R.id.edit_spinner_company);
        mEmailEdit = findViewById(R.id.edit_email);
        mPasswordEdit = findViewById(R.id.edit_password);
        mRepeatPasswordEdit = findViewById(R.id.edit_repeat_password);
    }

    /**
     * Method to handle click event of Login button.
     *
     * @param view
     */
    public void loginButtonClicked(View view) {
        finish();
    }

    /**
     * Method to handle click event of Register button.
     *
     * @param view
     */
    public void registerButtonClicked(View view) {
        String mEmail = mEmailEdit.getText().toString().trim();
        String mPassword = mPasswordEdit.getText().toString().trim();
        String mRepeatPassword = mRepeatPasswordEdit.getText().toString().trim();
        // need to update the company id as per selected company name.
        mSignUpPresenter.validateSignUpForm(mSelectedCompanyInfo, mEmail, mPassword, mRepeatPassword);
    }

    /**
     * Method to get the companies list data from server.
     */
    private void getCompaniesList() {
        mSignUpPresenter.getCompaniesList();
    }

    /**
     * Method to show the companies list as Spinner with the help of AlertDialog and RecyclerView.
     * If Company list is empty, then it will go to get companies list from server.
     *
     * @param view
     */
    public void showCompanyListSpinner(View view) {
/*
        if (mCompanyInfoList != null && mCompanyInfoList.size() > 0) {
            mSignUpPresenter.showCompanySpinner(mCompanyInfoList);
        } else {
            getCompaniesList();
        }
*/
    }


    @Override
    public void showProgress() {
        DialogUtils.showProgressDialog(this, getString(R.string.loading));
    }

    @Override
    public void hideProgress() {
        DialogUtils.dismissProgressDialog();
    }

    @Override
    public void setCompanyError() {
        DialogUtils.showToast(this, getString(R.string.error_company_is_required));
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
    public void setRepeatPasswordError() {
        DialogUtils.showToast(this, getString(R.string.error_repeat_password_is_required));
        mRepeatPasswordEdit.requestFocus();
    }

    @Override
    public void setRepeatPasswordNotMatchError() {
        DialogUtils.showToast(this, getString(R.string.error_repeat_password_not_match));
        mRepeatPasswordEdit.requestFocus();
    }

    @Override
    public void setWebApiError(String errorMessage) {
        DialogUtils.showToast(this, errorMessage);
    }

    @Override
    public void setmCompanyInfoList(List<CompanyInfo> mCompanyInfoList) {
        if (mCompanyInfoList != null && mCompanyInfoList.size() > 0) {
            this.mCompanyInfoList = mCompanyInfoList;
            // need to remove toast here
            DialogUtils.showToast(this, "Company list size: " + mCompanyInfoList.size());
        } else {
            DialogUtils.showToast(this, getString(R.string.error_company_list_not_found));
        }
    }

    @Override
    public void navigateToLogin(String message) {
        DialogUtils.showToast(this, message);
        finish();
    }

    @Override
    protected void onDestroy() {
        mSignUpPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setSelectedCompany(CompanyInfo companyInfo) {
        mSelectedCompanyInfo = companyInfo;
        if (companyInfo != null) {
            mCompanySpinnerEdit.setText(companyInfo.getCompanyName());
        }
    }
}
