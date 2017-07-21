package com.efd.striketectablet.util;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.efd.striketectablet.DTO.responsedto.ServerTrainingResponseDTO;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.utilities.CommonUtils;

import retrofit2.Call;

/**
 * Created by Super on 7/21/2017.
 */

public class SyncTrainingDataService extends IntentService {

    DBAdapter dbAdapter;

    boolean sessionFinished = false;
    boolean punchstatsFinished = false;
    boolean planresultsFinished = false;

    String userId = "";
    String accessToken = "";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SyncTrainingDataService(String name) {
        super(name);
    }

    public SyncTrainingDataService() {
        super("SyncTrainingDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        dbAdapter = DBAdapter.getInstance(this);
        userId = CommonUtils.getServerUserId(this);
        accessToken = CommonUtils.getAccessTokenValue(this);



        startSessionRetrieve();
        startPunchStatsRetrieve();
        startPlanResultsRetrieve();
    }

    private void startSessionRetrieve(){
        String startDate = dbAdapter.getLastSyncSessionServerTime(userId);
        Log.e("Super", "user id = " + userId + "   " + accessToken + "    " + startDate);
        retreiveUnsyncedTrainingSession(startDate);
    }

    private void retreiveUnsyncedTrainingSession(final String startDate){
        RetrofitSingleton.TRAINING_REST.retrieveTrainingSession(userId, accessToken, startDate).enqueue(new IndicatorCallback<ServerTrainingResponseDTO>(getApplicationContext(), false) {
            @Override
            public void onResponse(Call<ServerTrainingResponseDTO> call, retrofit2.Response<ServerTrainingResponseDTO> response) {
                super.onResponse(call, response);
                if (response.body() != null) {
                    ServerTrainingResponseDTO responseDTO = response.body();
                    if (responseDTO.getSuccess() && responseDTO.getAccess()){

                        if (responseDTO.getTrainingSession() != null && responseDTO.getTrainingSession().size() > 0){
                            //add training sessions to db
                            dbAdapter.insertTrainingSessionFromServer(responseDTO.getTrainingSession());
                            Log.e("Super", "training session response = " + responseDTO.getTrainingSession().size());
                        }else {
                            Log.e("Super", "session is 0");
                        }

                        if (responseDTO.getLast()){
                            sessionFinished = true;

                            if (isallfinished()){
                                stopSelf();
                            }
                        }else {
                            retreiveUnsyncedTrainingSession(startDate);
                        }

                    }else {
                        Log.e("Super", "retrieve success = faile" );
                    }
                } else {
                    Log.e("Super", "training response is null");
                    sessionFinished = true;
                }
            }

            @Override
            public void onFailure(Call<ServerTrainingResponseDTO> call, Throwable t) {
                super.onFailure(call, t);
                Log.e("Super", "training session response fail " + t);
            }
        });
    }

    private void startPunchStatsRetrieve(){
        String startDate = dbAdapter.getLastSyncPunchStatsServerTime(userId);
        retreiveUnsyncedTrainingPunchStats(startDate);
    }

    private void retreiveUnsyncedTrainingPunchStats(final String startDate){

        RetrofitSingleton.TRAINING_REST.retrieveTrainingPunchStats(userId, accessToken, startDate).enqueue(new IndicatorCallback<ServerTrainingResponseDTO>(getApplicationContext(), false) {
            @Override
            public void onResponse(Call<ServerTrainingResponseDTO> call, retrofit2.Response<ServerTrainingResponseDTO> response) {
                super.onResponse(call, response);
                if (response.body() != null) {
                    ServerTrainingResponseDTO responseDTO = response.body();
                    if (responseDTO.getSuccess() && responseDTO.getAccess()){

                        if (responseDTO.getTrainingPunchStats() != null && responseDTO.getTrainingPunchStats().size() > 0){
                            //add training punch stats to db
                            dbAdapter.insertTrainingPunchStatsFromServer(responseDTO.getTrainingPunchStats());
                            Log.e("Super", "training punch stats response = " + responseDTO.getTrainingPunchStats().size());
                        }else {
                            Log.e("Super", "punch stats is 0");
                        }

                        if (responseDTO.getLast()){
                            punchstatsFinished = true;

                            if (isallfinished()){
                                stopSelf();
                            }
                        }else {
                            retreiveUnsyncedTrainingPunchStats(startDate);
                        }

                    }else {
                        Log.e("Super", "retrieve punch stats success = faile" );
                    }
                } else {
                    Log.e("Super", "training punch stats is null");
                    punchstatsFinished = true;
                }
            }

            @Override
            public void onFailure(Call<ServerTrainingResponseDTO> call, Throwable t) {
                super.onFailure(call, t);
                Log.e("Super", "training punch stats response fail " + t);
            }
        });
    }

    private void startPlanResultsRetrieve(){
        String startDate = dbAdapter.getLastSyncPlanResultsServerTime(userId);
        retreiveUnsyncedTrainingPlanResults(startDate);
    }


    private void retreiveUnsyncedTrainingPlanResults(final String startDate){
        RetrofitSingleton.TRAINING_REST.retrieveTrainingPlanResults(userId, accessToken, startDate).enqueue(new IndicatorCallback<ServerTrainingResponseDTO>(getApplicationContext(), false) {
            @Override
            public void onResponse(Call<ServerTrainingResponseDTO> call, retrofit2.Response<ServerTrainingResponseDTO> response) {
                super.onResponse(call, response);
                if (response.body() != null) {
                    ServerTrainingResponseDTO responseDTO = response.body();
                    if (responseDTO.getSuccess() && responseDTO.getAccess()){

                        if (responseDTO.getTrainingPlanResults() != null && responseDTO.getTrainingPlanResults().size() > 0){
                            //add training plan results to db
                            Log.e("Super", "training plan results response = " + responseDTO.getTrainingPlanResults().size());
                            dbAdapter.insertTrainingPlansResultsFromServer(responseDTO.getTrainingPlanResults());
                        }else {
                            Log.e("Super", "plan results is 0");
                        }

                        if (responseDTO.getLast()){
                            planresultsFinished = true;

                            if (isallfinished()){
                                stopSelf();
                            }
                        }else {
                            retreiveUnsyncedTrainingPlanResults(startDate);
                        }

                    }else {
                        Log.e("Super", "retrieve plan results = faile" );
                    }
                } else {
                    Log.e("Super", "training plan results is null");
                    planresultsFinished = true;
                }
            }

            @Override
            public void onFailure(Call<ServerTrainingResponseDTO> call, Throwable t) {
                super.onFailure(call, t);
                Log.e("Super", "training plan results response fail " + t);
            }
        });
    }

    private boolean isallfinished(){
        return sessionFinished && punchstatsFinished && planresultsFinished;
    }


/*
    private void retrieveTrainingSession(final String userId, final String secureAccessToken, final String startDate){

        Log.e("Super", "start sync service");
        RetrofitSingleton.TRAINING_REST.retrieveTrainingDatas(userId, secureAccessToken, startDate).enqueue(new IndicatorCallback<ServerTrainingResponseDTO_OLD>(getApplicationContext(), false) {
            @Override
            public void onResponse(Call<ServerTrainingResponseDTO_OLD> call, retrofit2.Response<ServerTrainingResponseDTO_OLD> response) {
                super.onResponse(call, response);
                if (response.body() != null) {
                    ServerTrainingResponseDTO_OLD responseDTO = response.body();
                    if (responseDTO.getSuccess() && responseDTO.getAccess()){
                        ServerTrainingSessionDTO serverTrainingSessionDTOs = responseDTO.getTrainingSession();
                        if (serverTrainingSessionDTOs != null && serverTrainingSessionDTOs.getTraineeSession() != null && serverTrainingSessionDTOs.getTraineeSession().getServerID() != null)
                            dbAdapter.insertAllTrainingDataFromServer(serverTrainingSessionDTOs);
                        Log.e("Super", "retrieve last session = " + responseDTO.getLastSession());
                        if (!responseDTO.getLastSession())
                            retrieveTrainingSession(userId, secureAccessToken, startDate);
                        else
                            stopSelf();

//                        Log.e("Super", "retrieve success = " + serverTrainingSessionDTOs.size());
//                        for (int i = 0; i < serverTrainingSessionDTOs.size(); i++){
//
//                        }
                    }else {
                        Log.e("Super", "retrieve success = faile" );
                    }
                } else {
                    Log.e("Super", "training response is null");
                }
            }

            @Override
            public void onFailure(Call<ServerTrainingResponseDTO_OLD> call, Throwable t) {
                super.onFailure(call, t);
                Log.e("Super", "training response fail " + t);
            }
        });
    }

    private void retreiveUnsyncedTrainingData(){

        //get last date from training session in local db.

        String userId = CommonUtils.getServerUserId(this);
        String accessToken = CommonUtils.getAccessTokenValue(this);

        String startDate = dbAdapter.getLastSyncDate(userId);

        if (TextUtils.isEmpty(startDate)){
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(new Date());
//            cal.add(Calendar.DATE, -30);
//            startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

            startDate = "0";
        }

        Log.e("Super", "last sync date result = " + startDate + "    " + userId + "    " + accessToken);
        retrieveTrainingSession(userId, accessToken, startDate);
    }
    */
}
