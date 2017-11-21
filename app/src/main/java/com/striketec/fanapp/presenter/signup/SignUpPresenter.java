package com.striketec.fanapp.presenter.signup;

import com.striketec.fanapp.model.signup.dto.CompanyInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 15-11-2017.
 */

public interface SignUpPresenter {

    /**
     * Method to get the companies list data from server.
     */
    void getCompaniesList();

    /**
     * Method to validate the Sign Up form.
     *
     * @param companyInfo
     * @param email
     * @param password
     * @param repeatPassword
     */
    void validateSignUpForm(CompanyInfo companyInfo, String email, String password, String repeatPassword);

    /**
     * To destroy any items onDestroy of activity.
     */
    void onDestroy();

    /**
     * When user click on Company Spinner, it will check whether company list exists or not.
     * If not exists, it will hit /companies web API.
     * If exists, it will show the company spinner dialog to select the company.
     *
     * @param companyInfoList
     */
    void showCompanySpinner(List<CompanyInfo> companyInfoList);
}
