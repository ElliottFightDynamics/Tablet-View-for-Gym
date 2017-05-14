package com.efd.striketectablet.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.efd.striketectablet.R;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class BluetoothScanActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    ListView listViewDetected;
    Button buttonSearch;
    SensorScanAdapter detectedAdapter;
    BluetoothAdapter bluetoothAdapter = null;
    ArrayList<BluetoothDevice> arrayListBluetoothDevices = null;
    ArrayList<String> arrayListBluetoothAddresses = null;

    private static final int REQUEST_ENABLE_BT = 0;
    String leftDeviceAddress = EFDConstants.BLANK_TEXT, rightDeviceAddress = EFDConstants.BLANK_TEXT;
    ProgressDialog dialog;

    @SuppressLint({"NewApi", "DefaultLocale"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove Title bar
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        showLoader("Discovering MMAGlove Devices...");
        if (extras != null) {
            leftDeviceAddress = extras.getString("deviceLeft").toUpperCase();
            rightDeviceAddress = extras.getString("deviceRight").toUpperCase();
        }
        setContentView(R.layout.activity_bluetooth_scan);

        listViewDetected = (ListView) findViewById(R.id.listViewDetected);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        arrayListBluetoothDevices = new ArrayList<BluetoothDevice>();
        arrayListBluetoothAddresses = new ArrayList<>();

        detectedAdapter = new SensorScanAdapter(this, R.layout.device_selection_list, arrayListBluetoothDevices);
        listViewDetected.setAdapter(detectedAdapter);
        detectedAdapter.notifyDataSetChanged();
        arrayListBluetoothDevices.clear();
        arrayListBluetoothAddresses.clear();

        detectedAdapter.clear();
        startSearching();
    }

    public class SensorScanAdapter extends ArrayAdapter<BluetoothDevice> {
        Context context;
        int layoutResourceId;
        ArrayList<BluetoothDevice> data = null;

        public SensorScanAdapter(Context context, int layoutResourceId, ArrayList<BluetoothDevice> arrayListBluetoothDevices) {
            super(context, layoutResourceId, arrayListBluetoothDevices);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = arrayListBluetoothDevices;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflateObj = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflateObj.inflate(R.layout.device_selection_list, null);
            }
            RadioButton sensorButton = (RadioButton) convertView.findViewById(R.id.deviceNameID);
            final int itemPosition = position;
            BluetoothDevice device = data.get(itemPosition);
            sensorButton.setText(device.getName() + "\n" + device.getAddress());
            return convertView;
        }

    }

    public void onBlueToothDeviceRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.deviceNameID:
                if (checked) {
                    RadioButton sensorButton = (RadioButton) view.findViewById(R.id.deviceNameID);
                    String deviceName = sensorButton.getText().toString();
                    String listItem = deviceName;
                    String[] splitAddress = listItem.split("\\n");
                    getIntent().putExtra("device_id", splitAddress[1].replace(":", EFDConstants.BLANK_TEXT));
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("deviceAddress", splitAddress[1].replace(":", EFDConstants.BLANK_TEXT));
                    resultIntent.putExtra("deviceName", splitAddress[0].toString());
                    resultIntent.putStringArrayListExtra("deviceList", arrayListBluetoothAddresses);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        Log.d("BluetoothScanActivity", "In onStop( )" + myReceiver);
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        Log.d("BluetoothScanActivity", "In onPause( )");
    }

    @SuppressLint("NewApi")
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                Log.d("BluetoothScanActivity", "action=" + action);
                if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    Log.d("BluetoothScanActivity", "Inside action started");
//            	showLoader("Discovering MMAGlove Devices");
                }
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Log.d("BluetoothScanActivity", "Inside action found");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    try {
                        if (arrayListBluetoothDevices.size() < 1) // this checks if the size of bluetooth device is 0,then add the
                        {
                            if (device.getName() != null && device.getName() != EFDConstants.BLANK_TEXT && deviceNameExistsInList(device.getName()) && !device.getAddress().replace(":", EFDConstants.BLANK_TEXT).equals(leftDeviceAddress) && !device.getAddress().replace(":", EFDConstants.BLANK_TEXT).equals(rightDeviceAddress)) {
                                Toast.makeText(context, EFDConstants.SENSOR_FOUND_MESSAGE + device.getName() + EFDConstants.DETECTED_TEXT, Toast.LENGTH_SHORT).show();
                                //detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                                listViewDetected.setAlpha(1);
                                arrayListBluetoothDevices.add(device);
                                arrayListBluetoothAddresses.add(device.getAddress());
                                detectedAdapter.notifyDataSetChanged();
                                listViewDetected.setVisibility(View.VISIBLE);
                            }
                        } else {
                            boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                            for (int i = 0; i < arrayListBluetoothDevices.size(); i++) {
                                if (device.getAddress().equalsIgnoreCase(arrayListBluetoothDevices.get(i).getAddress())) {
                                    flag = false;
                                }
                            }
                            if (flag == true) {
                                if (device.getName() != null && device.getName() != EFDConstants.BLANK_TEXT && deviceNameExistsInList(device.getName()) && !device.getAddress().replace(":", EFDConstants.BLANK_TEXT).equals(leftDeviceAddress) && !device.getAddress().replace(":", EFDConstants.BLANK_TEXT).equals(rightDeviceAddress)) {
                                    Toast.makeText(context, EFDConstants.SENSOR_FOUND_MESSAGE + device.getName() + EFDConstants.DETECTED_TEXT, Toast.LENGTH_SHORT).show();
                                    //detectedAdapter.add(device.getName()+"\n"+device.getAddress());
                                    arrayListBluetoothDevices.add(device);
                                    arrayListBluetoothAddresses.add(device.getAddress());
                                    detectedAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.i("Log", "Inside the exception: ");
                        e.printStackTrace();
                    }

                }
                if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    Log.d("BluetoothScanActivity", "Inside action finished");
                    Toast.makeText(getApplicationContext(), EFDConstants.SCANNING_FINISHED, Toast.LENGTH_SHORT).show();
                    stopLoader(EFDConstants.BLANK_TEXT);
                    if (arrayListBluetoothDevices.size() == 0) {
                        Toast.makeText(getApplicationContext(), EFDConstants.SENSOR_NOT_FOUND_MESSAGE, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private boolean deviceNameExistsInList(final String deviceName) {
        boolean result = false;
        if (null != deviceName) {
            for (String name : EFDConstants.MMAGLOVE_PREFIX) {
                if (deviceName.startsWith(name)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private void startSearching() {

        try {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            Log.d("BluetoothScanActivity", "in the start searching method");
            IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            BluetoothScanActivity.this.registerReceiver(myReceiver, intentFilter);
            bluetoothAdapter.startDiscovery();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), EFDConstants.BLUETOOTH_ACCESS_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoader(String message) {
        Log.d("BluetoothScanActivity", "Inside show loader");
        dialog = ProgressDialog.show(this, message, "Loading..", true);
        dialog.show();
    }

    private void stopLoader(String message) {
        Log.d("BluetoothScanActivity", "Inside stop loader");
        dialog.dismiss();
    }
}
