package com.efd.striketectablet.bluetooth.Connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.UUID;

/**
 * Class to manage the connection to and reading of a bluetooth device.
 *
 * @author Cogden
 */
public class ConnectionManager {

    // Class log tag
    private static final String TAG = "ConnectionManager";

    // Handler to communicate with UI
    private final Handler uiHandler;

    // Bluetooth settings
    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final BluetoothAdapter adapter;
    private ConnectionThread connectionThread;
    public ReaderThread readerThread;
    private String boxerName;
    private String boxerStance;
    private Integer trainingDataId;
    private String boxerHand;

    /**
     * Constructor
     *
     * @param handler - Handler for sending messages to the UI
     */
    public ConnectionManager(Handler handler) {
        adapter = BluetoothAdapter.getDefaultAdapter();
        uiHandler = handler;
    }

    /**
     * Attempts to establish a connection with a bluetooth device at the given MAC Address
     *
     * @param leftDeviceId - MAC address of our desired device
     * @param appContext
     */
    public synchronized void connect(String deviceId) {

        // If we aren't already trying to connect, start a connection attempt on its own thread
        if ((connectionThread == null) && (readerThread == null)) {
            try {
                BluetoothDevice bluetoothDevice = adapter.getRemoteDevice(deviceId);
                connectionThread = new ConnectionThread(bluetoothDevice, uiHandler, uuid, adapter, this);
                connectionThread.start();
            } catch (Exception e) {
                Log.i("ConnectionManager", "Device address is not correct ===" + connectionThread);
                String infoMsg;
                if (deviceId.equals("")) {
                    infoMsg = EFDConstants.EMPTY_FIELD_CONNECTION_ERROR_MESSAGE;
                } else {
                    infoMsg = EFDConstants.INCORRECT_SENSOR_ID_MESSAGE + deviceId;
                }
                Message msg = uiHandler.obtainMessage(MainActivity.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("TOAST", infoMsg);
                bundle.putString("DeviceAddress", deviceId);
                bundle.putString("CONNECTION", "unsuccess");
                msg.setData(bundle);
                uiHandler.sendMessage(msg);
            }
        } else if (readerThread != null) {
            Message msg = uiHandler.obtainMessage(MainActivity.MESSAGE_TOAST);
            Bundle bundle = new Bundle();
            bundle.putString(MainActivity.TOAST, EFDConstants.ALREADY_CONNECTED_MESSAGE_TEXT);
            msg.setData(bundle);
            uiHandler.sendMessage(msg);
        }
    }

    /**
     * Method to create a deviceDataProcessingThread thread for a successful connection to a bluetooth device.
     *
     * @param bluetoothSocket
     */
    public synchronized void connected(BluetoothSocket bluetoothSocket) {

        Log.d(TAG, "Connected to socket");

        // Cool, we are connected, so let's get rid of the connection thread
        if (connectionThread != null) {
            connectionThread = null;
        }

        // Dispose of currently running thread for reading data
        disposeReaderThread();

        readerThread = new ReaderThread(bluetoothSocket, uiHandler, this);
        readerThread.start();
    }

    /**
     * Cancels all running threads for the open connection on disconnect.
     */
    public synchronized void disconnect() {
        Log.i(TAG, "Closing connections");
        disposeConnectionThread();
        disposeReaderThread();
    }

    private void disposeReaderThread() {
        if (readerThread != null) {
            readerThread.cancel();
            readerThread = null;
        }
    }

    private void disposeConnectionThread() {
        if (connectionThread != null) {
            connectionThread.cancel();
            connectionThread = null;
        }
    }

    /**
     * Identifies the UI of the connection attempt failure.
     */
    public void connectionFailed() {
        // Send a failure message back to the Activity
        Message msg = uiHandler.obtainMessage(MainActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.TOAST, EFDConstants.UNABLE_TO_CONNECT_WITH_SENSOR_MESSAGE_TEXT);
        msg.setData(bundle);
        uiHandler.sendMessage(msg);
    }

    /**
     * Identifies the UI of the connection being lost.
     */
    public void connectionLost() {
        // Send a failure message back to the Activity
        Message msg = uiHandler.obtainMessage(MainActivity.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.TOAST, EFDConstants.SENSOR_CONNECTION_LOST_MESSAGE_TEXT);
        msg.setData(bundle);
        uiHandler.sendMessage(msg);
    }

    public String getBoxerName() {
        return boxerName;
    }

    public void setBoxerName(String boxerName) {
        this.boxerName = boxerName;
    }

    public String getBoxerStance() {
        return boxerStance;
    }

    public void setBoxerStance(String boxerStance) {
        this.boxerStance = boxerStance;
    }

    public Integer getTrainingDataId() {
        return trainingDataId;
    }

    public void setTrainingDataId(Integer trainingDataId) {
        this.trainingDataId = trainingDataId;
    }

    public String getBoxerHand() {
        return boxerHand;
    }

    public void setBoxerHand(String boxerHand) {
        this.boxerHand = boxerHand;
    }

    public ConnectionThread getConnectionThread() {
        return connectionThread;
    }
}