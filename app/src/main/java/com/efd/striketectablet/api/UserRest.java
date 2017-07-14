package com.efd.striketectablet.api;

import com.efd.striketectablet.DTO.responsedto.AuthenticationDTO;
import com.efd.striketectablet.DTO.responsedto.ResetpwdDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserRest {

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
                                       @Field("secureAccessToken") String secureAccessToken);


    @FormUrlEncoded
    @POST(RestUrl.UPDATE_WEIGHTGLOVE)
    Call<ResetpwdDTO> updateWeightGlove(@FieldMap Map<String, String> fields);

}
