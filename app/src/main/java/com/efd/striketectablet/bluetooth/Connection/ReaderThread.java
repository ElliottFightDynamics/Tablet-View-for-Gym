package com.efd.striketectablet.bluetooth.Connection;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.bluetooth.DeviceDataProcessingThread;
import com.efd.striketectablet.utilities.EFDConstants;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * DeviceDataProcessingThread class to read data from connected bluetooth device or CSV file.
 */
public class ReaderThread extends Thread {

    // Class log tag
    private static final String TAG = "ReaderThread";

    /**
     * Set this flag to true to start reading from CSV file
     */
    private static final boolean DEBUG_WITH_CSV_FILE = false;

    // Handler to communicate with UI
    private final Handler uiHandler;

    // Socket from bluetooth connection
    private final BluetoothSocket connectionSocket;

    // Input and output data from bluetooth connection
    private final InputStream inStream;
    private final OutputStream outStream;
    private final DataInputStream dataInputStream;
    private String boxerName;
    private String boxerStance;
    private String hand;
    private Integer trainingDataId;
    public DeviceDataProcessingThread deviceDataProcessingThread;
    private Thread dataProcessingThread;
    BlockingQueue<Integer[]> sensorDatablockingQueue;

    // Manager for bluetooth connection
    private final ConnectionManager bluetoothConnectionManager;

    /**
     * Basic constructor
     *
     * @param connection  - Socket for connected bluetooth device
     * @param handler     - UI handler for sending messages to UI
     * @param logLocation - Location on the local file system for saving info
     * @param manager     - Connection manger to manage the reading thread
     * @param mainContext
     */
    public ReaderThread(BluetoothSocket connection, Handler handler, ConnectionManager manager) {

        uiHandler = handler;
        connectionSocket = connection;
        bluetoothConnectionManager = manager;
        this.boxerName = manager.getBoxerName();
        this.boxerStance = manager.getBoxerStance();
        this.hand = manager.getBoxerHand();
        this.trainingDataId = manager.getTrainingDataId();

        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        sensorDatablockingQueue = new LinkedBlockingQueue<Integer[]>();
        try {
            tmpOut = connection.getOutputStream();
            tmpIn = connection.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, "temp sockets not created", e);
        }
        Log.i(TAG, "Hand=" + hand + " TrainingDataId=" + trainingDataId);
        outStream = tmpOut;
        inStream = tmpIn;
        dataInputStream = new DataInputStream(inStream);

        final String csvFileName = "Boxer1Right.csv";
        final String CSV_FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + EFDConstants.APP_DIRECTORY + File.separator + EFDConstants.CONFIG_DIRECTORY;
        File csvFile = (DEBUG_WITH_CSV_FILE) ? new File(CSV_FILE_PATH, csvFileName) : null;
        deviceDataProcessingThread = new DeviceDataProcessingThread(sensorDatablockingQueue, boxerName, hand, boxerStance, 1, trainingDataId, uiHandler, /*mainContext,*/ csvFile, DEBUG_WITH_CSV_FILE);

        Log.i(TAG, "Reading data from " + ((DEBUG_WITH_CSV_FILE) ? ("csv file: " + csvFileName) : "chip"));

        dataProcessingThread = new Thread(this.deviceDataProcessingThread);
    }

    /**
     * Method to run on its own thread for reading from the bluetooth device.
     */
    @Override
    public void run() {

        Log.d(TAG, "BEGIN ReaderThread");

        // Initiate data streaming from bluetooth device
        try {
            setBatteryStreamMode0x0E();
            setHighGStreamMode0x07();
            setLowGStreamMode0x01();
            setGyroSteamMode0x10();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //int counter=0;
        dataProcessingThread.start();
        byte[] packetByte = new byte[104];
        // Read as long as we are connected
        while (true) {
            try {
                //counter=counter+packetByte.length;
                // For however many bytes we received, convert them to unsigned and store them
                if (dataInputStream != null) {
                    dataInputStream.read(packetByte, 0, packetByte.length);
                    //Log.d(TAG, "Time="+getCurrentTime()+" total no of packet="+counter);
                    //Log.d(TAG, "packetByte="+packetByte);
                    unsignedToBytes(packetByte, packetByte.length);
                }

            } catch (Exception e) {
                Log.e(TAG, "Failed to read data from device:-" + e);
                Message msg = uiHandler.obtainMessage(MainActivity.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("CONNECTION", "closed");
                bundle.putString("HAND", hand);
                msg.setData(bundle);
                uiHandler.sendMessage(msg);
                bluetoothConnectionManager.disconnect();
                break;
            }
        }
    }

    /**
     * Convert signed byte[] to unsigned int[]
     *
     * @param packetByte
     * @param noOfBytes
     * @throws InterruptedException
     */
    private void unsignedToBytes(byte[] packetByte, int noOfBytes) throws InterruptedException {
        Integer[] intArray = new Integer[noOfBytes];
        for (int i = 0; i < noOfBytes; i++) {
            intArray[i] = (packetByte[i] & 0xFF);
        }
        sensorDatablockingQueue.put(intArray);
        //Log.d(TAG, "Queue="+senserDatablockingQueue);
        //Log.d(TAG, "Time="+getCurrentTime()+" Queue size=-----------------------------------------"+senserDatablockingQueue.size());
    }

//	private String getCurrentTime(){
//		Calendar currentDate = Calendar.getInstance();
//		SimpleDateFormat formatter = new SimpleDateFormat(EFDConstants.DATE_FORMAT);
//		return formatter.format(currentDate.getTime());
//	}

    public void setLowGStreamMode0x01() throws IOException {
        outStream.write(170);
        outStream.write(1);
        outStream.write(5);
        outStream.write(0);
        outStream.write(1);
    }

    public void setHighGStreamMode0x07() throws IOException {
        outStream.write(170);
        outStream.write(7);
        outStream.write(5);
        outStream.write(0);
        outStream.write(1);
    }

    public void setBatteryStreamMode0x0E() throws IOException {
        outStream.write(170);
        outStream.write(14);
        outStream.write(5);
        outStream.write(0);
        outStream.write(1);
    }

    public void setGyroSteamMode0x10() throws IOException {
        outStream.write(170);
        outStream.write(16);
        outStream.write(5);
        outStream.write(0);
        outStream.write(1);
    }

    /**
     * Close the connection to the bluetooth device.
     */
    public void cancel() {
        Log.d(TAG, "Closing connection");

        try {
            close();
            connectionSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "close() of connect socket failed", e);
        }
    }

    public void close() {
        synchronized (this) {
            try {
                if (this.inStream != null) {
                    this.inStream.close();
                }
                if (this.outStream != null) {
                    this.outStream.close();
                }
                if (this.dataInputStream != null) {
                    this.dataInputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
