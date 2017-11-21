package com.striketec.fanapp.view.signup;

import com.striketec.fanapp.model.signup.dto.CompanyInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 16-11-2017.
 */

public interface SignUpActivityInteractor {
    void showProgress();

    void hideProgress();

    void setCompanyError();

    void setEmailError();

    void setEmailFormatError();

    void setPasswordError();

    void setRepeatPasswordError();

    void setRepeatPasswordNotMatchError();

    void setWebApiError(String errorMessage);

    void setmCompanyInfoList(List<CompanyInfo> mCompanyInfoList);

    void navigateToLogin(String message);

    void setSelectedCompany(CompanyInfo companyInfo);
}
