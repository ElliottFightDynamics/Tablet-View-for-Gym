package com.efd.striketectablet.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.efd.striketectablet.DTO.responsedto.AnalyzeCSVDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingPlanResultDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingPunchDetailDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingPunchStatDTO;
import com.efd.striketectablet.DTO.responsedto.DBTrainingSessionDTO;
import com.efd.striketectablet.DTO.responsedto.SyncResponseDTO;
import com.efd.striketectablet.DTO.responsedto.SyncServerResponseDTO;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.api.RetrofitSingletonCSV;
import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;
import com.efd.striketectablet.exception.EFDExceptionHandler;
import com.efd.striketectablet.util.IndicatorCallback;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class SendDataToWebService extends Activity {

    private final String TAG = "SendDataToWebService";

    public Context getAppContext() {
        return this.getApplicationContext();
    }

    /**
     * Called when the activity is first created.
     */
    //public static TextView txtTime;
    DBAdapter db = DBAdapter.getInstance(SendDataToWebService.this);

    private final String tableTrainingSession = DBAdapter.getTrainingSessionTable();
    private final String tableTrainingPunchStats = DBAdapter.getTrainingPunchStatsTable();
    private final String tablePlanResultTable = DBAdapter.getTrainingPlanResultsTable();
    private final String tablePunchDetailsTable = DBAdapter.getTrainingPunchDetailTable();


    private boolean isDataInTrainingSessionExist = true;
    private boolean isDataInTrainingPunchStatsExist = true;
    private boolean isDataInTrainingPlanResultsExist = true;
    private boolean isDataInTrainingDetailsExist = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handling Uncaught Exceptions
        Thread.setDefaultUncaughtExceptionHandler(new EFDExceptionHandler(this));
    }

    /**
     * syncAllWhileDataFound to sync trainee data if unsync data found
     */
    public void syncAllWhileDataFound() {
        MainActivity.setSynchronizingWithServer(true);
        synchronizeCSVFiles();
    }

    private void synchronizeCSVFiles() {
       File file = getUploadableFile();

        if (file == null){
            //goto next stop, sync training session
            synchronizeTrainingSessionRecords();
        }else {
            uploadCSVFile(file);
        }
    }

    private File getUploadableFile(){
        String logDirectory = EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + EFDConstants.LOGS_DIRECTORY;
        File logDir = new File(logDirectory);

        if (!logDir.exists()) {
            return null;
        }

        File[] files = logDir.listFiles();

        for (int i = 0; i < files.length; i++) {
            Log.e("Super", "files  = " + files[i].getAbsolutePath());
            //get timestamp
            String filename = files[i].getName();
            String[] splitednames = filename.split("-");
            String timestamp = splitednames[splitednames.length - 2];
            String hand = splitednames[splitednames.length - 3];

            Log.e("Super", "timestamp = " + timestamp + "    " + hand);

            if (db.checkCSVfileUploadable(timestamp, Integer.parseInt(MainActivity.getInstance().userId))) {
                return files[i];
            }
        }

        return null;
    }

    private void uploadCSVFile(final File file){

        String filename = file.getName();
        String[] splitednames = filename.split("-");
        final String timestamp = splitednames[splitednames.length - 2];
        final String hand = splitednames[splitednames.length - 3];

        Log.e("Super", "timestamp = " + timestamp + "    " + hand);

        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("data", file.getName(), requestFile);

        RetrofitSingletonCSV.CSV_REST.uploadCSV(body).enqueue(new IndicatorCallback<AnalyzeCSVDTO>(MainActivity.getInstance(), false) {
            @Override
            public void onResponse(Call<AnalyzeCSVDTO> call, Response<AnalyzeCSVDTO> response) {
                super.onResponse(call, response);
                if (response.body() != null) {
                    AnalyzeCSVDTO analyzeCSVDTO = response.body();
                    Log.e("Super", "uploading csv file= " + analyzeCSVDTO.getError() + "    " + analyzeCSVDTO.getFileName());
                    if (!TextUtils.isEmpty(analyzeCSVDTO.getFileName())){
                        boolean isLeft = hand.equalsIgnoreCase("L")? true : false;
                        //after uploading delete uploade file.
                        file.delete();
                        db.updatesessionInfofile(Integer.parseInt(MainActivity.getInstance().userId), timestamp, analyzeCSVDTO.getFileName(), isLeft, true);
                    }else {
                        StatisticUtil.showToastMessage(analyzeCSVDTO.getError());

                        //update session
                        synchronizeTrainingSessionRecords();
                    }
                } else {
                    synchronizeTrainingSessionRecords();

                }
            }
            @Override
            public void onFailure(Call<AnalyzeCSVDTO> call, Throwable t) {
                super.onFailure(call, t);
                //update session
                synchronizeTrainingSessionRecords();
            }
        });
    }

    /**
     * Synchronize the Training Session Records
     */
    private void synchronizeTrainingSessionRecords() {

        // Training Session
        List<DBTrainingSessionDTO> dbTrainingSessionDTOList = new ArrayList<DBTrainingSessionDTO>();
        dbTrainingSessionDTOList = db.getAllNonSynchronizedTrainingSessionRecords(EFDConstants.SYNC_RECORDS_LIMIT, Integer.valueOf(MainActivity.getInstance().userId));
        Gson gsonTrainingSession = new GsonBuilder().create();
        Log.e(TAG, " dbTrainingSessionDTOList: " + gsonTrainingSession.toJson(dbTrainingSessionDTOList));

        if (gsonTrainingSession.toJson(dbTrainingSessionDTOList).equals("[]")) {
            isDataInTrainingSessionExist = false;
            synchronizeTrainingPunchStatsRecords();
            resetSyncFlagIfDataNotExist();
        } else {
            isDataInTrainingSessionExist = true;

            RetrofitSingleton.TRAINING_REST.saveTrainingSession(CommonUtils.getServerUserId(MainActivity.context),
                    CommonUtils.getAccessTokenValue(MainActivity.context),
                    gsonTrainingSession.toJson(dbTrainingSessionDTOList)).enqueue(new IndicatorCallback<SyncServerResponseDTO>(MainActivity.context, false) {
                @Override
                public void onResponse(Call<SyncServerResponseDTO> call, Response<SyncServerResponseDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        SyncServerResponseDTO syncServerResponseDTO = response.body();

                        if (syncServerResponseDTO.getAccess()) {
                            MainActivity.isAccessTokenValid = true;
                            if (syncServerResponseDTO.getSuccess()) {
                                List<SyncResponseDTO> syncResponseDTOs = syncServerResponseDTO.getJsonArrayResponse();
                                int success = db.updateSynchronizedRecordsForTable(tableTrainingSession, syncResponseDTOs);
                                Log.e("Super", "session success = ");
                                synchronizeTrainingSessionRecords();
                            }
                        } else {
                            MainActivity.isAccessTokenValid = false;
                        }
                    } else {
                        Log.e("Super", "session success, body is null ");
                        synchronizeTrainingPunchStatsRecords();
                    }
                }

                @Override
                public void onFailure(Call<SyncServerResponseDTO> call, Throwable t) {
                    super.onFailure(call, t);

                    Log.e("Super", "session faild  " + t);
                }
            });
        }
    }

    private void synchronizeTrainingPunchStatsRecords(){
        List<DBTrainingPunchStatDTO> dbTrainingPunchStatDTOs = new ArrayList<DBTrainingPunchStatDTO>();
        dbTrainingPunchStatDTOs = db.getAllNonSynchronizedTrainingPunchStatRecords(EFDConstants.SYNC_RECORDS_LIMIT, Integer.valueOf(MainActivity.getInstance().userId));
        Gson gsonTrainingPunchStats = new GsonBuilder().create();

        Log.e(TAG, " dbTrainingPunchStatsDTOList: " + gsonTrainingPunchStats.toJson(dbTrainingPunchStatDTOs));

        if (gsonTrainingPunchStats.toJson(dbTrainingPunchStatDTOs).equals("[]")){
            isDataInTrainingPunchStatsExist = false;
            synchronizeTrainingPlanResultsRecords();
            resetSyncFlagIfDataNotExist();
        }else {
            isDataInTrainingPunchStatsExist = true;

            RetrofitSingleton.TRAINING_REST.saveTrainingPunchStats(CommonUtils.getServerUserId(MainActivity.context),
                    CommonUtils.getAccessTokenValue(MainActivity.context),
                    gsonTrainingPunchStats.toJson(dbTrainingPunchStatDTOs)).enqueue(new IndicatorCallback<SyncServerResponseDTO>(MainActivity.context, false) {
                @Override
                public void onResponse(Call<SyncServerResponseDTO> call, Response<SyncServerResponseDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        SyncServerResponseDTO syncServerResponseDTO = response.body();

                        if (syncServerResponseDTO.getAccess()){
                            MainActivity.isAccessTokenValid = true;
                            if (syncServerResponseDTO.getSuccess()){
                                List<SyncResponseDTO> syncResponseDTOs = syncServerResponseDTO.getJsonArrayResponse();
                                int success = db.updateSynchronizedRecordsForTable(tableTrainingPunchStats, syncResponseDTOs);
                                Log.e("Super", "punch stats success = ");
                                synchronizeTrainingPunchStatsRecords();
                            }
                        }else {
                            MainActivity.isAccessTokenValid = false;
                        }
                    } else {
                        Log.e("Super", "punch stats success, body is null ");
                        synchronizeTrainingPlanResultsRecords();
                    }
                }

                @Override
                public void onFailure(Call<SyncServerResponseDTO> call, Throwable t) {
                    super.onFailure(call, t);

                    Log.e("Super", "punch stats  faild  " + t);
                }
            });
        }
    }

    private void synchronizeTrainingPlanResultsRecords(){
        List<DBTrainingPlanResultDTO> dbTrainingPlanResultDTOs = new ArrayList<DBTrainingPlanResultDTO>();
        dbTrainingPlanResultDTOs = db.getAllNonSynchronizedTrainingPlanResultRecords(EFDConstants.SYNC_RECORDS_LIMIT, Integer.valueOf(MainActivity.getInstance().userId));

        Gson gsonTrainingPlanResults = new GsonBuilder().create();
        Log.e(TAG, " dbTrainingPlanResultDTOs: " + gsonTrainingPlanResults.toJson(dbTrainingPlanResultDTOs));

        if (gsonTrainingPlanResults.toJson(dbTrainingPlanResultDTOs).equals("[]")){
            isDataInTrainingPlanResultsExist = false;
//            synchronizeTrainingPunchDetailRecords();
            resetSyncFlagIfDataNotExist();
        }else {
            isDataInTrainingPlanResultsExist = true;
            Log.e("Super", "id = " + CommonUtils.getServerUserId(MainActivity.context) + "   " + CommonUtils.getAccessTokenValue(MainActivity.context));
            RetrofitSingleton.TRAINING_REST.saveTrainingPlanResults(CommonUtils.getServerUserId(MainActivity.context),
                    CommonUtils.getAccessTokenValue(MainActivity.context),
                    gsonTrainingPlanResults.toJson(dbTrainingPlanResultDTOs)).enqueue(new IndicatorCallback<SyncServerResponseDTO>(MainActivity.context, false) {
                @Override
                public void onResponse(Call<SyncServerResponseDTO> call, Response<SyncServerResponseDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        SyncServerResponseDTO syncServerResponseDTO = response.body();

                        if (syncServerResponseDTO.getAccess()){
                            MainActivity.isAccessTokenValid = true;
                            if (syncServerResponseDTO.getSuccess()){
                                List<SyncResponseDTO> syncResponseDTOs = syncServerResponseDTO.getJsonArrayResponse();
                                int success = db.updateSynchronizedRecordsForTable(tablePlanResultTable, syncResponseDTOs);
                                Log.e("Super", "plan results success = ");
                                synchronizeTrainingPlanResultsRecords();
                            }
                        }else {
                            MainActivity.isAccessTokenValid = false;
                            Log.e("Super", "plan results success = access is false");
                        }
                    } else {
                        Log.e("Super", "plan results success, body is null ");
                        synchronizeTrainingPunchDetailRecords();
                    }
                }

                @Override
                public void onFailure(Call<SyncServerResponseDTO> call, Throwable t) {
                    super.onFailure(call, t);

                    Log.e("Super", "plan results faild  " + t);
                }
            });
        }
    }

    private void synchronizeTrainingPunchDetailRecords(){
        List<DBTrainingPunchDetailDTO> dbTrainingPunchDetailDTOs = new ArrayList<DBTrainingPunchDetailDTO>();
        dbTrainingPunchDetailDTOs = db.getAllNonSynchronizedTrainingDetailRecords(EFDConstants.SYNC_RECORDS_LIMIT, Integer.valueOf(MainActivity.getInstance().userId));

        Gson gsonTrainingDetails = new GsonBuilder().create();
        Log.e(TAG, " dbTrainingPunchDetailDTOs: " + gsonTrainingDetails.toJson(dbTrainingPunchDetailDTOs));

        if (gsonTrainingDetails.toJson(dbTrainingPunchDetailDTOs).equals("[]")){
            isDataInTrainingDetailsExist = false;
            resetSyncFlagIfDataNotExist();
        }else {
            isDataInTrainingDetailsExist = true;

            RetrofitSingleton.TRAINING_REST.saveTrainingPunchDetails(CommonUtils.getServerUserId(MainActivity.context),
                    CommonUtils.getAccessTokenValue(MainActivity.context),
                    gsonTrainingDetails.toJson(dbTrainingPunchDetailDTOs)).enqueue(new IndicatorCallback<SyncServerResponseDTO>(MainActivity.context, false) {
                @Override
                public void onResponse(Call<SyncServerResponseDTO> call, Response<SyncServerResponseDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        SyncServerResponseDTO syncServerResponseDTO = response.body();

                        if (syncServerResponseDTO.getAccess()){
                            MainActivity.isAccessTokenValid = true;
                            if (syncServerResponseDTO.getSuccess()){
                                List<SyncResponseDTO> syncResponseDTOs = syncServerResponseDTO.getJsonArrayResponse();
                                int success = db.updateSynchronizedRecordsForTable(tablePunchDetailsTable, syncResponseDTOs);
                                Log.e("Super", "punch detail  success = ");
                                synchronizeTrainingPunchDetailRecords();
                            }
                        }else {
                            MainActivity.isAccessTokenValid = false;
                        }
                    } else {
                        Log.e("Super", "punch detail success, body is null ");
                    }
                }

                @Override
                public void onFailure(Call<SyncServerResponseDTO> call, Throwable t) {
                    super.onFailure(call, t);

                    Log.e("Super", "punch detail faild  " + t);
                }
            });
        }
    }

    /**
     * Reset Sync Flag If Data Not Exist
     */
    public void resetSyncFlagIfDataNotExist() {
        if (!isDataInTrainingSessionExist &&
                !isDataInTrainingPunchStatsExist &&
                !isDataInTrainingDetailsExist &&
                !isDataInTrainingPlanResultsExist) {

                 MainActivity.setSynchronizingWithServer(false);
        }
    }
}
