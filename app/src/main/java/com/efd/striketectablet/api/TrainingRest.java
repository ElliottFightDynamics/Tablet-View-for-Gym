package com.efd.striketectablet.api;

import com.efd.striketectablet.DTO.responsedto.PlansResponseDTO;
import com.efd.striketectablet.DTO.responsedto.ServerTrainingResponseDTO;
import com.efd.striketectablet.DTO.responsedto.SyncServerResponseDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TrainingRest {

    @FormUrlEncoded
    @POST(RestUrl.SAVE_COMBO_PLAN)
    Call<PlansResponseDTO> saveComboPlan(@Field("userId") String userId,
                                         @Field("secureAccessToken") String secureAccessToken,
                                         @Field("combo") String combo);

    @FormUrlEncoded
    @POST(RestUrl.RETRIEVE_PLANS)
    Call<PlansResponseDTO> retrievePlans(@Field("userId") String userId,
                                         @Field("secureAccessToken") String secureAccessToken);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_SETS_PLAN)
    Call<PlansResponseDTO> saveSetsPlan(@Field("userId") String userId,
                                        @Field("secureAccessToken") String secureAccessToken,
                                        @Field("sets") String sets);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_WORKOUT_PLAN)
    Call<PlansResponseDTO> saveWorkoutPlan(@Field("userId") String userId,
                                           @Field("secureAccessToken") String secureAccessToken,
                                           @Field("workout") String workout);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_PRESET)
    Call<PlansResponseDTO> savePreset(@Field("userId") String userId,
                                      @Field("secureAccessToken") String secureAccessToken,
                                      @Field("preset") String preset);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_SESSION)
    Call<SyncServerResponseDTO> saveTrainingSession(@Field("userId") String userId,
                                                    @Field("secureAccessToken") String secureAccessToken,
                                                    @Field("training_session") String training_session);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_DATA)
    Call<SyncServerResponseDTO> saveTrainingData(@Field("userId") String userId,
                                                 @Field("secureAccessToken") String secureAccessToken,
                                                 @Field("training_data") String training_data);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_DATA_DETAIL)
    Call<SyncServerResponseDTO> saveTrainingDataDetail(@Field("userId") String userId,
                                                       @Field("secureAccessToken") String secureAccessToken,
                                                       @Field("training_data_details") String training_data_details);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_PUNCH_DATA)
    Call<SyncServerResponseDTO> saveTrainingPunchData(@Field("userId") String userId,
                                                      @Field("secureAccessToken") String secureAccessToken,
                                                      @Field("training_punch_data") String training_punch_data);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_PUNCH_PEAK_SUMMARY)
    Call<SyncServerResponseDTO> saveTrainingPunchPeakSummary(@Field("userId") String userId,
                                                             @Field("secureAccessToken") String secureAccessToken,
                                                             @Field("training_punch_data_peak_summary") String training_punch_data_peak_summary);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_PUNCH_STATS)
    Call<SyncServerResponseDTO> saveTrainingPunchStats(@Field("userId") String userId,
                                                       @Field("secureAccessToken") String secureAccessToken,
                                                       @Field("training_punch_stats") String training_punch_stats);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_PUNCH_DETAIL)
    Call<SyncServerResponseDTO> saveTrainingPunchDetails(@Field("userId") String userId,
                                                         @Field("secureAccessToken") String secureAccessToken,
                                                         @Field("training_punch_detail") String training_punch_detail);

    @FormUrlEncoded
    @POST(RestUrl.SAVE_TRAINING_PLAN_RESULTS)
    Call<SyncServerResponseDTO> saveTrainingPlanResults(@Field("userId") String userId,
                                                        @Field("secureAccessToken") String secureAccessToken,
                                                        @Field("training_plan_results") String training_plan_results);

    @FormUrlEncoded
    @POST(RestUrl.RETRIEVE_TRAINING_SESSION)
    Call<ServerTrainingResponseDTO> retrieveTrainingSession(@Field("userId") String userId,
                                                            @Field("secureAccessToken") String secureAccessToken,
                                                            @Field("startDate") String startDate);

    @FormUrlEncoded
    @POST(RestUrl.RETRIEVE_TRAINING_PUNCH_STATS)
    Call<ServerTrainingResponseDTO> retrieveTrainingPunchStats(@Field("userId") String userId,
                                                               @Field("secureAccessToken") String secureAccessToken,
                                                               @Field("startDate") String startDate);

    @FormUrlEncoded
    @POST(RestUrl.RETRIEVE_TRAINING_PLAN_RESULTS)
    Call<ServerTrainingResponseDTO> retrieveTrainingPlanResults(@Field("userId") String userId,
                                                                @Field("secureAccessToken") String secureAccessToken,
                                                                @Field("startDate") String startDate);

    @FormUrlEncoded
    @POST(RestUrl.RETRIEVE_TRAINING_PUNCH_DETAIL)
    Call<ServerTrainingResponseDTO> retrieveTrainingPunchDetails(@Field("userId") String userId,
                                                                 @Field("secureAccessToken") String secureAccessToken,
                                                                 @Field("startDate") String startDate);




}
