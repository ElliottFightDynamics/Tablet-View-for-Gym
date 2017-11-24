package com.striketec.fanapp.model.api;

import com.striketec.fanapp.model.api.response.ResponseArray;
import com.striketec.fanapp.model.api.response.ResponseObject;
import com.striketec.fanapp.model.events.dto.CreateEventInfo;
import com.striketec.fanapp.model.events.dto.CreateEventResInfo;
import com.striketec.fanapp.model.events.dto.EventLocationInfo;
import com.striketec.fanapp.model.login.LoginReqInfo;
import com.striketec.fanapp.model.signup.dto.CompanyInfo;
import com.striketec.fanapp.model.signup.dto.NewUserInfo;
import com.striketec.fanapp.model.signup.dto.SignUpReqInfo;
import com.striketec.fanapp.model.users.dto.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @POST(RestUrl.LOGIN)
    Call<ResponseObject<NewUserInfo>> login(@Body LoginReqInfo loginReqInfo);

    @GET(RestUrl.GET_EVENT_LOCATIONS)
    Call<ResponseArray<EventLocationInfo>> getEventLocationsList(@Header(RestUrl.AUTHORIZATION) String token);

    @GET(RestUrl.GET_USERS_LIST)
    Call<ResponseArray<UserInfo>> getUsersList(@Header(RestUrl.AUTHORIZATION) String token);
    //    Call<ResponseArray<UserInfo>> getUsersList(@Header(RestUrl.AUTHORIZATION) String token, @Query(RestUrl.START) int start, @Query(RestUrl.LIMIT) int limit);

    @POST(RestUrl.CREATE_EVENT)
    Call<ResponseObject<CreateEventResInfo>> createEvent(@Header(RestUrl.AUTHORIZATION) String token, @Body CreateEventInfo createEventInfo);
}
