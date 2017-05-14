package com.efd.striketectablet.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.efd.striketectablet.DTO.DBTrainingDataDTO;
import com.efd.striketectablet.DTO.DBTrainingDataDetailsDTO;
import com.efd.striketectablet.DTO.DBTrainingPunchDataDTO;
import com.efd.striketectablet.DTO.DBTrainingPunchDataPeakSummaryDTO;
import com.efd.striketectablet.DTO.DBTrainingSessionDTO;
import com.efd.striketectablet.DTO.SyncResponseDTO;
import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;
import com.efd.striketectablet.exception.EFDExceptionHandler;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendDataToWebService extends Activity {
    int sessionId, trainingDataId, trainingDataDetailsId, trainingPunchDataId, trainingPunchDataPeakSummaryId;
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
    private final String tableTrainingData = DBAdapter.getTrainingDataTable();
    private final String tableTrainingDataDetails = DBAdapter.getTrainingDataDetailsTable();
    private final String tableTrainingPunchData = DBAdapter.getTrainingPunchDataTable();
    private final String tableTrainingPunchDataPeakSummary = DBAdapter.getTrainingPunchDataPeakSummaryTable();

    private boolean isDataInTrainingSessionExist = true;
    private boolean isDataInTrainingDataExist = true;
    private boolean isDataInTrainingDataDetailsExist = true;
    private boolean isDataInTrainingPunchDataExist = true;
    private boolean isDataInTrainingPunchDataPeakSummaryExist = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handling Uncaught Exceptions
        Thread.setDefaultUncaughtExceptionHandler(new EFDExceptionHandler(this));
    }

    /**
     * Delete records which are completely synced and are more than 30 days old.
     */
    public void deletePastSyncedRecords() {
        final int MORE_THAN_N_DAYS = 30;
        db.deleteCompletelySyncedTrainingSessionRecords(MORE_THAN_N_DAYS);
    }

    class Data {

        String webService = PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();

        String url;
        String action;
        String method;
        Map<String, String> params;

        public Data(String webServiceUrl, Map<String, String> param,
                    String httpMethod, String actionData) {
            url = this.webService + "/" + webServiceUrl;
            params = param;
            method = httpMethod;
            action = actionData;
        }
    }

    private class AsyncCallWS extends AsyncTask<Data, Void, String> {
        String action;

        private String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");
                }
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        @Override
        protected String doInBackground(Data... params) {
            action = params[0].action;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> boxer : params[0].params.entrySet()) {
                param.add(new BasicNameValuePair(boxer.getKey(), boxer.getValue()));
            }

            String text = null;
            HttpGet httpGet = null;
            HttpPost httpPost = null;
            HttpResponse response = null;
            if ((params[0].method).equals("GET")) {
                httpGet = new HttpGet(params[0].url);
                try {
                    response = httpClient.execute(httpGet, localContext);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ((params[0].method).equals("POST")) {
                httpPost = new HttpPost(params[0].url);
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
                    response = httpClient.execute(httpPost, localContext);
                    //response.getStatusLine().getStatusCode();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {

                HttpEntity entity = response.getEntity();
                if (entity != null) {

                    InputStream instream = entity.getContent();
                    text = convertStreamToString(instream);
                    instream.close();
                }
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
            return text;

        }

        protected void onPostExecute(String result) {
            //new code for sync. using unique keys
            if (result != null) {
                if (action.equals("saveSynchronizedTrainingSession")) {
                    performLocalUpdates(result, tableTrainingSession);
                } else if (action.equals("saveSynchronizedTrainingData")) {
                    performLocalUpdates(result, tableTrainingData);
                } else if (action.equals("saveSynchronizedTrainingDataDetails")) {
                    performLocalUpdates(result, tableTrainingDataDetails);
                } else if (action.equals("saveSynchronizedTrainingPunchData")) {
                    performLocalUpdates(result, tableTrainingPunchData);
                } else if (action.equals("saveSynchronizedPunchDataPeakSummary")) {
                    performLocalUpdates(result, tableTrainingPunchDataPeakSummary);
                } else if (action.equals("synchronizeUserInfoAfterEdit")) {
                    setSyncFlag(result, DBAdapter.getUserTable());
                }
            }
        }
    }

    /**
     * syncAllWhileDataFound to sync trainee data if unsync data found
     */
    public void syncAllWhileDataFound() {
//super        MainActivity.setSynchronizingWithServer(true);
        synchronizeTrainingSessionRecords();
    }

    /**
     * Synchronize the Training PunchData Peak Summary Records
     */
    private void synchronizeTrainingPunchDataPeakSummaryRecords() {

//        Log.d(TAG,"-- Start Syncing synchronizeTrainingPunchDataPeakSummaryRecords " + new Timestamp((new Date()).getTime()));

        // Training Punch Data Peak Summary
        List<DBTrainingPunchDataPeakSummaryDTO> dataPeakSummaries = new ArrayList<DBTrainingPunchDataPeakSummaryDTO>();
        dataPeakSummaries = db.getAllNonSynchronizedTrainingPunchDataPeakSummaryRecords(EFDConstants.SYNC_RECORDS_LIMIT);

        Gson gsonTrainingPunchDataPeakSummaries = new GsonBuilder().create();
        Map<String, String> paramsForTrainingPunchDataPeakSummary = new HashMap<String, String>();
        paramsForTrainingPunchDataPeakSummary.put("training_punch_data_peak_summary", gsonTrainingPunchDataPeakSummaries.toJson(dataPeakSummaries));
        //Log.d(TAG,"paramsForTrainingPunchDataPeakSummary " + paramsForTrainingPunchDataPeakSummary.get("training_punch_data_peak_summary"));

        if (paramsForTrainingPunchDataPeakSummary.get("training_punch_data_peak_summary").equals("[]")) {
            isDataInTrainingPunchDataPeakSummaryExist = false;
            resetSyncFlagIfDataNotExist();
        } else {
            isDataInTrainingPunchDataPeakSummaryExist = true;
            sendDataToWS(paramsForTrainingPunchDataPeakSummary, "trainingPunchDataPeakSummary/saveBulkLocalData", "saveSynchronizedPunchDataPeakSummary");
        }

//        Log.d(TAG,"-- completed function synchronizeTrainingPunchDataPeakSummaryRecords " + new Timestamp((new Date()).getTime()));
    }

    /**
     * Synchronize the Training PunchData Records
     */
    private void synchronizeTrainingPunchDataRecords() {

//        Log.d(TAG,"-- Start Syncing synchronizeTrainingPunchDataRecords " + new Timestamp((new Date()).getTime()));

        // Training Punch Data
        List<DBTrainingPunchDataDTO> dbTrainingPunchDataDTOs = new ArrayList<DBTrainingPunchDataDTO>();
        dbTrainingPunchDataDTOs = db.getAllNonSynchronizedTrainingPunchDataRecords(EFDConstants.SYNC_RECORDS_LIMIT);

        Gson gsonTrainingPunchData = new GsonBuilder().create();
        Map<String, String> paramsForTrainingPunchData = new HashMap<String, String>();
        paramsForTrainingPunchData.put("training_punch_data", gsonTrainingPunchData.toJson(dbTrainingPunchDataDTOs));
        //Log.d(TAG,"synchronizeTrainingPunchDataRecords " + paramsForTrainingPunchData.get("training_punch_data"));

        if (paramsForTrainingPunchData.get("training_punch_data").equals("[]")) {
            isDataInTrainingPunchDataExist = false;
            synchronizeTrainingPunchDataPeakSummaryRecords();
            resetSyncFlagIfDataNotExist();
        } else {
            isDataInTrainingPunchDataExist = true;
            sendDataToWS(paramsForTrainingPunchData, "trainingPunchData/saveBulkLocalData", "saveSynchronizedTrainingPunchData");
        }

//        Log.d(TAG,"-- completed function synchronizeTrainingPunchDataRecords " + new Timestamp((new Date()).getTime()));
    }

    /**
     * Synchronize the Training Data Details Records
     */
    private void synchronizeTrainingDataDetailsRecords() {

//        Log.d(TAG,"-- Start Syncing synchronizeTrainingDataDetailsRecords " + new Timestamp((new Date()).getTime()));

        // Training Data Details
        List<DBTrainingDataDetailsDTO> dbTrainingDataDetailsDTOs = new ArrayList<DBTrainingDataDetailsDTO>();
        dbTrainingDataDetailsDTOs = db.getAllNonSynchronizedTrainingDataDetailsRecords(EFDConstants.SYNC_RECORDS_LIMIT);

        Gson gsonTrainingDataDetails = new GsonBuilder().create();
        Map<String, String> paramsForTrainingDataDetails = new HashMap<String, String>();
        paramsForTrainingDataDetails.put("training_data_details", gsonTrainingDataDetails.toJson(dbTrainingDataDetailsDTOs));
        //Log.d(TAG,"-- paramsForTrainingDataDetails " + paramsForTrainingDataDetails.get("training_data_details"));

        if (paramsForTrainingDataDetails.get("training_data_details").equals("[]")) {
            isDataInTrainingDataDetailsExist = false;
            resetSyncFlagIfDataNotExist();
            synchronizeTrainingPunchDataRecords();
        } else {
            isDataInTrainingDataDetailsExist = true;
            sendDataToWS(paramsForTrainingDataDetails, "trainingDataDetails/saveBulkLocalData", "saveSynchronizedTrainingDataDetails");
        }

//        Log.d(TAG,"-- completed function synchronizeTrainingDataDetailsRecords " + new Timestamp((new Date()).getTime()));
    }

    /**
     * Synchronize the Training Data Records
     */
    private void synchronizeTrainingDataRecords() {


        // Training Data
        List<DBTrainingDataDTO> dbTrainingDataDTOs = new ArrayList<DBTrainingDataDTO>();
        dbTrainingDataDTOs = db.getAllNonSynchronizedTrainingDataRecords(EFDConstants.SYNC_RECORDS_LIMIT);

        Gson gsonTrainingData = new GsonBuilder().create();

        Map<String, String> paramsForTrainingData = new HashMap<String, String>();
        paramsForTrainingData.put("training_data", gsonTrainingData.toJson(dbTrainingDataDTOs));
        Log.d(TAG, "synchronizeTrainingDataRecords data " + paramsForTrainingData.get("training_data"));

        if (paramsForTrainingData.get("training_data").equals("[]")) {
            isDataInTrainingDataExist = false;
//        	resetSyncFlagIfDataNotExist();
            synchronizeTrainingDataDetailsRecords();
            //synchronizeTrainingPunchDataRecords();
            //synchronizeTrainingPunchDataPeakSummaryRecords();
        } else {
            isDataInTrainingDataExist = true;
            sendDataToWS(paramsForTrainingData, "trainingData/saveBulkLocalData", "saveSynchronizedTrainingData");
        }

//        Log.d(TAG,"-- completed function synchronizeTrainingDataRecords " + new Timestamp((new Date()).getTime()));
    }

    /**
     * Synchronize the Training Session Records
     */
    private void synchronizeTrainingSessionRecords() {

//        Log.d(TAG,"Start Syncing synchronizeTrainingSessionRecords " + new Timestamp((new Date()).getTime()));

        // Training Session
        List<DBTrainingSessionDTO> dbTrainingSessionDTOList = new ArrayList<DBTrainingSessionDTO>();
        dbTrainingSessionDTOList = db.getAllNonSynchronizedTrainingSessionRecords(EFDConstants.SYNC_RECORDS_LIMIT);

        Log.i(TAG, " dbTrainingSessionDTOList: " + dbTrainingSessionDTOList.toString());

        Gson gsonTrainingSession = new GsonBuilder().create();
        Map<String, String> paramsForTrainingSession = new HashMap<String, String>();
        paramsForTrainingSession.put("training_session", gsonTrainingSession.toJson(dbTrainingSessionDTOList));
        Log.i(TAG, "synchronizeTrainingSessionRecords  data " + paramsForTrainingSession.get("training_session"));

        if (paramsForTrainingSession.get("training_session").equals("[]")) {
            isDataInTrainingSessionExist = false;
//        	resetSyncFlagIfDataNotExist();
            synchronizeTrainingDataRecords();
        } else {
            isDataInTrainingSessionExist = true;
            sendDataToWS(paramsForTrainingSession, "trainingSession/saveBulkLocalData", "saveSynchronizedTrainingSession");
        }
//        Log.d(TAG,"-- Final completed function synchronizeTrainingSessionRecords " + new Timestamp((new Date()).getTime()));
    }

    /**
     * Send Data To Web Server
     *
     * @param params
     * @param url
     * @param action
     */
    public void sendDataToWS(Map<String, String> params, String url, String action) {
        /*//super
        Log.i("sendDataToWS ", "SECURE ACCESS TOKEN: " + CommonUtils.getAccessTokenValue(MainActivity.context));
        Log.i("sendDataToWS ", "SERVER USER ID: " + CommonUtils.getServerUserId(MainActivity.context));

        if (CommonUtils.getAccessTokenValue(MainActivity.context) != null && !"".equals(CommonUtils.getAccessTokenValue(MainActivity.context).trim()) &&
                CommonUtils.getServerUserId(MainActivity.context) != null && !"".equals(CommonUtils.getServerUserId(MainActivity.context).trim())) {
            params.put(EFDConstants.KEY_SECURE_ACCESS_TOKEN, CommonUtils.getAccessTokenValue(MainActivity.context));
            params.put(EFDConstants.KEY_USER_ID, CommonUtils.getServerUserId(MainActivity.context));
            final String method = EFDConstants.HTTP_POST_METHOD;
            Data obj = new Data(url, params, method, action);
            AsyncCallWS task = new AsyncCallWS();
            task.execute(obj);
        }
        */
    }

    /**
     * Perform Local Updates
     *
     * @param result
     * @param tableName
     */
    public void performLocalUpdates(String result, String tableName) {

//		Log.d(TAG,"-- result " + result);

        Type listType = new TypeToken<List<SyncResponseDTO>>() {
        }.getType();
        try {
            JSONObject jsonResponce = new JSONObject(result);
            Log.i("performLocalUpdates ", "tableName " + tableName);
            if ((jsonResponce.getString(EFDConstants.KEY_ACCESS) != null) && jsonResponce.getBoolean(EFDConstants.KEY_ACCESS)) {
                if (jsonResponce.getBoolean("success")) {
                    ArrayList<SyncResponseDTO> serverResponse = new Gson().fromJson(jsonResponce.getJSONArray("jsonArrayResponse").toString(), listType);
                    int success = db.updateSynchronizedRecordsForTable(tableName, serverResponse);
                    Log.i("performLocalUpdates ", " updateSynchronizedRecordsForTable --" + success);

                    if (tableName.equals(tableTrainingSession)) {
                        synchronizeTrainingSessionRecords();
                    }
                    if (tableName.equals(tableTrainingData)) {
                        synchronizeTrainingDataRecords();
                    }
                    if (tableName.equals(tableTrainingDataDetails)) {
                        synchronizeTrainingDataDetailsRecords();
                    }
                    if (tableName.equals(tableTrainingPunchData)) {
                        synchronizeTrainingPunchDataRecords();
                    }
                    if (tableName.equals(tableTrainingPunchDataPeakSummary)) {
                        synchronizeTrainingPunchDataPeakSummaryRecords();
                    }
                    //resetSyncFlagIfDataNotExist();
//super                    MainActivity.isAccessTokenValid = true;
                }
            } else {
//super                MainActivity.isAccessTokenValid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset Sync Flag If Data Not Exist
     */
    public void resetSyncFlagIfDataNotExist() {
        Log.i(TAG, "-- data exists ? " + isDataInTrainingSessionExist + " " +
                isDataInTrainingDataExist + " " +
                isDataInTrainingDataDetailsExist + " " +
                isDataInTrainingPunchDataExist + " " +
                isDataInTrainingPunchDataPeakSummaryExist);

        if (!isDataInTrainingSessionExist &&
                !isDataInTrainingDataExist &&
                !isDataInTrainingDataDetailsExist &&
                !isDataInTrainingPunchDataExist &&
                !isDataInTrainingPunchDataPeakSummaryExist) {

//			System.out.println("-- set flag to false");
            //super       MainActivity.setSynchronizingWithServer(false);
        }
    }

    /**
     * Synchronize User Info details
     */
    public void synchronizeUserInfoAfterEdit() {
        final String url = "user/updateUserInfo";
        final String action = "synchronizeUserInfoAfterEdit";
        Map<String, String> params = db.getNonSyncedUserInformation();

//		Log.d(TAG,"-- synchronizeUserInfoAfterEdit params " + params);

        if (params != null) {
            sendDataToWS(params, url, action);
        }

    }

    /**
     * Set Sync Flag
     *
     * @param result
     * @param tableName
     */
    public void setSyncFlag(String result, String tableName) {
        int sync = 1;

        try {
            if (result != null) {
                JSONObject jsonObject = new JSONObject(result);
                //Log.d(TAG,"tableName: "+tableName);
                if ((jsonObject.getString(EFDConstants.KEY_ACCESS) != null) && jsonObject.getBoolean(EFDConstants.KEY_ACCESS)) {
                    if (jsonObject.getString("success").equals("true")) {
                        JSONObject userObject = jsonObject.getJSONObject("user");
                        db.setSyncFlag(userObject.getInt("id"), tableName, sync);
                        //super            MainActivity.isAccessTokenValid = true;
                    }
                } else {
//					CommonUtils.showAlertDialog(MainActivity.context, jsonObject.getString("message") );	// TODO: Comment this message
                    //super          MainActivity.isAccessTokenValid = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
