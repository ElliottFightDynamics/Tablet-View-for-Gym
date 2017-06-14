package com.efd.striketectablet.api;

import com.efd.striketectablet.DTO.responsedto.AuthenticationDTO;
import com.efd.striketectablet.DTO.responsedto.CountryListDTO;
import com.efd.striketectablet.DTO.responsedto.QuestionListDTO;
import com.efd.striketectablet.DTO.responsedto.RegisterResponseDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CredentialRest {

    @FormUrlEncoded
    @POST(RestUrl.LOGIN)
    Call<AuthenticationDTO> login(@Field("username") String username,
                                  @Field("password") String password);

    @POST(RestUrl.QUESTION_LIST)
    Call<QuestionListDTO> questionList();

    @POST(RestUrl.COUNTRY_LIST)
    Call<CountryListDTO> countryList();

    @FormUrlEncoded
    @POST(RestUrl.REGIST)
    Call<RegisterResponseDTO> register(@Field("firstName") String firstName,
                                       @Field("lastName") String lastName,
                                       @Field("username") String username,
                                       @Field("zipcode") String zipcode,
                                       @Field("countryId") String countryId,
                                       @Field("emailId") String emailId,
                                       @Field("password") String password,
                                       @Field("quesId") String quesId,
                                       @Field("answer") String answer,
                                       @Field("leftDevice") String leftDevice,
                                       @Field("rightDevice") String rightDevice,
                                       @Field("leftDeviceSensorName") String leftDeviceSensorName,
                                       @Field("leftDeviceGeneration") String leftDeviceGeneration,
                                       @Field("rightDeviceSensorName") String rightDeviceSensorName,
                                       @Field("rightDeviceGeneration") String rightDeviceGeneration );

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
