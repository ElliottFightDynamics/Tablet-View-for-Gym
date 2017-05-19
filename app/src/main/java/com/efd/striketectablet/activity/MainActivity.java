package com.efd.striketectablet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.efd.striketectablet.DTO.CalendarSummaryDTO;
import com.efd.striketectablet.DTO.ProgressSummaryDTO;
import com.efd.striketectablet.DTO.PunchCountSummaryDTO;
import com.efd.striketectablet.DTO.PunchDataDTO;
import com.efd.striketectablet.DTO.PunchHistoryGraphDataDetails;
import com.efd.striketectablet.DTO.ResultSummaryDTO;
import com.efd.striketectablet.DTO.TrainingBatteryLayoutDTO;
import com.efd.striketectablet.DTO.TrainingBatteryVoltageDTO;
import com.efd.striketectablet.DTO.TrainingConnectStatusDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.profile.ProfileFragment;
import com.efd.striketectablet.activity.training.TrainingFragment;
import com.efd.striketectablet.activity.trainingstats.TrainingStatsFragment;
import com.efd.striketectablet.bluetooth.Connection.ConnectionManager;
import com.efd.striketectablet.bluetooth.Connection.ConnectionThread;
import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.database.SendDataToWebService;
import com.efd.striketectablet.mmaGlove.EffectivePunchMassCalculator;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;
import com.efd.striketectablet.utilities.JSONParsers;
import com.efd.striketectablet.utilities.SharedPreferencesUtils;
import com.efd.striketectablet.utilities.TrainingManager;
import com.efd.striketectablet.utilities.TrainingTimer;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int REQUEST_ENABLE_BT = 1;
    public static final int MESSAGE_TOAST = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int rightDeviceRequestCode = 4;
    public static final int leftDeviceRequestCode = 5;
    public static final int guestRightDeviceRequestCode = 6;
    public static final int guestLeftDeviceRequestCode = 7;

    public static final String TOAST = "TOAST";

    public static double boxerPunchMassEffect;

    public boolean flagForDevice;
    String currentRightDevice = null, currentLeftDevice = null;

    private boolean isGuestBoxerActive;

    public String userId = "1";
    public static DBAdapter db;

    public PunchDataDTO punchDataDTO = new PunchDataDTO();

    public static String leftHandBatteryVoltage = "", rightHandBatteryVoltage = "";
    private boolean isDeviceLeftConnectionFinish;
    private boolean isDeviceRightConnectionFinish;
    private int checkDeviceConnectionFlag = 0;
    public String deviceLeft = "";
    public String deviceRight = "";
    public boolean leftSensorConnected = false;
    public boolean rightSensorConnected = false;

    private ConnectionThread leftHandConnectionThread = null;
    private ConnectionThread rightHandConnectionThread = null;
    public static ConnectionManager rightDeviceConnectionManager, leftDeviceConnectionManager;
    public static TrainingTimer trainingDurationCounterThread = null;

    public Integer trainingSessionId;
    Integer trainingRightHandId;
    Integer trainingLeftHandId;
    Integer currentTrainingSessionId;

    public String endTrainingTime = EFDConstants.DEFAULT_START_TIME;

    public List<PunchHistoryGraphDataDetails> punchHistoryGraph;

    public HashMap<String, String> liveMonitorDataMap;

    public static Handler customHandler = new Handler();

    public static Context context;
    private CustomViewPagerAdapter mAdapter;
    private ViewPager viewPager;

    public TrainingManager trainingManager = new TrainingManager();

    static MainActivity instance;
    public static MainActivity getInstance(){
        return instance;
    }

    private static boolean isSynchronizingWithServer = false;
    public static boolean isAccessTokenValid = true;

    public Timer timer;
    SendDataToWebService sendDataObj;

    public int month, year, day;

    public static String boxersStance;

    public static boolean isSynchronizingWithServer() {
        return isSynchronizingWithServer;
    }

    public static void setSynchronizingWithServer(boolean isSynchronizingWithServer) {
        MainActivity.isSynchronizingWithServer = isSynchronizingWithServer;
    }

    public MainActivity() {
        flagForDevice = false;
        setGuestBoxerActive(false);
        Log.d(TAG, "flagForDevice :" + flagForDevice);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initUI();

        final SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreference.getString("userId", "");

        if (TextUtils.isEmpty(userId))
            userId = "1";

        context = this;
        liveMonitorDataMap = new HashMap<String, String>(); // For storing live monitor data when training
        punchHistoryGraph = new LinkedList<PunchHistoryGraphDataDetails>();

        db = DBAdapter.getInstance(MainActivity.this);
        db.deleteGymTrainingSession();
        db.deleteGymTrainingStats();

        isSynchronizingWithServer = false;
        deleteCalendarSummaryBeforeTwoYears();

        // Checking the user is present or not if not then flow goes to login
        checkUser();

        // To reset all null end_time values with proper end_time values
        db.endAllPreviousTrainingSessions();

        createOrUpdateSummaryTables();

        sendDataObj = new SendDataToWebService();
        startBackgroundThread();

        // code to save boxers stance (traditional or non-traditional)
        setBoxersStance();

        int theUserId = Integer.valueOf(userId);
        HashMap<String, String> usersBoxerDetails = MainActivity.db.getUsersBoxerDetails(theUserId);

        double punchMassEffect = calculateBoxerPunchMassEffect(usersBoxerDetails);
        PunchDetectionConfig.getInstance().setPunchMassEff(punchMassEffect);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        setDeviceHandlers();


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);
        }

        instance = this;
    }

    public void deleteGymTrainingData(){
        MainActivity.db.deleteGymTrainingSession();
        MainActivity.db.deleteGymTrainingStats();
    }

    private void setDeviceHandlers() {
        if (!trainingManager.isTrainingRunning()) {
            MainActivity.rightDeviceConnectionManager = new ConnectionManager(mHandler);
            MainActivity.leftDeviceConnectionManager = new ConnectionManager(mHandler);
        }
    }

    public void reconnectSensor(View view) {
        switch (view.getId()) {

            case R.id.left_sensor_connection_layout:
                if (leftDeviceConnectionManager.readerThread == null && trainingManager.isTrainingRunning()) {

                    EventBus.getDefault().post(new TrainingBatteryLayoutDTO(true, false));

                    if (deviceLeft != null) {
                        leftDeviceConnectionManager = new ConnectionManager(mHandler);
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);
                        }

                        Toast.makeText(getApplicationContext(), EFDConstants.LEFT_SENSOR_CONNECTING_MESSAGE, Toast.LENGTH_LONG).show();

                        leftDeviceConnectionManager.connect(CommonUtils.getMacAddress(deviceLeft));

                    } else {
                        Toast.makeText(getApplicationContext(), EFDConstants.PLEASE_ENTER_DEVICE_ID, Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.right_sensor_connection_layout:
                if (rightDeviceConnectionManager.readerThread == null && trainingManager.isTrainingRunning()) {

                    EventBus.getDefault().post(new TrainingBatteryLayoutDTO(false, false));

                    if (deviceRight != null) {
                        rightDeviceConnectionManager = new ConnectionManager(mHandler);
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);
                        }

                        Toast.makeText(getApplicationContext(), EFDConstants.RIGHT_SENSOR_CONNECTING_MESSAGE, Toast.LENGTH_LONG).show();

                        rightDeviceConnectionManager.connect(CommonUtils.getMacAddress(deviceRight));

                    } else {
                        Toast.makeText(getApplicationContext(), EFDConstants.PLEASE_ENTER_DEVICE_ID, Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_WRITE:

                    if (msg.getData().containsKey("jsonData")) {
                        try {
                            if (trainingManager.isTrainingRunning()) {
                                JSONObject punchDataJson = new JSONObject(msg.getData().getString("jsonData"));
                                setDataToLiveMonitorMap(punchDataJson.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (msg.getData().containsKey("batteryVoltage")) {

                        String batteryVoltageString = msg.getData().getString("batteryVoltage");
                        try {
                            JSONObject batteryJson = new JSONObject(batteryVoltageString);
                            if (batteryJson.getString("hand").equals("left")) {
                                leftHandBatteryVoltage = batteryVoltageString;
                            } else {
                                rightHandBatteryVoltage = batteryVoltageString;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        EventBus.getDefault().post(new TrainingBatteryVoltageDTO(false, "", batteryVoltageString));
                    }
                    break;

                case MESSAGE_TOAST:

                    try {
                        setDeviceConnectionFinishFlag();
                        Response response = Response.valueOf(msg.getData().getString("CONNECTION"));
                        handlingConnectionToastResponse(response, msg);
                        initiateTraining();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public void createOrUpdateSummaryTables() {

        createCalendarSummary();
        createResultSummary(EFDConstants.SUMMARY_TYPE_MAX);
        createResultSummary(EFDConstants.SUMMARY_TYPE_AVG);
        createProgressSummary(EFDConstants.SUMMARY_TYPE_DAILY);
        createProgressSummary(EFDConstants.SUMMARY_TYPE_WEEKLY);
        createProgressSummary(EFDConstants.SUMMARY_TYPE_MONTHLY);
        createPunchCountSummary();
    }

    /**
     * Create or update calendar summary record once training is stopped
     * gets called in onCreate() to update record if training is not stopped by user
     */
    public void createCalendarSummary() {

        JSONObject calendarSummaryResult = db.trainingDataDetailsCalendarSummary(Long.parseLong(userId),
                CommonUtils.getCurrentDateStringYMD(), 0, trainingSessionId == null ? 0 : trainingSessionId);

        CalendarSummaryDTO calendarDTO = JSONParsers.parseCalendarResultJSON(calendarSummaryResult, Integer.parseInt(userId), CommonUtils.getCurrentDateStringYMD());

        if (!calendarDTO.getTotalTrainingTime().equals(EFDConstants.DEFAULT_START_TIME)) {
            if (db.isCalendarSummaryRecordExist(CommonUtils.getCurrentDateStringYMD(), Integer.parseInt(userId))) {
                db.updateCalendarSummaryRecord(calendarDTO);
            } else {
                db.insertCalendarSummaryRecord(calendarDTO);
            }
        }
    }

    /**
     * @param summaryType - max / avg
     */
    public void createResultSummary(String summaryType) {
        JSONObject maxData = db.getTrainingResultData(userId, summaryType.toLowerCase(Locale.getDefault()));
        ResultSummaryDTO resultSummaryDTO = JSONParsers.parseTrainingResultJSON(maxData, Integer.parseInt(userId), CommonUtils.getCurrentDateStringYMD(), summaryType);
        if (db.isResultSummaryRecordExist(Integer.parseInt(userId), summaryType.toLowerCase(Locale.getDefault()))) {
            db.updateResultSummaryRecord(resultSummaryDTO);
        } else {
            db.insertResultSummaryRecord(resultSummaryDTO);
        }
    }

    /**
     * Create or update calendar summary record once training is stopped
     * gets called in onCreate() to update record if training is not stopped by user
     */
    public void createPunchCountSummary() {

        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, -6);
        String startDate = df.format(calendar.getTime());
        int currentSessionId = (trainingSessionId != null) ? trainingSessionId : 0;

        JSONObject punchCountSummaryResult = MainActivity.db.getPunchCountScreenDetails(userId, startDate, endDate, currentSessionId);

        try {
            if (punchCountSummaryResult.getBoolean("success")) {

                PunchCountSummaryDTO punchCountSummaryDTO = JSONParsers.parsePunchCountSummaryResultJSON(
                        punchCountSummaryResult.getJSONObject("punchCount"), userId, CommonUtils.getCurrentDateStringYMD());

                if (null != punchCountSummaryDTO) {
                    Integer id = db.isPunchCountSummaryRecordExist(userId);
                    if (null != id) {
                        punchCountSummaryDTO.setId(id);
                        db.updatePunchCountRecord(punchCountSummaryDTO);
                    } else {
                        db.insertPunchCountRecord(punchCountSummaryDTO);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createProgressSummary(String summaryType) {
        // getTrainingProgressData() returns empty map if data does not exist, also it returns punch types thrown
        // So, validate the result and process the data.

        JSONObject jsonObject = new JSONObject(
                db.getTrainingProgressData(userId, CommonUtils.calculatePastDate(CommonUtils.getCurrentDateStringYMD(),
                        Integer.parseInt(summaryType), EFDConstants.MEASURE_DATE), CommonUtils.getCurrentDateStringYMD(),
                        trainingSessionId == null ? 0 : trainingSessionId));

        ProgressSummaryDTO progressSummaryDTO;

        if (jsonObject.length() != 0) {
            progressSummaryDTO = JSONParsers.parseProgressResultJSON(jsonObject, Integer.parseInt(userId), CommonUtils.getCurrentDateStringYMD(), summaryType);
        } else {
            progressSummaryDTO = new ProgressSummaryDTO(Integer.parseInt(userId), CommonUtils.getCurrentDateStringYMD(), summaryType, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        if (db.isProgressSummaryRecordExist(CommonUtils.getCurrentDateStringYMD(), Integer.parseInt(userId), summaryType.toLowerCase())) {
            db.updateProgressSummaryRecord(progressSummaryDTO);
        } else {
            db.insertProgressSummaryRecord(progressSummaryDTO);
        }

    }


    private void checkUser() {
//        if (!db.isUserAvailable()) {
//            Log.d(TAG, "isUserAvailable" + db.isUserAvailable());
//            showAlert();
//        }
    }

    public void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(EFDConstants.DATABASE_NOT_FOUND_ERROR_MESSAGE);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                logoutUser();
            }
        });
        alertDialogBuilder.show();
    }

    public void logoutUser() {
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = settings.edit();
//        /******** code to disconnect the current connected device ***********/
//        if (trainingSessionId != null) {
//            JSONObject result_Training_session_end = db.trainingSessionEnd(trainingSessionId);
//
//        }
//
//        timer.cancel();
//        isSynchronizingWithServer = false;
//        isAccessTokenValid = true;
//        editor.remove(EFDConstants.KEY_USER_ID);
//        editor.remove(EFDConstants.KEY_SERVER_USER_ID);
//        editor.remove(EFDConstants.KEY_SECURE_ACCESS_TOKEN);
//        editor.commit();
//        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }

    // changes for Local Storage start
    // seconds -- check multiplication
    long delay = 10 * EFDConstants.NUM_MILLISECONDS_IN_ONE_SECOND;        //	* EFDConstants.NUM_SECONDS_IN_ONE_MINUTE

    // 1 minute -- check multiplication
    long period = 1 * EFDConstants.NUM_SECONDS_IN_ONE_MINUTE * EFDConstants.NUM_MILLISECONDS_IN_ONE_SECOND;

    private void startBackgroundThread() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (CommonUtils.isOnline(getApplicationContext())) {
                    // TODO: later see if this can be put in a separate class & implemented with @link{FutureTask}

                    // Sync training data
                    if (!isSynchronizingWithServer()) {
                        sendDataObj.syncAllWhileDataFound();
                    }

                    // Delete past synced training data
                    sendDataObj.deletePastSyncedRecords();

                    // sync user information data
                    sendDataObj.synchronizeUserInfoAfterEdit();
                    if (!isAccessTokenValid) {
                        showSessionExpiredAlertDialog();
                    }
                } else {
                    Log.i(TAG, "Internet not available on phone....");
                    isSynchronizingWithServer = false;
                }
            }
        }, delay, period);
    }

    public void showSessionExpiredAlertDialog() {
        //isAccessTokenValid = true;
        timer.cancel();
        try {
            this.runOnUiThread(new Runnable() {
                public void run() {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder
                            .setMessage(EFDConstants.TRAINEE_SESSION_EXPIRED_ERROR)
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            logoutUser();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete calendar summary records of past two years
     */
    @SuppressLint("SimpleDateFormat")
    private void deleteCalendarSummaryBeforeTwoYears() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedCurrentDate = dateFormat.format(new Date());
        String beforeTwoYears = CommonUtils.calculatePastDate(formattedCurrentDate, EFDConstants.CALENDAR_SUMMARY_NUMBER_OF_YEARS, EFDConstants.MEASURE_YEAR);
        db.deleteCalendarSummaryBeforeDate(beforeTwoYears);
    }

    public void setDataToLiveMonitorMap(String liveMonitorData) {
        JSONObject punchDataJson = null;
        try {
            JSONObject roundDetailsJson = new JSONObject(liveMonitorData);
            if (roundDetailsJson.getString("success").equals("true")) {
                punchDataJson = roundDetailsJson.getJSONObject("jsonObject");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String punchType;
        try {
            punchType = punchDataJson.getString("punchType");
            String speed = punchDataJson.getString("speed");
            String force = punchDataJson.getString("force");
            liveMonitorDataMap.put(punchType, liveMonitorData);
            Log.d(TAG, "punch history list size= " + punchHistoryGraph.size());
            if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_LARGE) == Configuration.SCREENLAYOUT_SIZE_LARGE
                    || (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_XLARGE) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                if (punchHistoryGraph.size() >= EFDConstants.TABLET_HISTORY_GRAPH_LENGTH) {
                    punchHistoryGraph.remove(0);
                }
            } else {
                if (punchHistoryGraph.size() >= EFDConstants.PHONE_HISTORY_GRAPH_LENGTH) {
                    punchHistoryGraph.remove(0);
                }
            }
            if (punchType.equalsIgnoreCase(EFDConstants.LEFT_UNRECOGNIZED)
                    || punchType.equalsIgnoreCase(EFDConstants.RIGHT_UNRECOGNIZED)) {
                punchHistoryGraph.add(new PunchHistoryGraphDataDetails(punchType.charAt(0) + EFDConstants.BLANK_TEXT, "UR", force, speed));
            } else {
                punchHistoryGraph.add(
                        new PunchHistoryGraphDataDetails(punchType.charAt(0) + EFDConstants.BLANK_TEXT, punchType.charAt(1) + EFDConstants.BLANK_TEXT, force, speed));
            }

            EventBus.getDefault().post(punchHistoryGraph.get(punchHistoryGraph.size() - 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void handlingConnectionToastResponse(Response response, Message msg) {
        try {
            switch (response) {
                case success:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    deviceConnectionSuccess(msg);
                    break;

                case unsuccess:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
                    if (msg.getData().getString("DeviceAddress").equals(CommonUtils.getMacAddress(deviceRight))) {

                        EventBus.getDefault().post(new TrainingBatteryLayoutDTO(false, true));

                        isDeviceRightConnectionFinish = true;
                        checkDeviceConnectionFlag++;
                        initiateTraining();
                    } else if (msg.getData().getString("DeviceAddress").equals(CommonUtils.getMacAddress(deviceLeft))) {

                        EventBus.getDefault().post(new TrainingBatteryLayoutDTO(true, true));

                        isDeviceLeftConnectionFinish = true;
                        checkDeviceConnectionFlag++;
                        initiateTraining();
                    }

                    EventBus.getDefault().post(new TrainingConnectStatusDTO(false, true, msg.getData().getString("HAND").toString()));

                    break;

                case closed:
                    isDeviceLeftConnectionFinish = false;
                    isDeviceRightConnectionFinish = false;
                    checkDeviceConnectionFlag = 0;

                    if (msg.getData().getString("HAND").toString().equals("left")) {
                        leftHandBatteryVoltage = "";
                    } else {
                        rightHandBatteryVoltage = "";
                    }

                    EventBus.getDefault().post(new TrainingBatteryVoltageDTO(true, msg.getData().getString("HAND").toString(), ""));
                    EventBus.getDefault().post(new TrainingConnectStatusDTO(false, true, msg.getData().getString("HAND").toString()));

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public enum Response {
        success, unsuccess, closed;
    }

    private void initiateTraining() {

        try {
            if (isDeviceRightConnectionFinish || isDeviceLeftConnectionFinish) {
                dialog.dismiss();

//                if (!trainingManager.isTrainingRunning() && checkDeviceConnectionFlag != 2) {
//                    trainingManager.startTraining();
//                    MainActivity.startTrainingTimer();
//                    startTraining();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deviceConnectionSuccess(Message msg) {

        if (msg.getData().getString(EFDConstants.CONNECTED_DEVICE_TEXT).equals(CommonUtils.getMacAddress(deviceLeft))) {
            isDeviceLeftConnectionFinish = true;
            leftHandConnectionThread = leftDeviceConnectionManager.getConnectionThread();

            //left device is connected
            leftSensorConnected = true;

			/*for reconnection device */
            if (trainingManager.isTrainingRunning()) {
                setConnectionManagerConnected(leftHandConnectionThread, boxerName, boxerStance, trainingLeftHandId, EFDConstants.LEFT_HAND);
            }

            EventBus.getDefault().post(new TrainingBatteryLayoutDTO(true, false));
        }

        if (msg.getData().getString(EFDConstants.CONNECTED_DEVICE_TEXT).equals(CommonUtils.getMacAddress(deviceRight))) {
            isDeviceRightConnectionFinish = true;
            rightHandConnectionThread = rightDeviceConnectionManager.getConnectionThread();

            rightSensorConnected = true;

			/*for reconnection device */
            if (trainingManager.isTrainingRunning()) {
                setConnectionManagerConnected(rightHandConnectionThread, boxerName, boxerStance, trainingRightHandId, EFDConstants.RIGHT_HAND);
            }

            EventBus.getDefault().post(new TrainingBatteryLayoutDTO(false, false));
        }
    }

    // Flags are used for start match after both device get connected or not connected
    private void setDeviceConnectionFinishFlag() {
        if (checkDeviceConnectionFlag >= 2) {
            isDeviceLeftConnectionFinish = false;
            isDeviceRightConnectionFinish = false;
            checkDeviceConnectionFlag = 0;
        }
        if (deviceLeft.toString().trim().equals(EFDConstants.BLANK_TEXT)) {
            isDeviceLeftConnectionFinish = true;
            checkDeviceConnectionFlag++;
        }
        if (deviceRight.toString().trim().equals(EFDConstants.BLANK_TEXT)) {
            isDeviceRightConnectionFinish = true;
            checkDeviceConnectionFlag++;
        }
    }

    public void startDeviceConnection(boolean isLeft) {
        if (!isLeft)
            MainActivity.rightDeviceConnectionManager.connect(CommonUtils.getMacAddress(deviceRight));
        else
            MainActivity.leftDeviceConnectionManager.connect(CommonUtils.getMacAddress(deviceLeft));
    }

    String boxerName = null, boxerStance = null;

    public void endTrainingSession (String result){
        JSONObject json;

        try {
            json = new JSONObject(result);
            if (json.getString("success").equals("true")) {
//                MainActivity.leftDeviceConnectionManager.disconnect();
//                MainActivity.rightDeviceConnectionManager.disconnect();
//                rightHandConnectionThread = null;
//                leftHandConnectionThread = null;
//                stopTrainingTimer();
//                trainingSessionId = null;
            } else {
                Log.e(TAG, "Error in calling web service");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectSensors(){
        MainActivity.leftDeviceConnectionManager.disconnect();
        MainActivity.rightDeviceConnectionManager.disconnect();
        rightHandConnectionThread = null;
        leftHandConnectionThread = null;
    }

    public boolean stopTraining() {
        try {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            if (trainingSessionId != null) {
                JSONObject result_Training_session_end = db.trainingSessionEnd(trainingSessionId);
                endTrainingSession(result_Training_session_end.toString());
                trainingManager.stopTraining();
                liveMonitorDataMap.clear();
                punchDataDTO.resetValues(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void startTraining() {
        try {
            boxerName = null;
            boxerStance = null;
            JSONObject json = null;
            @SuppressWarnings("unused")
            Integer boxerIdValue;

            HashMap<String, String> checkboxerDetails = MainActivity.db.getBoxerDetails(Integer.valueOf(userId));
            if (checkboxerDetails != null) {
                boxerStance = checkboxerDetails.get("stance");
            }
            if (isGuestBoxerActive()) {
                boxerName = EFDConstants.GUEST_BOXER_USERNAME;
                boxerIdValue = EFDConstants.GUEST_BOXER_ID;
                trainingSessionId = EFDConstants.GUEST_TRAINING_SESSION_ID;
                trainingLeftHandId = EFDConstants.GUEST_TRAINING_DATA_LEFT_HAND_ID;
                trainingRightHandId = EFDConstants.GUEST_TRAINING_DATA_RIGHT_HAND_ID;
                boxerPunchMassEffect = EFDConstants.GUEST_TRAINING_EFFECTIVE_PUNCH_MASS;
            } else {
                JSONObject result = null;
                result = MainActivity.db.trainingSessionSave(Integer.parseInt(userId), EFDConstants.TRAINING_TYPE_BOXER);
                MainActivity.db.insertGymTrainingSession();
                boxerName = "WES";
                boxerStance = EFDConstants.TRADITIONAL;//checkboxerDetails.get("boxerName");
                json = new JSONObject(result.toString());
                JSONObject trainingData = json.getJSONObject("trainingData");
                trainingSessionId = trainingData.getInt("trainingSessionId");
                trainingRightHandId = trainingData.getInt("trainingRightHandId");
                trainingLeftHandId = trainingData.getInt("trainingLeftHandId");
                boxerIdValue = Integer.valueOf(userId);
                currentTrainingSessionId = trainingSessionId;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                HashMap<String, String> usersBoxerDetails = MainActivity.db.getUsersBoxerDetails(boxerIdValue);
                boxerPunchMassEffect = calculateBoxerPunchMassEffect(usersBoxerDetails);

                if (PunchDetectionConfig.getInstance().getPunchMassEff() != boxerPunchMassEffect) {
                    Log.d(TAG, "Resetting Punch mass effect = " + boxerPunchMassEffect + " (" + PunchDetectionConfig.getInstance().getPunchMassEff() + ")");
                    PunchDetectionConfig.getInstance().setPunchMassEff(boxerPunchMassEffect);
                }
            }

            if (leftHandConnectionThread != null) {
                setConnectionManagerConnected(leftHandConnectionThread, boxerName, boxerStance, trainingLeftHandId, EFDConstants.LEFT_HAND);
            }
            if (rightHandConnectionThread != null) {
                setConnectionManagerConnected(rightHandConnectionThread, boxerName, boxerStance, trainingRightHandId, EFDConstants.RIGHT_HAND);
            }

            EventBus.getDefault().post(new TrainingConnectStatusDTO(true, false, ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startTrainingTimer() {
        if (null == MainActivity.trainingDurationCounterThread) { // if timer is not initialised
            // Start timer
            MainActivity.trainingDurationCounterThread = new TrainingTimer(MainActivity.customHandler);
        }
    }

    public static String stopTrainingTimer() {
        String endTime = EFDConstants.DEFAULT_START_TIME;
        if (null != trainingDurationCounterThread) { // if timer is initialised
            // Stop timer
            trainingDurationCounterThread.stopTimer();
            endTime = trainingDurationCounterThread.getTimerTime();
            trainingDurationCounterThread = null;
        }
        return endTime;
    }

    public void startRoundTraining(){

        if (!trainingManager.isTrainingRunning()) {
            //set taskmanger traing value as true
            trainingManager.startTraining();

            //start training timer
            startTrainingTimer();

            //start training
            startTraining();
            Log.e("Super", "Start round training");
        }else {
            StatisticUtil.showToastMessage("Sensor is not connected  " + checkDeviceConnectionFlag);
        }
    }

    public void stopRoundTraining(){
        if (trainingManager.isTrainingRunning()) {
            trainingManager.stopTraining();
            stopTrainingTimer();
            trainingSessionId = null;
            punchHistoryGraph.clear();
            liveMonitorDataMap.clear();
            punchDataDTO.resetValues(0);

            endTrainingTime = EFDConstants.DEFAULT_START_TIME;
            MainActivity.db.endAllPreviousGymTrainingSessions();
        }
    }

    private void setConnectionManagerConnected(ConnectionThread connectionThread, String boxerName, String boxerStance, Integer trainingDataId, String boxerHand) {
        ConnectionManager connectionManager = connectionThread.getBluetoothConnectionManager();
        connectionManager.setBoxerName(boxerName);
        connectionManager.setBoxerStance(boxerStance);
        connectionManager.setTrainingDataId(trainingDataId);
        connectionManager.setBoxerHand(boxerHand);
        connectionManager.connected(connectionThread.getBluetoothSocket());
    }

    public ProgressDialog dialog;

    public class ShowLoaderTask extends AsyncTask<String, Integer, String> {
        Activity activity;
        public ShowLoaderTask(Activity activity){
            this.activity = activity;
        }
        @Override
        protected void onPreExecute() {

            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Please wait...");
            dialog.setTitle("Connecting With Device...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String sensor = params[0];
            if (sensor.equals("left"))
                startDeviceConnection(true);
            else
                startDeviceConnection(false);
            return "Done!";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
			/*
			 * if (dialog.isShowing()) dialog.dismiss();
			 */
        }
    } // end ShowLoaderTask



    public boolean isGuestBoxerActive() {
        return isGuestBoxerActive;
    }

    public void setGuestBoxerActive(boolean isGuestBoxerActive) {
        this.isGuestBoxerActive = isGuestBoxerActive;
    }

    /**
     * code to get stance value from DB and store in boxersStance variable
     */
    public void setBoxersStance() {
        HashMap<String, String> boxerDetails = MainActivity.db
                .getBoxerDetails(Integer.valueOf(userId));
        if (boxerDetails != null) {
            MainActivity.boxersStance = boxerDetails.get("stance");
        }
    }

    public double calculateBoxerPunchMassEffect(HashMap<String, String> boxerDetails) {

        double effectivePunchMass = EFDConstants.GUEST_TRAINING_EFFECTIVE_PUNCH_MASS;

        try {

            JSONObject user_info_jsonObj = MainActivity.db.trainingUserInfo(userId).getJSONObject("userInfo");

            String gender = user_info_jsonObj.getString("user_gender");

            if (boxerDetails != null) {

                // Putting this here for now. Just recalculate it when we need to use
                //    it instead of dealing with storing it (short term) ECH

                String weightCountValue = "150";
                String traineeSkillLevelValue = "Beginner";

                if (boxerDetails.containsKey(DBAdapter.KEY_BOXER_WEIGHT)) {
                    weightCountValue = boxerDetails.get(DBAdapter.KEY_BOXER_WEIGHT);
                }

                if (boxerDetails.containsKey(DBAdapter.KEY_BOXER_SKILL_LEVEL)) {
                    traineeSkillLevelValue = boxerDetails.get(DBAdapter.KEY_BOXER_SKILL_LEVEL);
                }

                if (StringUtils.isBlank(gender) && boxerDetails.containsKey(DBAdapter.KEY_USER_GENDER)) {
                    gender = boxerDetails.get(DBAdapter.KEY_USER_GENDER);
                }

                EffectivePunchMassCalculator calculator = new EffectivePunchMassCalculator();
                effectivePunchMass = calculator.calculatePunchMassEffect(weightCountValue, traineeSkillLevelValue, gender);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            effectivePunchMass = EFDConstants.GUEST_TRAINING_EFFECTIVE_PUNCH_MASS;
        }

        return effectivePunchMass;
    }

    public String getCurrentRightDevice() {
        return currentRightDevice;
    }

    public String getCurrentLeftDevice() {
        return currentLeftDevice;
    }

    public void setCurrentRightDevice(String deviceString) {
        currentRightDevice = deviceString;
    }

    public void setCurrentLeftDevice(String deviceString) {
        currentLeftDevice = deviceString;
    }


    @Override
    protected void onResume() {
        super.onResume();
        DBAdapter.clearDBAdapter();
        db = DBAdapter.getInstance(this);
    }

    void initUI(){
        final NavigationTabBar navigationTabBar;

        viewPager = (ViewPager) findViewById(R.id.navigation_viewpager);

        mAdapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.profile),
                        getResources().getColor(R.color.active_color_ntb))
                        .selectedIcon(getResources().getDrawable(R.drawable.profile_selected))
                        .title("PROFILE")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.training),
                        getResources().getColor(R.color.active_color_ntb))
                        .selectedIcon(getResources().getDrawable(R.drawable.training_selected))
                        .title("TRAINING")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.trainingstats),
                        getResources().getColor(R.color.active_color_ntb))
                        .selectedIcon(getResources().getDrawable(R.drawable.training_stats_selected))
                        .title("STATS")
                        .badgeTitle("state")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
//                navigationTabBar.getModels().get(position).hideBadge();
                Fragment fragment = (Fragment) mAdapter.instantiateItem(viewPager, position);
                if (fragment != null) {

                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 150);
    }

    private class CustomViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] titles = { "PROFILE", "TRAINING", "STATS" };
        private final FragmentManager mFragmentManager;

        public CustomViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            mFragmentManager = fragmentManager;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new TrainingFragment();
                case 2:
                    return new TrainingStatsFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTraining();
        disconnectSensors();
    }
}
