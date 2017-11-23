package com.striketec.fanapp.presenter.signup;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.api.RestUrl;
import com.striketec.fanapp.model.api.response.ResponseArray;
import com.striketec.fanapp.model.api.response.ResponseObject;
import com.striketec.fanapp.model.signup.SignUpModel;
import com.striketec.fanapp.model.signup.SignUpModelImpl;
import com.striketec.fanapp.model.signup.dto.CompanyInfo;
import com.striketec.fanapp.model.signup.dto.NewUserInfo;
import com.striketec.fanapp.model.signup.dto.SignUpReqInfo;
import com.striketec.fanapp.view.signup.SignUpActivity;
import com.striketec.fanapp.view.signup.SignUpActivityInteractor;
import com.striketec.fanapp.view.signup.adapter.CompanyListAdapter;

import java.util.List;

/**
 * Created by Sukhbirs on 16-11-2017.
 * This class is SignUp Presenter implementation that acts as present among model and view for sign up screen.
 */

public class SignUpPresenterImpl implements SignUpPresenter, SignUpModel.OnSignUpListener {

    private SignUpActivityInteractor mSignUpActivityInteractor;
    private SignUpModel mSignUpModel;

    private CompanyInfo mLastSelectedCompanyInfo, mNewSelectedCompanyInfo;
    private SignUpActivity mSignUpActivity;

    public SignUpPresenterImpl(SignUpActivityInteractor mSignUpActivityInteractor) {
        this.mSignUpActivityInteractor = mSignUpActivityInteractor;
        mSignUpActivity = (SignUpActivity) mSignUpActivityInteractor;
        mSignUpModel = new SignUpModelImpl(this);
    }

    @Override
    public void getCompaniesList() {
        if (mSignUpActivityInteractor != null) {
            mSignUpActivityInteractor.showProgress();
        }
        mSignUpModel.getCompaniesList();
    }

    @Override
    public void validateSignUpForm(CompanyInfo companyInfo, String email, String password, String repeatPassword) {
        if (companyInfo == null) {
            mSignUpActivityInteractor.setCompanyError();
        } else if (TextUtils.isEmpty(email)) {
            mSignUpActivityInteractor.setEmailError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mSignUpActivityInteractor.setEmailFormatError();
        } else if (TextUtils.isEmpty(password)) {
            mSignUpActivityInteractor.setPasswordError();
        } else if (TextUtils.isEmpty(repeatPassword)) {
            mSignUpActivityInteractor.setRepeatPasswordError();
        } else if (!password.equals(repeatPassword)) {
            mSignUpActivityInteractor.setRepeatPasswordNotMatchError();
        } else {
            if (mSignUpActivityInteractor != null) {
                mSignUpActivityInteractor.showProgress();
            }
            // creating request data object for sign up.
            SignUpReqInfo mSignUpReqInfo = new SignUpReqInfo();
            mSignUpReqInfo.setCompanyId(String.valueOf(companyInfo.getId()));
            mSignUpReqInfo.setEmail(email);
            mSignUpReqInfo.setPassword(password);
            mSignUpModel.signUp(mSignUpReqInfo);
        }
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        if (mSignUpActivityInteractor != null) {
            mSignUpActivityInteractor.hideProgress();
            // check whether it is response of companies list data web API or sign up web API.
            if (whichApi != null) {
                if (whichApi.equals(RestUrl.GET_COMPANIES_LIST)) {
                    if (responseObject != null) {
                        ResponseArray<CompanyInfo> responseArray = (ResponseArray<CompanyInfo>) responseObject;
                        List<CompanyInfo> companyInfoList = responseArray.getmData();
                        mSignUpActivityInteractor.setmCompanyInfoList(companyInfoList);
                    }
                } else if (whichApi.equals(RestUrl.SIGN_UP)) {
                    /**
                     * need to check sign up successfully or not.
                     * Whether it should navigate to the Login page after success response or show any message in case of any failure.
                     */
                    if (responseObject != null) {
                        ResponseObject<NewUserInfo> response = (ResponseObject<NewUserInfo>) responseObject;
                        if (response != null) {
                            mSignUpActivityInteractor.navigateToLogin(response.getmMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage) {
        if (mSignUpActivityInteractor != null) {
            mSignUpActivityInteractor.hideProgress();
            mSignUpActivityInteractor.setWebApiError(errorMessage);
        }
    }

    @Override
    public void onDestroy() {
        mSignUpActivityInteractor = null;
    }

    @Override
    public void showCompanySpinner(List<CompanyInfo> companyInfoList) {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mSignUpActivity);
        LayoutInflater mInflater = (LayoutInflater) (mSignUpActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mDialogView = mInflater.inflate(R.layout.dialog_company_list, null);
        mAlertDialogBuilder.setView(mDialogView);

        final AlertDialog mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout mOuterLinearLayout = mDialogView.findViewById(R.id.outer_linear_layout);
        RecyclerView mCompanyListRecyclerView = mOuterLinearLayout.findViewById(R.id.recycler_view_company_names);
        RelativeLayout mBottomRelativeLayout = mOuterLinearLayout.findViewById(R.id.bottom_relative_layout);
        Button mCancelButton = mBottomRelativeLayout.findViewById(R.id.button_cancel);
        Button mOkButton = mBottomRelativeLayout.findViewById(R.id.button_ok);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialize the last selected company instance with new selected company info on click of OK button.
                mLastSelectedCompanyInfo = mNewSelectedCompanyInfo;
                (mSignUpActivity).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSignUpActivityInteractor.setSelectedCompany(mNewSelectedCompanyInfo);
                        mAlertDialog.dismiss();
                    }
                });
            }
        });

        // populate the company list data on Alert Dialog using adapter.
        CompanyListAdapter mCompanyListAdapter = new CompanyListAdapter(mSignUpActivity, companyInfoList, mLastSelectedCompanyInfo, new CompanyListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position, CompanyInfo companyInfo) {
                mNewSelectedCompanyInfo = companyInfo;
            }
        });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mSignUpActivity);
        mCompanyListRecyclerView.setLayoutManager(mLinearLayoutManager);
        mCompanyListRecyclerView.setAdapter(mCompanyListAdapter);

        mAlertDialog.show();
    }
}
