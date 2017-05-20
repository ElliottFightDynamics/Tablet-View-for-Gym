package com.efd.striketectablet.activity.training;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.efd.striketectablet.DTO.PunchHistoryGraphDataDetails;
import com.efd.striketectablet.DTO.TrainingBatteryLayoutDTO;
import com.efd.striketectablet.DTO.TrainingBatteryVoltageDTO;
import com.efd.striketectablet.DTO.TrainingConnectStatusDTO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Super on 5/14/2017.
 */

public class BaseTrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PunchHistoryGraphDataDetails details) {
        receivedPunchData(details);
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TrainingConnectStatusDTO connectStatusDTO) {
        if (connectStatusDTO.getUpdateConnectStatus()){
            setDeviceConnectionState();
        }else {
            resetConnectDeviceBg(connectStatusDTO.getHand());
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TrainingBatteryLayoutDTO batteryLayoutDTO) {
        handleBatteryLayoutClickable(batteryLayoutDTO.getIsLeft(), batteryLayoutDTO.getClickage());
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TrainingBatteryVoltageDTO batteryVoltageDTO) {
        if (batteryVoltageDTO.getResetVoltage()){
            resetBatteryVoltage(batteryVoltageDTO.getHand());
        }else {
            setBatteryVoltage(batteryVoltageDTO.getVoltage());
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Super", "event but on start");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("Super", "event but on stop");
        EventBus.getDefault().unregister(this);
    }

    public void receivedPunchData(PunchHistoryGraphDataDetails details){};
    public void setDeviceConnectionState (){};
    public void setBatteryVoltage(String voltage){};
    public void handleBatteryLayoutClickable(boolean isLeft, boolean clickable){};
    public void resetConnectDeviceBg (String hand){};
    public void resetBatteryVoltage(String hand){};
}
