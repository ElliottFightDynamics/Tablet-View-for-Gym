package com.efd.striketectablet.activity.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.bluetooth.BluetoothScanActivity;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomEditText;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.utilities.EFDConstants;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileFragment extends Fragment  {

    private MainActivity mainActivityInstance;

    private Spinner weightSpinner, heightSpinner, gloveSpinner, reachSpinner;
    private CustomButton connectBtn;
    private CustomEditText leftIDView, rightIDView;

    CustomSpinnerAdapter weightAdpater, heightAdapter, gloveAdapter, reachAdapter;


    private String leftDeviceAddress, rightDeviceAddress;
    private boolean isLeftSearched = false;
    private boolean isRightSearched = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mainActivityInstance = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        weightSpinner = (Spinner)view.findViewById(R.id.weight_spinner);
        weightAdpater = new CustomSpinnerAdapter(getActivity(), R.layout.custom_spinner_with_img, PresetUtil.weightList, EFDConstants.SPINNER_WHITE);
        weightSpinner.setAdapter(weightAdpater);

        heightSpinner = (Spinner)view.findViewById(R.id.height_spinner);
        heightAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.custom_spinner_with_img, PresetUtil.weightList, EFDConstants.SPINNER_WHITE);
        heightSpinner.setAdapter(heightAdapter);

        gloveSpinner = (Spinner)view.findViewById(R.id.glove_spinner);
        gloveAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.custom_spinner_with_img, PresetUtil.gloveList, EFDConstants.SPINNER_WHITE);
        gloveSpinner.setAdapter(gloveAdapter);

        reachSpinner = (Spinner)view.findViewById(R.id.reach_spinner);
        reachAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.custom_spinner_with_img, PresetUtil.weightList, EFDConstants.SPINNER_WHITE);
        reachSpinner.setAdapter(reachAdapter);

        weightSpinner.setSelection(PresetUtil.weightList.size() / 2);
        heightSpinner.setSelection(PresetUtil.weightList.size() / 2);
        gloveSpinner.setSelection(0);
        reachSpinner.setSelection(PresetUtil.weightList.size() / 2);

        connectBtn = (CustomButton)view.findViewById(R.id.connect_button);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConnectSensorDialog();
            }
        });

        return view;
    }

    public void showConnectSensorDialog(){
        final Dialog dialog = new Dialog(getActivity());

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_connectsensor);
        dialog.setCancelable(false);

        ImageView leftSearchBtn, rightSearchBtn;
        final CustomButton cancelBtn, okBtn;

        leftIDView = (CustomEditText)dialog.findViewById(R.id.left_sensor_id);
        rightIDView = (CustomEditText)dialog.findViewById(R.id.right_sensor_id);

        leftSearchBtn = (ImageView)dialog.findViewById(R.id.left_search);
        rightSearchBtn = (ImageView)dialog.findViewById(R.id.right_search);
        cancelBtn = (CustomButton)dialog.findViewById(R.id.cancel_btn);
        okBtn = (CustomButton)dialog.findViewById(R.id.ok_btn);

        if (!mainActivityInstance.flagForDevice) {
            leftIDView.setText("");
            rightIDView.setText("");
            mainActivityInstance.flagForDevice = true;
        } else {
            leftIDView.setText(mainActivityInstance.getCurrentLeftDevice());
            rightIDView.setText(mainActivityInstance.getCurrentRightDevice());
        }

        leftSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSensor(leftIDView.getText().toString().trim(), rightIDView.getText().toString().trim(), MainActivity.leftDeviceRequestCode);
            }
        });

        rightSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSensor(leftIDView.getText().toString().trim(), rightIDView.getText().toString().trim(), MainActivity.rightDeviceRequestCode);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityInstance.setCurrentLeftDevice(leftIDView.getText().toString().trim());
                mainActivityInstance.setCurrentRightDevice(rightIDView.getText().toString().trim());

                setBoxerIds();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setBoxerIds() {
        if (!mainActivityInstance.isGuestBoxerActive()) {
            mainActivityInstance.deviceLeft = leftIDView.getText().toString().trim();
            mainActivityInstance.deviceRight = rightIDView.getText().toString().trim();
        }
    }

    private void searchSensor(String leftDeviceAddress, String rightDeviceAddress, int requestCode) {
        if (!mainActivityInstance.trainingManager.isTrainingRunning()) {
            Intent intent = new Intent(mainActivityInstance, BluetoothScanActivity.class);
            intent.putExtra("deviceLeft", leftDeviceAddress);
            intent.putExtra("deviceRight", rightDeviceAddress);
            startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(mainActivityInstance, EFDConstants.SENSOR_SEARCH_BUTTON_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(MainActivity.TAG, "On result code :---1195 "+Activity.RESULT_OK);

        switch (requestCode) {

            case MainActivity.rightDeviceRequestCode:
                if (resultCode == Activity.RESULT_OK) {
                    rightDeviceAddress = data.getStringExtra("deviceAddress");
                    rightIDView.setText(rightDeviceAddress);
                    isRightSearched = true;

                    ArrayList<String> sensorAddressLists = data.getStringArrayListExtra("deviceList");
                    autoPlaceSensors(sensorAddressLists);
                }
                break;

            case MainActivity.leftDeviceRequestCode:
                if (resultCode == Activity.RESULT_OK) {
                    leftDeviceAddress = data.getStringExtra("deviceAddress");
                    leftIDView.setText(leftDeviceAddress);
                    isLeftSearched = true;

                    ArrayList<String> sensorAddressLists = data.getStringArrayListExtra("deviceList");
                    autoPlaceSensors(sensorAddressLists);
                }
                break;

            case MainActivity.guestRightDeviceRequestCode:
                if (resultCode == Activity.RESULT_OK) {

                }
                break;

            case MainActivity.guestLeftDeviceRequestCode:
                if (resultCode == Activity.RESULT_OK) {

                }
                break;

            default:
                break;
        }
    }

    private void autoPlaceSensors(ArrayList<String> sensorLists){
        if (isLeftSearched && isRightSearched){
            return;
        }else {
            if (isLeftSearched){
                //left sensor is searched, set address for right sensor
                for (int i = 0; i < sensorLists.size(); i++){

                    String result = sensorLists.get(i).replace(":", EFDConstants.BLANK_TEXT);

                    if (!leftIDView.getText().toString().equalsIgnoreCase(result)){
                        rightIDView.setText(result);
                        break;
                    }
                }
            }else if (isRightSearched){
                //right sensor is searched, set address for left sensor
                for (int i = 0; i < sensorLists.size(); i++){
                    String result = sensorLists.get(i).replace(":", EFDConstants.BLANK_TEXT);

                    if (!rightIDView.getText().toString().equalsIgnoreCase(result)){
                        leftIDView.setText(result);
                        break;
                    }
                }
            }
        }
    }
}
