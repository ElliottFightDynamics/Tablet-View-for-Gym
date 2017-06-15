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

    @FormUrlEncoded
    @POST(RestUrl.UPDATE_USER)
    Call<AuthenticationDTO> updateUser(@Field("userId") String userId,
                                       @Field("firstName") String firstName,
                                       @Field("lastName") String lastName,
                                       @Field("stance") String stance,
                                       @Field("gender") String gender,
                                       @Field("dateOfBirth") String dateOfBirth,
                                       @Field("weight") String weight,
                                       @Field("reach") String reach,
                                       @Field("skillLevel") String skillLevel,
                                       @Field("height") String height,
                                       @Field("gloveType") String gloveType,
                                       @Field("emailId") String emailId,
                                       @Field("secureAccessToken") String secureAccessToken );

//    @FormUrlEncoded
//    @POST(RestUrl.REGIST)
//    Call<RegisterResponseDTO> register(@Field("firstName") String firstName,
//                                       @Field("lastName") String lastName,
//                                       @Field("username") String username,
//                                       @Field("zipcode") String zipcode,
//                                       @Field("countryId") String countryId,
//                                       @Field("emailId") String emailId,
//                                       @Field("password") String password,
//                                       @Field("quesId") String quesId,
//                                       @Field("answer") String answer,
//                                       @Field("leftDevice") String leftDevice,
//                                       @Field("rightDevice") String rightDevice,
//                                       @Field("leftDeviceSensorName") String leftDeviceSensorName,
//                                       @Field("leftDeviceGeneration") String leftDeviceGeneration,
//                                       @Field("rightDeviceSensorName") String rightDeviceSensorName,
//                                       @Field("rightDeviceGeneration") String rightDeviceGeneration );

}
