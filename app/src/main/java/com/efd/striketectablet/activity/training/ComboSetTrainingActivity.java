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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.PunchDTO;
import com.efd.striketectablet.DTO.PunchHistoryGraphDataDetails;
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.DTO.TrainingResultComboDTO;
import com.efd.striketectablet.DTO.TrainingResultPunchDTO;
import com.efd.striketectablet.DTO.TrainingResultSetDTO;
import com.efd.striketectablet.DTO.TrainingResultWorkoutDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.training.round.RoundTrainingActivity;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomTextView;
import com.efd.striketectablet.customview.CustomTextViewFontEfDigit;
import com.efd.striketectablet.util.ComboSetUtil;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.EFDConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ComboSetTrainingActivity extends BaseTrainingActivity {

    ImageView backBtn;
    TextView titleView;
    CustomButton startTrainingBtn;
    LinearLayout leftSensorConnectionLayout, rightSensorConnectionLayout;
    CustomTextView punchTypeView, trainingProgressStatus;


    CustomTextViewFontEfDigit currentTimeView;
    ProgressBar progressBar;
    RelativeLayout timerLayout;

    LinearLayout progressView;
    LinearLayout comboNumParent;
    LinearLayout comboResultParent;
    LinearLayout nextcomboTip;
    TextView nextComboTipContent;

    ImageView audioBtn;

    TextView avgSpeedView, avgForceView, maxSpeedView, maxForceView;
    TextView leftavgSpeedView, leftavgForceView, leftmaxSpeedView, leftmaxForceView;
    TextView rightavgSpeedView, rightavgForceView, rightmaxSpeedView, rightmaxForceView;
    TextView speedValue, punchCountView, forceValue;

    public ImageView leftSensorConnectionButton, rightSensorConnectionButton;
    CustomTextView leftHandBattery, rightHandBattery;
    View leftHandBatteryView, rightHandBatteryView;

    Timer progressCombosetTimer, progressWorkoutTimer;
    TimerTask updateProgressComboSetTimerTask, updateProgressWorkoutTimerTask;
    private Handler mHandler;

    private int currentStatus = -1;   //0: prepare, 1: round, 2: resting
    private int roundvalue = 0;
    private int totalTime = 0;

    private int roundCount = 0;
    private int roundTime = 0;
    private int warningTime = 0;
    private int prepareTime = 0;
    private int restTime = 0;

    private int currentTime = 0;
    private int currentProgressTime = 0;

    boolean audioEnabled = true;

    private MediaPlayer bellplayer;
    private AudioManager audioManager;

    private MainActivity mainActivityInstance;

    private ArrayList<PunchHistoryGraphDataDetails> punchLists;
    private ArrayList<PunchDTO> punchDTOs;
    private ArrayList<PunchDTO> leftpunchDTOS, rightpunchDTOS;

    private long trainingStartTime = 0L;
    private  long trainingEndTime = 0L;
    private float maxSpeed = 0f, avgSpeed = 0, maxForce = 0, avgForce = 0;
    private float leftmaxSpeed = 0f, leftavgSpeed = 0, leftmaxForce = 0, leftavgForce = 0;
    private float rightmaxSpeed = 0f, rightavgSpeed = 0, rightmaxForce = 0, rightavgForce = 0;

    private String trainingtype;
    private int comboid = -1, setid = -1;//, workoutid = -1;

    private ComboDTO currentComboDTO;
    private SetsDTO currentSetDTO;
    private WorkoutDTO workoutDTO;

    private int maxview = 0;
    private int currentPunchIndex = 0;
    private int currentComboIndex = 0;
    private int currentSetIndex = 0;

    private TrainingResultComboDTO resultComboDTO;
    private TrainingResultSetDTO resultSetDTO;
    private TrainingResultWorkoutDTO resultWorkoutDTO;

    private ArrayList<TrainingResultPunchDTO> resultPunchList;
    private ArrayList<TrainingResultComboDTO>  resultComboList;
    private ArrayList<TrainingResultSetDTO>  resultRoundList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comboset_training);

        trainingtype = getIntent().getStringExtra(EFDConstants.TRAININGTYPE);
        if (trainingtype.equalsIgnoreCase(EFDConstants.COMBINATION)){
            comboid = getIntent().getIntExtra(EFDConstants.COMBO_ID, -1);
        }else if (trainingtype.equalsIgnoreCase(EFDConstants.SETS)){
            setid = getIntent().getIntExtra(EFDConstants.SET_ID, -1);
        }else if(trainingtype.equalsIgnoreCase(EFDConstants.WORKOUT)){
            workoutDTO = (WorkoutDTO)getIntent().getSerializableExtra(EFDConstants.WORKOUT_ID);
        }

        punchLists = new ArrayList<>();
        punchDTOs = new ArrayList<>();
        leftpunchDTOS = new ArrayList<>();
        rightpunchDTOS = new ArrayList<>();

        resultPunchList = new ArrayList<>();
        resultComboList = new ArrayList<>();
        resultRoundList = new ArrayList<>();

        mainActivityInstance = MainActivity.getInstance();

        initViews();
    }

    private void initViews(){
        mHandler = new Handler();
        titleView = (TextView)findViewById(R.id.title);
        backBtn = (ImageView)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startTrainingBtn = (CustomButton)findViewById(R.id.training_start_button);

        leftSensorConnectionLayout = (LinearLayout)findViewById(R.id.left_sensor_connection_layout);
        rightSensorConnectionLayout = (LinearLayout)findViewById(R.id.right_sensor_connection_layout);
        leftHandBattery = (CustomTextView) findViewById(R.id.left_sensor_battery);
        rightHandBattery = (CustomTextView) findViewById(R.id.right_sensor_battery);
        leftHandBatteryView = findViewById(R.id.battery_life_left);
        rightHandBatteryView = findViewById(R.id.battery_life_right);
        leftSensorConnectionButton = (ImageView)findViewById(R.id.left_connection_button);
        rightSensorConnectionButton = (ImageView)findViewById(R.id.right_connection_button);

        progressBar = (ProgressBar)findViewById(R.id.trainingprogressbar);
        punchTypeView = (CustomTextView)findViewById(R.id.training_punchtype);
        trainingProgressStatus = (CustomTextView)findViewById(R.id.trainingprogress_status);
        timerLayout = (RelativeLayout)findViewById(R.id.timer_layout);
        currentTimeView = (CustomTextViewFontEfDigit)findViewById(R.id.trainingprogress_time);
        progressView = (LinearLayout)findViewById(R.id.progress_view);
        comboNumParent = (LinearLayout)findViewById(R.id.punch_type_parent);
        comboResultParent = (LinearLayout)findViewById(R.id.punch_result_parent);
        progressView.setVisibility(View.VISIBLE);

        nextcomboTip = (LinearLayout)findViewById(R.id.next_combo_tip);
        nextComboTipContent = (TextView)findViewById(R.id.next_combo);
        nextcomboTip.setVisibility(View.INVISIBLE);

        speedValue = (TextView)findViewById(R.id.speed_value);
        punchCountView = (TextView)findViewById(R.id.punch_value);
        forceValue = (TextView)findViewById(R.id.power_value);

        avgSpeedView = (TextView)findViewById(R.id.training_avg_speed);
        avgForceView = (TextView)findViewById(R.id.training_avg_power);
        maxSpeedView = (TextView)findViewById(R.id.training_max_speed);
        maxForceView = (TextView)findViewById(R.id.training_max_force);

        leftavgSpeedView = (TextView)findViewById(R.id.lh_avg_speed);
        leftavgForceView = (TextView)findViewById(R.id.lh_avg_force);
        leftmaxSpeedView = (TextView)findViewById(R.id.lh_max_speed);
        leftmaxForceView = (TextView)findViewById(R.id.lh_max_force);

        rightavgSpeedView = (TextView)findViewById(R.id.rh_avg_speed);
        rightavgForceView = (TextView)findViewById(R.id.rh_avg_force);
        rightmaxSpeedView = (TextView)findViewById(R.id.rh_max_speed);
        rightmaxForceView = (TextView)findViewById(R.id.rh_max_force);

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
//                tmpStart();
                if (startTrainingBtn.getText().toString().equals(getResources().getString(R.string.start_training))){
                    startTraining();
                }else {
                    startTrainingBtn.setText(getResources().getString(R.string.start_training));
                    stopTraining();
                }
            }
        });

        if (bellplayer == null)
            bellplayer = MediaPlayer.create(this, R.raw.boxing_bell);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

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
        resetPunchDetails();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setBatteryVoltage(MainActivity.leftHandBatteryVoltage);
                setBatteryVoltage(MainActivity.rightHandBatteryVoltage);
            }
        }, 10);
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

        leftavgSpeedView.setText("0");
        leftavgForceView.setText("0");
        leftmaxForceView.setText("0");
        leftmaxSpeedView.setText("0");

        rightavgSpeedView.setText("0");
        rightavgForceView.setText("0");
        rightmaxForceView.setText("0");
        rightmaxSpeedView.setText("0");

        maxForce = 0f;
        avgForce = 0f;
        maxSpeed = 0f;
        avgSpeed = 0f;

        leftmaxForce = 0f;
        leftmaxSpeed = 0f;
        leftavgForce = 0f;
        leftavgSpeed = 0f;

        rightmaxForce = 0f;
        rightmaxSpeed = 0f;
        rightavgForce = 0f;
        rightavgSpeed = 0f;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfo();
    }

    private void setInfo(){
        if (comboid != -1){
            titleView.setText(getResources().getString(R.string.title_combination));
            currentComboDTO = ComboSetUtil.getComboDtowithID(comboid);
            currentTime = 0;
            initComboTrainingView();

            nextcomboTip.setVisibility(View.INVISIBLE);
            timerLayout.setVisibility(View.INVISIBLE);

        }else if (setid != -1){
            currentTime = 0;
            titleView.setText(getResources().getString(R.string.title_sets));
            currentSetDTO = ComboSetUtil.getSetDtowithID(setid);
            currentComboIndex = 0;
            currentComboDTO = ComboSetUtil.getComboDtowithID(currentSetDTO.getComboIDLists().get(0));
            initComboTrainingView();
            nextcomboTip.setVisibility(View.INVISIBLE);
            timerLayout.setVisibility(View.INVISIBLE);
        }else if (workoutDTO != null){
            currentTime = 0;
            titleView.setText(getResources().getString(R.string.title_workout));
//            workoutDTO = ComboSetUtil.getWorkoutDtoWithID(workoutid);
            currentComboIndex = 0;
            currentComboDTO = ComboSetUtil.getComboDtowithID(workoutDTO.getRoundsetIDs().get(0).get(0));
            roundCount = workoutDTO.getRoundcount();
            roundTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(workoutDTO.getRound()));
            warningTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(workoutDTO.getWarning()));
            prepareTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(workoutDTO.getPrepare()));
            restTime = Integer.parseInt(PresetUtil.timerwitSecsList.get(workoutDTO.getRest()));

            progressView.setVisibility(View.INVISIBLE);
            nextcomboTip.setVisibility(View.INVISIBLE);
            timerLayout.setVisibility(View.VISIBLE);

            initComboTrainingView();
            //startProgressWorkoutTimer();
        }else {
            titleView.setText("TRAINING");
        }
    }

    private void initComboTrainingView(){
        currentPunchIndex = 0;
        comboResultParent.removeAllViews();
        comboNumParent.removeAllViews();

        maxview = EFDConstants.MAX_NUM_FORPUNCH;//Math.max(EFDConstants.MAX_NUM_FORPUNCH, comboDTO.getComboTypes().size());

        for (int i = 0; i < maxview; i++){
            addPunchNumView(i);
        }

        for (int i = 0; i < currentComboDTO.getComboTypes().size(); i++){
            addPunchResultView(i, currentComboDTO.getComboTypes().get(i));
        }

        //first 3 views has to be invisible
//        int min = Math.min(4, currentComboDTO.getComboTypes().size());

        int min = Math.max(0, 3 - currentPunchIndex );
        int max = Math.min(7, currentComboDTO.getComboTypes().size() - currentPunchIndex + 3);

        for (int i = 0; i < maxview; i++){
            LinearLayout child = (LinearLayout)comboNumParent.getChildAt(i);
            TextView keyView = (TextView)child.findViewById(R.id.key);

            if (i < min){
                child.setVisibility(View.INVISIBLE);
            }else if (i < max){
                child.setVisibility(View.VISIBLE);
                keyView.setText(currentComboDTO.getComboTypes().get(currentPunchIndex + i - 3));

                if (i == max - 1){
                    View divider = child.findViewById(R.id.combo_divider);
                    divider.setVisibility(View.INVISIBLE);
                }
            }else {
                child.setVisibility(View.INVISIBLE);
            }
        }

//        for (int i = 0; i < maxview; i++){
//            if (i < 3){
//                comboNumParent.getChildAt(i).setVisibility(View.INVISIBLE);
//            }else if (i < 3 + min){
//                LinearLayout child = (LinearLayout)comboNumParent.getChildAt(i);
//                TextView keyView = (TextView)child.findViewById(R.id.key);
//                keyView.setText(currentComboDTO.getComboTypes().get(i - 3));
//            }else {
//                comboNumParent.getChildAt(i).setVisibility(View.INVISIBLE);
//            }
//        }

        if (workoutDTO == null) {
            trainingProgressStatus.setText(ComboSetUtil.punchTypeMap.get(currentComboDTO.getComboTypes().get(0)));
            trainingProgressStatus.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void addPunchResultView(int position, String key){
        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_training_punchresult, null);
        TextView keyView = (TextView)newLayout.findViewById(R.id.key);

        if (position == 0){
            keyView.setBackgroundResource(R.drawable.next_punch_first);
            keyView.setTextColor(getResources().getColor(R.color.white));
        }else {
            keyView.setBackgroundResource(R.drawable.punchkey_bg_later);
        }

        keyView.setText(key);

        comboResultParent.addView(newLayout, position);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)newLayout.getLayoutParams();
        params.leftMargin = StatisticUtil.dpTopx(-3);
        newLayout.setLayoutParams(params);
    }

    private void addPunchNumView(int position){
        final LinearLayout newLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.item_training_punchkey, null);
        TextView keyView = (TextView)newLayout.findViewById(R.id.key);
        View comboDivider = newLayout.findViewById(R.id.combo_divider);

        keyView.setAlpha(1 - (float)(Math.abs(3 - position) * 0.32));
        comboDivider.setAlpha(0.25f);
        comboNumParent.addView(newLayout, position);

        if (position == 3){
            keyView.setTextSize(115);
        }else {
            keyView.setTextSize(80);
        }

        if (position == maxview - 1){
            comboDivider.setVisibility(View.GONE);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        newLayout.setLayoutParams(params);
    }

    int temp = 0;
    private void tmpStart(){
        if (mainActivityInstance.trainingManager.isTrainingRunning()) {
            temp++;
            if (temp % 2 == 0) {
                updateView(true);
            } else {
                updateView(false);
            }
        }
    }

    private void gotoNextCombo(){
        currentPunchIndex  = 0;
        int currentTrainingComboCount = 0;

        if (setid != -1){
            currentTrainingComboCount = currentSetDTO.getComboIDLists().size();
        }else if (workoutDTO != null){
            currentTrainingComboCount = workoutDTO.getRoundsetIDs().get(roundvalue - 1).size();
        }

        if (currentComboIndex == currentTrainingComboCount - 1){
            if (setid != -1) {
                playBoxingBell();
                resultSetDTO = new TrainingResultSetDTO(currentSetDTO.getName(), resultComboList, String.valueOf(System.currentTimeMillis()));
                mainActivityInstance.receivePunchable = false;
                stopTraining();
            }else {
                resultSetDTO = new TrainingResultSetDTO("ROUND " + (roundvalue), resultComboList, String.valueOf(System.currentTimeMillis()));
                resultRoundList.add(resultSetDTO);
                mainActivityInstance.receivePunchable = false;
            }
        }else {
            nextcomboTip.setVisibility(View.VISIBLE);
            progressView.setVisibility(View.INVISIBLE);

            if (setid != -1){
                ComboDTO comboDTO = ComboSetUtil.getComboDtowithID(currentSetDTO.getComboIDLists().get(currentComboIndex + 1));
                nextComboTipContent.setText(comboDTO.getCombos());

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextcomboTip.setVisibility(View.INVISIBLE);
                        currentComboIndex ++;
                        currentComboDTO = ComboSetUtil.getComboDtowithID(currentSetDTO.getComboIDLists().get(currentComboIndex));
                        progressView.setVisibility(View.VISIBLE);
                        mainActivityInstance.receivePunchable = true;
                        initComboTrainingView();
                    }
                }, 1000);

            }else if (workoutDTO != null){
                ComboDTO comboDTO = ComboSetUtil.getComboDtowithID(workoutDTO.getRoundsetIDs().get(roundvalue - 1).get(currentComboIndex + 1));
                nextComboTipContent.setText(comboDTO.getCombos());

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextcomboTip.setVisibility(View.INVISIBLE);
                        currentComboIndex ++;
                        currentComboDTO = ComboSetUtil.getComboDtowithID(workoutDTO.getRoundsetIDs().get(roundvalue - 1).get(currentComboIndex));
                        progressView.setVisibility(View.VISIBLE);
                        mainActivityInstance.receivePunchable = true;
                        initComboTrainingView();
                    }
                }, 1000);
            }
        }
    }

    private void updateView(boolean success){

        if (currentPunchIndex == currentComboDTO.getComboTypes().size() - 1){

            //update num view
            LinearLayout child = (LinearLayout)comboNumParent.getChildAt(3);
            TextView keyView = (TextView)child.findViewById(R.id.key);
            keyView.setTextSize(80);

            LinearLayout resultChild = (LinearLayout)comboResultParent.getChildAt(currentPunchIndex);
            TextView resultkeyView = (TextView)resultChild.findViewById(R.id.key);

            //update result view

            if (currentPunchIndex == 0){
                if (success)
                    resultkeyView.setBackgroundResource(R.drawable.punch_success_first);
                else
                    resultkeyView.setBackgroundResource(R.drawable.punch_fail_first);
            }else {
                if (success)
                    resultkeyView.setBackgroundResource(R.drawable.punch_success_next);
                else
                    resultkeyView.setBackgroundResource(R.drawable.punch_fail_next);
            }

            resultkeyView.setTextColor(getResources().getColor(R.color.black));

            //training is finished,
            if (comboid != -1) {
                playBoxingBell();
                resultComboDTO = new TrainingResultComboDTO(currentComboDTO.getName(), resultPunchList, String.valueOf(System.currentTimeMillis()) );
                mainActivityInstance.receivePunchable = false;
                stopTraining();

                //go to stats activity;
//                startStatsActivity(2);
            }else if (setid != -1){
                resultComboDTO = new TrainingResultComboDTO(currentComboDTO.getName(), resultPunchList, String.valueOf(System.currentTimeMillis()));
                resultComboList.add(resultComboDTO);
                mainActivityInstance.receivePunchable = false;
                gotoNextCombo();
            }else if (workoutDTO != null){
                resultComboDTO = new TrainingResultComboDTO(currentComboDTO.getName(), resultPunchList, String.valueOf(System.currentTimeMillis()));
                resultComboList.add(resultComboDTO);
                mainActivityInstance.receivePunchable = false;
                gotoNextCombo();
            }
        }else {
            currentPunchIndex ++;

            int min = Math.max(0, 3 - currentPunchIndex );
            int max = Math.min(7, currentComboDTO.getComboTypes().size() - currentPunchIndex + 3);

            for (int i = 0; i < maxview; i++){
                LinearLayout child = (LinearLayout)comboNumParent.getChildAt(i);
                TextView keyView = (TextView)child.findViewById(R.id.key);

                if (i < min){
                    child.setVisibility(View.INVISIBLE);
                }else if (i < max){
                    child.setVisibility(View.VISIBLE);
                    keyView.setText(currentComboDTO.getComboTypes().get(currentPunchIndex + i - 3));

                    if (i == 3){
                        keyView.setTextSize(115);
                    }else {
                        keyView.setTextSize(80);
                    }

                    if (i == max - 1){
                        View divider = child.findViewById(R.id.combo_divider);
                        divider.setVisibility(View.INVISIBLE);
                    }
                }else {
                    child.setVisibility(View.INVISIBLE);
                }
            }

            LinearLayout resultcurrentChild = (LinearLayout)comboResultParent.getChildAt(currentPunchIndex - 1);
            TextView resultcurrentkeyView = (TextView)resultcurrentChild.findViewById(R.id.key);

            //update result view

            if (currentPunchIndex == 1){
                if (success)
                    resultcurrentkeyView.setBackgroundResource(R.drawable.punch_success_first);
                else
                    resultcurrentkeyView.setBackgroundResource(R.drawable.punch_fail_first);
            }else {
                if (success)
                    resultcurrentkeyView.setBackgroundResource(R.drawable.punch_success_next);
                else
                    resultcurrentkeyView.setBackgroundResource(R.drawable.punch_fail_next);
            }

            resultcurrentkeyView.setTextColor(getResources().getColor(R.color.black));

            //update next result view
            LinearLayout resultnextChild = (LinearLayout)comboResultParent.getChildAt(currentPunchIndex);
            TextView resultnextkeyView = (TextView)resultnextChild.findViewById(R.id.key);

            resultnextkeyView.setBackgroundResource(R.drawable.next_punch_next);
            resultnextkeyView.setTextColor(getResources().getColor(R.color.white));

            if (resultnextkeyView.getText().toString().length() == 2){
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    updateView(true);
                    }
                }, 300);
            }

            if (workoutDTO != null)
                trainingProgressStatus.setText(ComboSetUtil.punchTypeMap.get(currentComboDTO.getComboTypes().get(currentPunchIndex)));
        }
    }

    private void connectSensor(boolean isLeft){
        if (mainActivityInstance.deviceLeft.equals(EFDConstants.BLANK_TEXT) && mainActivityInstance.deviceRight.equals(EFDConstants.BLANK_TEXT)) {
            Toast.makeText(this, EFDConstants.DEVICE_ID_MUST_NOT_BE_BLANK_ERROR, Toast.LENGTH_SHORT).show();
        } else if (mainActivityInstance.deviceLeft.equalsIgnoreCase(mainActivityInstance.deviceRight)) {
            Toast.makeText(this, EFDConstants.DEVICE_ID_MUST_NOT_BE_SAME, Toast.LENGTH_SHORT).show();
        } else {
            //code to clear punch history details after we have restarted a training
            mainActivityInstance.punchDataDTO.resetValues(0);
            mainActivityInstance.liveMonitorDataMap.clear();
            mainActivityInstance.punchHistoryGraph.clear();
            mainActivityInstance.endTrainingTime = EFDConstants.DEFAULT_START_TIME;
//                mainActivityInstance.getBagTrainingOneFragment().resetTrainingOnePageLiveData();
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

    private void playBoxingBell(){
        if (audioEnabled) {
            int volume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            bellplayer.start();
//            if (volume != 0 && bellplayer != null)
        }
    }

    private void startTraining(){
//        if (!mainActivityInstance.leftSensorConnected && !mainActivityInstance.rightSensorConnected){
//            StatisticUtil.showToastMessage("Please connect with sensors");
//            return;
//        }

        if (comboid != -1 || setid != -1){
            //this is combo training
            startProgressCombosetTimer();
            playBoxingBell();
            mainActivityInstance.startRoundTraining();
            startTrainingBtn.setText(getResources().getString(R.string.stop_training));
        }else if (workoutDTO != null){
            if (currentStatus == -1){
                startProgressCombosetTimer();
                startProgressWorkoutTimer();
                //prepare for round 1
                currentStatus = 0;
                roundvalue = 1;
                totalTime = prepareTime;
                currentProgressTime = totalTime;
                trainingProgressStatus.setText("PREPARE");
                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_preparebar));
                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_prepare));

                nextcomboTip.setVisibility(View.INVISIBLE);
                progressView.setVisibility(View.INVISIBLE);
                timerLayout.setVisibility(View.VISIBLE);

                progressBar.setMax(totalTime);
                progressBar.setProgress(totalTime - currentProgressTime);
            }
        }
    }

    private void stopTraining(){

        if (comboid != -1){
            if (mainActivityInstance.receivePunchable){
                resultComboDTO = new TrainingResultComboDTO(currentComboDTO.getName(), resultPunchList, String.valueOf(System.currentTimeMillis()));
            }

            mainActivityInstance.trainingresultComboDTO = resultComboDTO;
            ComboSetUtil.saveComboStats(MainActivity.db, resultComboDTO);
            mainActivityInstance.showStats(1);
//            startStatsActivity(2);
        }else if (setid != -1){
            if (mainActivityInstance.receivePunchable){
                resultComboDTO = new TrainingResultComboDTO(currentComboDTO.getName(), resultPunchList, String.valueOf(System.currentTimeMillis()));
                resultComboList.add(resultComboDTO);
            }

            mainActivityInstance.trainingResultSetDTO = resultSetDTO;
            ComboSetUtil.saveSetStats(MainActivity.db, resultSetDTO);
            mainActivityInstance.showStats(2);
//            startStatsActivity(3);
        }else if (workoutDTO != null){
            if (mainActivityInstance.receivePunchable) {
                resultComboDTO = new TrainingResultComboDTO(currentComboDTO.getName(), resultPunchList, String.valueOf(System.currentTimeMillis()));
                resultComboList.add(resultComboDTO);
                resultSetDTO = new TrainingResultSetDTO("ROUND " + (roundvalue + 1), resultComboList, String.valueOf(System.currentTimeMillis()));
                resultRoundList.add(resultSetDTO);
            }

            resultWorkoutDTO = new TrainingResultWorkoutDTO(workoutDTO.getName(), resultRoundList, String.valueOf(System.currentTimeMillis()));

            mainActivityInstance.trainingResultWorkoutDTO = resultWorkoutDTO;
            ComboSetUtil.saveWorkStats(MainActivity.db, resultWorkoutDTO);

            mainActivityInstance.showStats(3);
//            startStatsActivity(4);
        }

        stopProgressCombosetTimer();
        stopProgressWorkoutTimer();

        startTrainingBtn.setText(getResources().getString(R.string.start_training));
        mainActivityInstance.stopRoundTraining();
        mainActivityInstance.stopTraining();

        finish();
    }

    public void startProgressCombosetTimer (){
        progressCombosetTimer = new Timer();
        initializeProgressCombosetTimerTask();
        progressCombosetTimer.schedule(updateProgressComboSetTimerTask, 0, 1000);
    }

    public void stopProgressCombosetTimer (){
        if (progressCombosetTimer != null){
            Log.e("Super", "stop combo timer");
            progressCombosetTimer.cancel();
            progressCombosetTimer = null;
        }
    }

    public void initializeProgressCombosetTimerTask (){
        updateProgressComboSetTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        currentTime++;
                        String text = PresetUtil.chagngeSecsToTime(currentTime) + " - STOP";

                        startTrainingBtn.setText(text);
                        if (currentTime % 1 == 0)
                            tmpStart();
                    }
                });
            }
        };
    }

    public void startProgressWorkoutTimer (){
        progressWorkoutTimer = new Timer();
        initializeProgressWorkoutTimerTask();
        progressWorkoutTimer.schedule(updateProgressWorkoutTimerTask, 0, 1000);
    }

    public void stopProgressWorkoutTimer (){
        if (progressWorkoutTimer != null){
            progressWorkoutTimer.cancel();
            progressWorkoutTimer = null;
        }
    }

    public void initializeProgressWorkoutTimerTask (){
        updateProgressWorkoutTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentProgressTime == 0){
                            if (currentStatus == 0){
                                //prepare is finished, go to round
                                totalTime = roundTime;
                                currentProgressTime = totalTime;

                                trainingProgressStatus.setText("ROUND " + roundvalue);
                                currentStatus++;
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_roundbar));
//                                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_round));
                                trainingProgressStatus.setTextColor(getResources().getColor(R.color.white));
                                trainingStartTime = System.currentTimeMillis();
                                resetPunchDetails();
                                mainActivityInstance.startRoundTraining();

                                progressView.setVisibility(View.VISIBLE);
                                timerLayout.setVisibility(View.INVISIBLE);
                                //round is starting
                                playBoxingBell();
                            }
                            else if (currentStatus == 1){
                                //round is finished, go to rest
                                totalTime = restTime;
                                currentProgressTime = totalTime;
                                trainingProgressStatus.setText("REST");
                                currentStatus++;
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_restbar));
                                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_rest));
                                mainActivityInstance.stopRoundTraining();
                                punchTypeView.setText("");

                                nextcomboTip.setVisibility(View.INVISIBLE);
                                progressView.setVisibility(View.INVISIBLE);
                                timerLayout.setVisibility(View.VISIBLE);
                                //round is finished
                                playBoxingBell();
                            }else if (currentStatus == 2){
                                currentStatus++;
                                if (roundvalue == roundCount){
                                    //training is finished
                                    Toast.makeText(ComboSetTrainingActivity.this, "Training is finished", Toast.LENGTH_SHORT).show();
                                    currentStatus = -1;
                                    startTrainingBtn.setText(getResources().getString(R.string.start_training));
                                    trainingProgressStatus.setText("");
                                    stopProgressWorkoutTimer();
                                    stopProgressCombosetTimer();
                                }else {
                                    roundvalue++;
                                    totalTime = roundTime;
                                    currentProgressTime = totalTime;
                                    currentStatus = 1;
                                    trainingProgressStatus.setText("ROUND " + roundvalue);
                                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_roundbar));
//                                    trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_round));
                                    trainingProgressStatus.setTextColor(getResources().getColor(R.color.white));
                                    currentComboIndex = 0;
                                    currentComboDTO = ComboSetUtil.getComboDtowithID(workoutDTO.getRoundsetIDs().get(roundvalue - 1).get(0));
                                    initComboTrainingView();

                                    trainingStartTime = System.currentTimeMillis();
                                    resetPunchDetails();
                                    mainActivityInstance.startRoundTraining();

                                    progressView.setVisibility(View.VISIBLE);
                                    timerLayout.setVisibility(View.INVISIBLE);

                                    playBoxingBell();
                                }
                            }

                            progressBar.setProgress(0);
                            progressBar.setMax(totalTime);
                            currentTimeView.setText(PresetUtil.chagngeSecsToTime(currentProgressTime));
                        }else {
                            currentProgressTime--;
                            if (currentStatus == 1 && currentProgressTime == warningTime){
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.customprogress_warningbar));
                                trainingProgressStatus.setTextColor(getResources().getColor(R.color.progress_warning));
                            }
                            progressBar.setProgress(totalTime - currentProgressTime);
                            currentTimeView.setText(PresetUtil.chagngeSecsToTime(currentProgressTime));
                        }
                    }
                });
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityInstance.stopRoundTraining();
        stopProgressCombosetTimer();
        stopProgressWorkoutTimer();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void receivedPunchData(PunchHistoryGraphDataDetails details) {
        super.receivedPunchData(details);
        punchLists.add(details);

        PunchDTO punchDTO = new PunchDTO();
        punchDTO.setTime((int)(System.currentTimeMillis() - trainingStartTime));
        int currentSpeed = Integer.parseInt(details.punchSpeed);
        int currentForce = Integer.parseInt(details.punchForce);
        punchDTO.setPower(currentForce);
        punchDTO.setSpeed(currentSpeed);

        String hand = details.boxersHand.equalsIgnoreCase("L") ? "LEFT" : "RIGHT";

        speedValue.setText(details.punchSpeed);
        forceValue.setText(details.punchForce);


        maxForce = Math.max(maxForce, (float)currentForce);
        maxSpeed = Math.max(maxSpeed, (float) currentSpeed);

        if (hand.equalsIgnoreCase("left")){
            leftmaxForce = Math.max(leftmaxForce, (float)currentForce);
            leftmaxSpeed = Math.max(leftmaxSpeed, (float) currentSpeed);

            int leftpunchCount = leftpunchDTOS.size();
            leftavgForce = (leftavgForce * leftpunchCount + currentForce) / (leftpunchCount + 1);
            leftavgSpeed = (leftavgSpeed * leftpunchCount + currentSpeed) / (leftpunchCount + 1);

            leftpunchDTOS.add(punchDTO);

            leftavgSpeedView.setText(String.valueOf((int)leftavgSpeed));
            leftavgForceView.setText(String.valueOf((int)leftavgForce));
            leftmaxSpeedView.setText(String.valueOf((int)leftmaxSpeed));
            leftmaxForceView.setText(String.valueOf((int)leftmaxForce));

        }else if (hand.equalsIgnoreCase("right")){
            rightmaxForce = Math.max(rightmaxForce, (float)currentForce);
            rightmaxSpeed = Math.max(rightmaxSpeed, (float) currentSpeed);

            int rightpunchCount = rightpunchDTOS.size();
            rightavgForce = (avgForce * rightpunchCount + currentForce) / (rightpunchCount + 1);
            rightavgSpeed = (avgSpeed * rightpunchCount + currentSpeed) / (rightpunchCount + 1);
            rightpunchDTOS.add(punchDTO);

            rightavgSpeedView.setText(String.valueOf((int)rightavgSpeed));
            rightavgForceView.setText(String.valueOf((int)rightavgForce));
            rightmaxSpeedView.setText(String.valueOf((int)rightmaxSpeed));
            rightmaxForceView.setText(String.valueOf((int)rightmaxForce));
        }

        int punchCount = punchDTOs.size();
        avgForce = (avgForce * punchCount + currentForce) / (punchCount + 1);
        avgSpeed = (avgSpeed * punchCount + currentSpeed) / (punchCount + 1);

        punchDTOs.add(punchDTO);
        punchCountView.setText(String.valueOf(punchDTOs.size()));
        avgSpeedView.setText(String.valueOf((int)avgSpeed));
        avgForceView.setText(String.valueOf((int)avgForce));
        maxSpeedView.setText(String.valueOf((int)maxSpeed));
        maxForceView.setText(String.valueOf((int)maxForce));

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
        String detectedPunchType = hand + " " + punchType;
        punchTypeView.setText(detectedPunchType);

        String successString = ComboSetUtil.punchTypeMap.get(currentComboDTO.getComboTypes().get(currentPunchIndex));
        if (successString.equalsIgnoreCase(EFDConstants.JAB) || successString.equalsIgnoreCase(EFDConstants.STRAIGHT)){
            if (punchType.equalsIgnoreCase(successString)){
                updateView(true);
            }else
                updateView(false);
        }else {
            if (detectedPunchType.equalsIgnoreCase(successString))
                updateView(true);
            else
                updateView(false);
        }

    }


    @Override
    public void setDeviceConnectionState() {
        super.setDeviceConnectionState();

        if (mainActivityInstance.leftSensorConnected) {
            leftSensorConnectionButton.setImageResource(R.drawable.green_btn);
            leftSensorConnectionLayout.setEnabled(false);
        }

        if (mainActivityInstance.rightSensorConnected) {
            rightSensorConnectionButton.setImageResource(R.drawable.green_btn);
            rightSensorConnectionLayout.setEnabled(false);
        }
    }

    @Override
    public void setBatteryVoltage(String batteryVoltage) {
        super.setBatteryVoltage(batteryVoltage);
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
        super.handleBatteryLayoutClickable(isLeft, clickable);
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
        super.resetConnectDeviceBg(hand);

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
        super.resetBatteryVoltage(hand);
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
