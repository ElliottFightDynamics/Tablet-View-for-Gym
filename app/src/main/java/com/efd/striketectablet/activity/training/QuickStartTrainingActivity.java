package com.efd.striketectablet.activity.training;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efd.striketectablet.DTO.PresetDTO;
import com.efd.striketectablet.DTO.PunchDTO;
import com.efd.striketectablet.DTO.PunchHistoryGraphDataDetails;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.customview.CurveChartView;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuickStartTrainingActivity extends BaseTrainingActivity {

    ImageView backBtn;
    CustomButton startTrainingBtn;
    public LinearLayout leftSensorConnectionLayout, rightSensorConnectionLayout;
    TextView punchTypeView, trainingProgressStatus;
    TextView speedValue, punchCountView, forceValue;
    TextView currentTimeView;
    ProgressBar progressBar;
    CurveChartView chartView;

    ImageView audioBtn;

    TextView avgSpeedView, avgForceView, maxSpeedView, maxForceView;
    RelativeLayout graphLayout, digitLayout;
    LinearLayout speedLayout, powerLayout;

    public ImageView leftSensorConnectionButton, rightSensorConnectionButton;
    TextView leftHandBattery, rightHandBattery;
    View leftHandBatteryView, rightHandBatteryView;


    PresetDTO presetDTO;
    Timer progressTimer;
    TimerTask updateProgressTimerTask;
    private Handler mHandler;

    private int currentStatus = -1;   //0: prepare, 1: round, 2: resting
    private int roundvalue = 0;
    private int totalTime = 0;
    private int currentTime = 0;

    boolean audioEnabled = true;

    private MediaPlayer bellplayer;
    private AudioManager audioManager;
    private boolean isSpeedGraph = false;

    private MainActivity mainActivityInstance;

    private ArrayList<PunchHistoryGraphDataDetails> punchLists;
    private ArrayList<PunchDTO> punchDTOs;

    private long trainingStartTime = 0L;
    private  long trainingEndTime = 0L;
    private float maxSpeed = 0f, avgSpeed = 0, maxForce = 0, avgForce = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_quickstart_training);
        punchLists = new ArrayList<>();
        punchDTOs = new ArrayList<>();

        mainActivityInstance = MainActivity.getInstance();

        presetDTO = (PresetDTO)getIntent().getSerializableExtra("preset");

        initViews();
    }

    private void initViews(){
        mHandler = new Handler();
        backBtn = (ImageView)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startTrainingBtn = (CustomButton)findViewById(R.id.training_start_button);
        leftHandBattery = (TextView) findViewById(R.id.left_sensor_battery);
        rightHandBattery = (TextView) findViewById(R.id.right_sensor_battery);
        leftHandBatteryView = findViewById(R.id.battery_life_left);
        rightHandBatteryView = findViewById(R.id.battery_life_right);

        leftSensorConnectionLayout = (LinearLayout)findViewById(R.id.left_sensor_connection_layout);
        rightSensorConnectionLayout = (LinearLayout)findViewById(R.id.right_sensor_connection_layout);

        leftSensorConnectionButton = (ImageView)findViewById(R.id.left_connection_button);
        rightSensorConnectionButton = (ImageView)findViewById(R.id.right_connection_button);

        punchTypeView = (TextView)findViewById(R.id.training_punchtype);
        trainingProgressStatus = (TextView)findViewById(R.id.trainingprogress_status);

        graphLayout = (RelativeLayout)findViewById(R.id.progress_graph);
        digitLayout = (RelativeLayout)findViewById(R.id.progress_digit);

        graphLayout.setVisibility(View.INVISIBLE);
        digitLayout.setVisibility(View.VISIBLE);

        progressBar = (ProgressBar)findViewById(R.id.trainingprogressbar);
        currentTimeView = (TextView)findViewById(R.id.trainingprogress_time);

        avgSpeedView = (TextView)findViewById(R.id.training_avg_speed);
        avgForceView = (TextView)findViewById(R.id.training_avg_power);
        maxSpeedView = (TextView)findViewById(R.id.training_max_speed);
        maxForceView = (TextView)findViewById(R.id.training_max_force);

        chartView = (CurveChartView)findViewById(R.id.curvechart);
        chartView.setMaxXAxisValue(Integer.parseInt(PresetUtil.timerwitSecsList.get(presetDTO.getRound_time())) * 1000);

        speedLayout = (LinearLayout)findViewById(R.id.training_speedlayout);
        powerLayout = (LinearLayout)findViewById(R.id.training_powerlayout);

        speedValue = (TextView)findViewById(R.id.speed_value);
        punchCountView = (TextView)findViewById(R.id.punch_value);
        forceValue = (TextView)findViewById(R.id.power_value);


        speedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSpeedGraph){
                    isSpeedGraph = true;
//                    chartView.setIsSpeed(isSpeedGraph);
                    chartView.changeGraph(isSpeedGraph);
                }
            }
        });

        powerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSpeedGraph){
                    isSpeedGraph = false;
//                    chartView.setIsSpeed(isSpeedGraph);
                    chartView.changeGraph(isSpeedGraph);
                }
            }
        });

        audioBtn = (ImageView)findViewById(R.id.audiobtn);
        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioEnabled){
                    audioBtn.setImageResource(R.drawable.audio_off);
                    audioEnabled = false;
                }else {
                    audioBtn.setImageResource(R.drawable.audio_on);
                    audioEnabled = true;
                }
            }
        });

        startTrainingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startTrainingBtn.getText().toString().equals(getResources().getString(R.string.start_training))){

                    startTraining();
                }else {
                    startTrainingBtn.setText(getResources().getString(R.string.start_training));
                    stopTraining();
                }
            }
        });

        leftSensorConnectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivityInstance.trainingManager.isTrainingRunning())
                    MainActivity.getInstance().reconnectSensor(v);
                else
                    connectSensor(true);
            }
        });

        rightSensorConnectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivityInstance.trainingManager.isTrainingRunning())
                    MainActivity.getInstance().reconnectSensor(v);
                else
                    connectSensor(false);
            }
        });


        resetConnectDeviceBg("right");
        resetConnectDeviceBg("left");

        setDeviceConnectionState();

        if (bellplayer == null)
            bellplayer = MediaPlayer.create(this, R.raw.boxing_bell);

        resetPunchDetails();

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBatteryVoltage(MainActivity.leftHandBatteryVoltage);
                setBatteryVoltage(MainActivity.rightHandBatteryVoltage);
            }
        }, 10);
    }

    private void connectSensor(boolean isLeft){
            if (mainActivityInstance.deviceLeft.equals(EFDConstants.BLANK_TEXT) && mainActivityInstance.deviceRight.equals(EFDConstants.BLANK_TEXT)) {
                Toast.makeText(QuickStartTrainingActivity.this, EFDConstants.DEVICE_ID_MUST_NOT_BE_BLANK_ERROR, Toast.LENGTH_SHORT).show();
            } else if (mainActivityInstance.deviceLeft.equalsIgnoreCase(mainActivityInstance.deviceRight)) {
                Toast.makeText(QuickStartTrainingActivity.this, EFDConstants.DEVICE_ID_MUST_NOT_BE_SAME, Toast.LENGTH_SHORT).show();
            } else {
                //code to clear punch history details after we have restarted a training
                mainActivityInstance.punchDataDTO.resetValues(0);
                mainActivityInstance.liveMonitorDataMap.clear();
                mainActivityInstance.punchHistoryGraph.clear();
                mainActivityInstance.endTrainingTime = EFDConstants.DEFAULT_START_TIME;

                if ((mainActivityInstance.deviceLeft != null || mainActivityInstance.deviceRight != null) && (!EFDConstants.BLANK_TEXT.equals(mainActivityInstance.deviceLeft) || !EFDConstants.BLANK_TEXT.equals(mainActivityInstance.deviceRight))) {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, MainActivity.REQUEST_ENABLE_BT);
                    }

                    if (isLeft)
                        mainActivityInstance.new ShowLoaderTask(this).execute("left");
                    else
                        mainActivityInstance.new ShowLoaderTask(this).execute("right");
                }
            }

    }

    public void resetPunchDetails(){
        if (punchLists != null && punchLists.size() > 0)
            punchLists.clear();

        if (punchDTOs != null && punchDTOs.size() > 0)
            punchDTOs.clear();

        punchTypeView.setText("");

        speedValue.setText("0");
        punchCountView.setText("0");
        forceValue.setText("0");

        avgSpeedView.setText("0");
        avgForceView.setText("0");
        maxForceView.setText("0");
        maxSpeedView.setText("0");

        maxForce = 0f;
        avgForce = 0f;
        maxSpeed = 0f;
        avgSpeed = 0f;
    }

    private void playBoxingBell(){
        if (audioEnabled) {
            int volume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            bellplayer.start();
//            if (volume != 0 && bellplayer != null)
        }
    }

    private void startTraining(){

//        if (!mainActivityInstance.leftSensorConnected && !mainActivityInstance.leftSensorConnected){
//            StatisticUtil.showToastMessage("Please connect with sensors");
//            return;
//        }

        if (currentStatus == -1){
            //prepare for round 1
            startTrainingBtn.setText(getResources().getString(R.string.stop_training));
            currentStatus = 0;
            roundvalue = 1;
            totalTime = 10; //Integer.parseInt(PresetUtil.timerwitSecsList.get(presetDTO.getPrepare()));
            currentTime = totalTime;
            trainingProgressStatus.setText("PREPARE");
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_preparebar));
            trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_prepare));
            isSpeedGraph = false;

            graphLayout.setVisibility(View.INVISIBLE);
            digitLayout.setVisibility(View.VISIBLE);
        }else {
            if (currentStatus == 0){
                //current is prepare
                startTrainingBtn.setText(getResources().getString(R.string.stop_training));
            }else if (currentStatus == 1){

                totalTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(presetDTO.getRest()));
                currentTime = totalTime;
                trainingProgressStatus.setText("REST");
                currentStatus++;
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_restbar));
                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_rest));

                startTrainingBtn.setText(getResources().getString(R.string.stop_training));

                graphLayout.setVisibility(View.INVISIBLE);
                digitLayout.setVisibility(View.VISIBLE);

//                String text = PresetUtil.chagngeSecsToTime(currentTime) + " - STOP";
//                startTrainingBtn.setText(text);

            }else if (currentStatus == 2){
                //current is rest
                startTrainingBtn.setText(getResources().getString(R.string.stop_training));
            }
        }

        progressBar.setMax(totalTime);
        progressBar.setProgress(totalTime - currentTime);

        startProgressTimer();
    }

    private void stopTraining(){
        Log.e("Super", "Stop Training");
        stopProgressTimer();

        mainActivityInstance.stopRoundTraining();
        mainActivityInstance.stopTraining();

//        if (mainActivityInstance.trainingManager.isTrainingRunning()) {
//            JSONObject result_Training_session_end = MainActivity.db.trainingSessionEnd(mainActivityInstance.trainingSessionId);
//            endTrainingSession(result_Training_session_end.toString());
//
//            // extract summary upon ending training session
//            mainActivityInstance.createOrUpdateSummaryTables();
//        }
    }

    public void startProgressTimer (){
        progressTimer = new Timer();
        initializeProgressTimerTask();
        progressTimer.schedule(updateProgressTimerTask, 0, 1000);
    }

    public void stopProgressTimer (){
        if (progressTimer != null){
            progressTimer.cancel();
            progressTimer = null;
        }
    }

    public void initializeProgressTimerTask (){
        updateProgressTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentTime == 0){
                            if (currentStatus == 0){
                                //prepare is finished, go to round
                                totalTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(presetDTO.getRound_time()));
                                currentTime = totalTime;
                                trainingProgressStatus.setText("ROUND " + roundvalue);
                                currentStatus++;
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_roundbar));
                                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_round));

                                //round is starting
                                playBoxingBell();

                                chartView.setPunchDatas(isSpeedGraph, new ArrayList());

                                graphLayout.setVisibility(View.VISIBLE);
                                digitLayout.setVisibility(View.INVISIBLE);

                                String text = PresetUtil.chagngeSecsToTime(currentTime) + " - STOP";
                                startTrainingBtn.setText(text);

                                trainingStartTime = System.currentTimeMillis();
                                resetPunchDetails();
                                mainActivityInstance.startRoundTraining();
                            }
                            else if (currentStatus == 1){
                                //round is finished, go to rest
                                totalTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(presetDTO.getRest()));
                                currentTime = totalTime;
                                trainingProgressStatus.setText("REST");
                                currentStatus++;
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_restbar));
                                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_rest));

                                startTrainingBtn.setText(getResources().getString(R.string.stop_training));

                                //round is finished
                                playBoxingBell();
                                graphLayout.setVisibility(View.INVISIBLE);
                                digitLayout.setVisibility(View.VISIBLE);
                                punchTypeView.setText("");
                                mainActivityInstance.stopRoundTraining();

                            }else if (currentStatus == 2){
                                currentStatus++;
                                if (roundvalue == Integer.parseInt(PresetUtil.roundsList.get(presetDTO.getRounds()))){
                                    //training is finished
                                    Toast.makeText(QuickStartTrainingActivity.this, "Training is finished", Toast.LENGTH_SHORT).show();
                                    currentStatus = -1;
                                    startTrainingBtn.setText(getResources().getString(R.string.start_training));
                                    trainingProgressStatus.setText("");

                                    stopProgressTimer();

                                    mainActivityInstance.stopRoundTraining();
                                }else {
                                    roundvalue++;
                                    totalTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(presetDTO.getRound_time()));
                                    currentTime = totalTime;
                                    currentStatus = 1;
                                    String text = PresetUtil.chagngeSecsToTime(currentTime) + " - STOP";
                                    startTrainingBtn.setText(text);

                                    trainingProgressStatus.setText("ROUND " + roundvalue);
                                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_roundbar));
                                    trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_round));
                                    chartView.setPunchDatas(isSpeedGraph, new ArrayList());
                                    playBoxingBell();
                                    trainingStartTime = System.currentTimeMillis();
                                    resetPunchDetails();
                                    mainActivityInstance.startRoundTraining();
                                    graphLayout.setVisibility(View.VISIBLE);
                                    digitLayout.setVisibility(View.INVISIBLE);
                                }
                            }

                            progressBar.setProgress(0);
                            progressBar.setMax(totalTime);
                            currentTimeView.setText(PresetUtil.chagngeSecsToTime(currentTime));
                        }else {
                            currentTime--;
                            if (currentStatus == 1 && currentTime == Integer.parseInt(PresetUtil.warningList.get(presetDTO.getWarning()))){
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_warningbar));
                                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_warning));
                            }

                            if (currentStatus == 1){
                                if (currentTime == 0 || currentTime > totalTime - 2){

                                }else {
                                    chartView.setPunchDatas(isSpeedGraph, punchDTOs);
                                }

                                String text = PresetUtil.chagngeSecsToTime(currentTime) + " - STOP";
                                startTrainingBtn.setText(text);
                            }

                            progressBar.setProgress(totalTime - currentTime);
                            currentTimeView.setText(PresetUtil.chagngeSecsToTime(currentTime));
                        }
                    }
                });
            }
        };
    }

    public void endTrainingSession(String result) {

        JSONObject json;
        try {
            json = new JSONObject(result);
            if (json.getString("success").equals("true")) {
                MainActivity.leftDeviceConnectionManager.disconnect();
                MainActivity.rightDeviceConnectionManager.disconnect();
                // stop training timer
                mainActivityInstance.endTrainingTime = MainActivity.stopTrainingTimer();
                resetConnectDeviceBg("right");
                resetConnectDeviceBg("left");
                MainActivity.leftHandBatteryVoltage = "";
                MainActivity.rightHandBatteryVoltage = "";
                mainActivityInstance.trainingSessionId = null;
            } else {
                Log.e("BagTrainingFragment ", "Error in calling web service");
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopProgressTimer();
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.left_center, R.anim.center_right);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void receivedPunchData(PunchHistoryGraphDataDetails details) {
        punchLists.add(details);

        PunchDTO punchDTO = new PunchDTO();
        punchDTO.setTime((int)(System.currentTimeMillis() - trainingStartTime));
        int currentSpeed = Integer.parseInt(details.punchSpeed);
        int currentForce = Integer.parseInt(details.punchForce);
        punchDTO.setPower(currentForce);
        punchDTO.setSpeed(currentSpeed);

        speedValue.setText(details.punchSpeed);
        forceValue.setText(details.punchForce);

        maxForce = Math.max(maxForce, (float)currentForce);
        maxSpeed = Math.max(maxSpeed, (float) currentSpeed);

        int punchCount = punchDTOs.size();
        avgForce = (avgForce * punchCount + currentForce) / (punchCount + 1);
        avgSpeed = (avgSpeed * punchCount + currentSpeed) / (punchCount + 1);

        punchDTOs.add(punchDTO);
        punchCountView.setText(String.valueOf(punchDTOs.size()));

        avgSpeedView.setText(String.valueOf((int)avgSpeed));
        avgForceView.setText(String.valueOf((int)avgForce));
        maxSpeedView.setText(String.valueOf((int)maxSpeed));
        maxForceView.setText(String.valueOf((int)maxForce));

        String hand = details.boxersHand.equalsIgnoreCase("L") ? "LEFT" : "RIGHT";
        String punchType = details.punchType;

        if (punchType.equalsIgnoreCase(EFDConstants.JAB_ABBREVIATION_TEXT)) {
            setPunchTypeText(hand, EFDConstants.JAB);
        } else if (punchType.equalsIgnoreCase(EFDConstants.HOOK_ABBREVIATION_TEXT)) {
            setPunchTypeText(hand, EFDConstants.HOOK);
        } else if (punchType.equalsIgnoreCase(EFDConstants.STRAIGHT_ABBREVIATION_TEXT)) {
            setPunchTypeText(hand, EFDConstants.STRAIGHT);
        } else if (punchType.equalsIgnoreCase(EFDConstants.UPPERCUT_ABBREVIATION_TEXT)) {
            setPunchTypeText(hand, EFDConstants.UPPERCUT);
        } else if (punchType.equalsIgnoreCase(EFDConstants.UNRECOGNIZED_ABBREVIATION_TEXT)) {
            setPunchTypeText(hand, EFDConstants.UNRECOGNIZED);
        }
    }

    private void setPunchTypeText(String hand, String punchType) {
        punchTypeView.setText(hand + " " + punchType);
    }

    @Override
    public void setDeviceConnectionState() {
        if (mainActivityInstance.leftDeviceConnectionManager != null && mainActivityInstance.leftDeviceConnectionManager.readerThread != null) {
            leftSensorConnectionButton.setImageResource(R.drawable.green_btn);
            leftSensorConnectionLayout.setEnabled(false);
        }

        if (mainActivityInstance.rightDeviceConnectionManager != null && mainActivityInstance.rightDeviceConnectionManager.readerThread != null) {
            rightSensorConnectionButton.setImageResource(R.drawable.green_btn);
            rightSensorConnectionLayout.setEnabled(false);
        }
    }

    @Override
    public void setBatteryVoltage(String batteryVoltage) {
        if (!batteryVoltage.equals("")) {
            try {
                JSONObject batteryJson = new JSONObject(batteryVoltage);
                boolean tabletSize = getResources().getBoolean(R.bool.iTablet);
                if (batteryJson.getString("hand").equals("left")) {
                    leftHandBattery.setText(batteryJson.getString("batteryVoltage") + "%");
                    View parent = (View) leftHandBatteryView.getParent();
                    int width = parent.getWidth();
                    int childViewWidth = (width * Integer.parseInt(batteryJson.getString("batteryVoltage"))) / 100;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(childViewWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0, 0, (tabletSize ? 6 : 4), 0);
                    leftHandBatteryView.setLayoutParams(params);
                } else {
                    rightHandBattery.setText(batteryJson.getString("batteryVoltage") + "%");
                    View parent = (View) rightHandBatteryView.getParent();
                    int width = parent.getWidth();
                    int childViewWidth = (width * Integer.parseInt(batteryJson.getString("batteryVoltage"))) / 100;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(childViewWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0, 0, (tabletSize ? 6 : 4), 0);
                    rightHandBatteryView.setLayoutParams(params);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleBatteryLayoutClickable(boolean isLeft, boolean clickable) {
        if (isLeft){
            if (clickable){
                leftSensorConnectionButton.setImageResource(R.drawable.red_btn);
                leftSensorConnectionLayout.setEnabled(true);
            }else {
                leftSensorConnectionButton.setImageResource(R.drawable.green_btn);
                leftSensorConnectionLayout.setEnabled(false);
            }
        }else {
            if (clickable){
                rightSensorConnectionButton.setImageResource(R.drawable.red_btn);
                rightSensorConnectionLayout.setEnabled(true);
            }else {
                rightSensorConnectionButton.setImageResource(R.drawable.green_btn);
                rightSensorConnectionLayout.setEnabled(false);
            }
        }
    }

    @Override
    public void resetConnectDeviceBg(String hand) {

        if (hand.equals("right")) {
            rightSensorConnectionButton.setImageResource(R.drawable.red_btn);
            rightSensorConnectionLayout.setEnabled(true);
        } else {
            leftSensorConnectionButton.setImageResource(R.drawable.red_btn);
            leftSensorConnectionLayout.setEnabled(true);
        }
    }

    @Override
    public void resetBatteryVoltage(String hand) {
        if (hand.equals("left")) {
            leftHandBattery.setText("0%");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            leftHandBatteryView.setLayoutParams(params);
        } else {
            rightHandBattery.setText("0%");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            rightHandBatteryView.setLayoutParams(params);
        }
    }
}
