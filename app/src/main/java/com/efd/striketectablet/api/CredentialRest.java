package com.efd.striketectablet.api;

import com.efd.striketectablet.DTO.responsedto.AuthenticationDTO;
import com.efd.striketectablet.DTO.responsedto.CheckRegisteredUserDTO;
import com.efd.striketectablet.DTO.responsedto.CountryListDTO;
import com.efd.striketectablet.DTO.responsedto.QuestionListDTO;
import com.efd.striketectablet.DTO.responsedto.RegisterResponseDTO;
import com.efd.striketectablet.DTO.responsedto.ResetpwdDTO;

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
                                       @Field("rightDeviceGeneration") String rightDeviceGeneration);

    @FormUrlEncoded
    @POST(RestUrl.RESETPWD_EMAIL)
    Call<ResetpwdDTO> resetPwdwithEmail(@Field("emailId") String emailId);

    @FormUrlEncoded
    @POST(RestUrl.RESETPWD_QUESTION)
    Call<ResetpwdDTO> resetPwdwithQuestion(@Field("emailId") String emailId,
                                           @Field("questionId") String quesId,
                                           @Field("questionAnswer") String questionAsnwer);

    @FormUrlEncoded
    @POST(RestUrl.CHANGEPWD)
    Call<ResetpwdDTO> updatePassword(@Field("emailId") String emailId,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST(RestUrl.CHECK_UNREGISTERED_USER)
    Call<CheckRegisteredUserDTO> checkUnregisteredUser(@Field("emailId") String emailId);

}
