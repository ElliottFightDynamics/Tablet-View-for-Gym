package com.efd.striketectablet.api;

import com.efd.striketectablet.DTO.AuthenticationDTO;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CredentialRest {

    @FormUrlEncoded
    @POST(RestUrl.LOGIN)
    Call<AuthenticationDTO> login(@Field("username") String username,
                                  @Field("password") String password);

//    @POST(AppConst.Url.DIGITS_LOGIN)
//    Call<AuthenticationDto> digitsLogin(@Body Map<String, String> params);
//
//    @FormUrlEncoded
//    @POST(AppConst.Url.SIGNUP)
//    Call<AuthenticationDto> signUp(@Field("email") String email,
//                                   @Field("phone") String phone,
//                                   @Field("username") String username,
//                                   @Field("password") String password,
//                                   @Field("fullName") String fullName,
//                                   @Field("token") String token,
//                                   @Field("appName") String appName,
//                                   @Field("avatarUrl") String avatarUrl,
//                                   @Field("deviceType") String deviceType,
//                                   @Field("needsVerify") boolean needsVerify);
//
//    @FormUrlEncoded
//    @POST(AppConst.Url.RESET_PASSWORD)
//    Call<ResetPwdStatus> resetPassword(@Field("user") String user,
//                                       @Field("appName") String appName);
}
