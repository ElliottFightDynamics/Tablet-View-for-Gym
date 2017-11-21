package com.striketec.fanapp.model.api;

import com.striketec.fanapp.model.api.response.ResponseArray;
import com.striketec.fanapp.model.api.response.ResponseObject;
import com.striketec.fanapp.model.signup.dto.CompanyInfo;
import com.striketec.fanapp.model.signup.dto.NewUserInfo;
import com.striketec.fanapp.model.signup.dto.SignUpReqInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Sukhbirs on 16-11-2017.
 * It contains web Api methods.
 */

public interface RestInterface {

    @GET(RestUrl.GET_COMPANIES_LIST)
    Call<ResponseArray<CompanyInfo>> getCompaniesList();

    @POST(RestUrl.SIGN_UP)
    Call<ResponseObject<NewUserInfo>> signUp(@Body SignUpReqInfo signUpReqInfo);
}
