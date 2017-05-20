package com.efd.striketectablet.bluetooth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.efd.punch.PeakPunchValueDetector;
import com.efd.punch.PunchDetails;
import com.efd.punch.PunchListener;
import com.efd.punch.PunchVFAData;
import com.efd.punch.SensorBuffer;
import com.efd.punch.SensorDataPackage;
import com.efd.punch.SensorDataSample;
import com.efd.striketectablet.DTO.TrainingPunchDTO;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.bluetooth.readerBean.PunchDetectedMap;
import com.efd.striketectablet.bluetooth.readerBean.PunchDetectionConfig;
import com.efd.striketectablet.bluetooth.readerBean.PunchDetector;
import com.efd.striketectablet.bluetooth.readerBean.SensorData;
import com.efd.striketectablet.bluetooth.readerBean.VFA;
import com.efd.striketectablet.bluetooth.readerBean.VFAMap;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.mmaGlove.LowGPunchDetection;
import com.efd.striketectablet.mmaGlove.PunchVFACalculatorOnYYAxis;
import com.efd.striketectablet.mmaGlove.PunchVFACalculatorOnYZAxis;
import com.efd.striketectablet.util.rest.RestClient;
import com.efd.striketectablet.utilities.EFDConstants;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;


@SuppressLint("SimpleDateFormat")
public class DeviceDataProcessingThread extends Observable implements Runnable, PunchListener { //Changes by #1014

    private static final String TAG = "DDProcThread";

    String boxerName = "";
    String hand;
    String stance;
    Integer boxerId;
    Integer trainingId;
    boolean shouldStop = false;
    Handler mhandler;
    File file;
    boolean isreadFromCSVFile;
    BlockingQueue<Integer[]> senserDatablockingQueue;
    Calendar currentDate = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat(EFDConstants.DATE_FORMAT);
    private MainActivity mainActivityInstance;

    public DBAdapter db;
    private SensorBuffer sensorBuffer;
    private double effectivePunchMass;

    public DeviceDataProcessingThread(BlockingQueue<Integer[]> senserDatablockingQueue, String boxerName, String hand, String stance, Integer boxerId, Integer trainingId,
                                      Handler mHandler, File file, boolean isreadFromCSVFile) {
        try {
            synchronized (this) {
                this.senserDatablockingQueue = senserDatablockingQueue;
                this.boxerName = boxerName;
                this.hand = hand;
                this.stance = stance;
                this.mhandler = mHandler;
                this.boxerId = boxerId;
                this.trainingId = trainingId;
                db = DBAdapter.getInstance(MainActivity.context);
                mainActivityInstance = (MainActivity) MainActivity.context;
                this.file = file;
                this.isreadFromCSVFile = isreadFromCSVFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*super added, update training info when round stats*/
    public void updateTrainingInfo(String boxerName, String hand, String stance, Integer trainingId){
        this.boxerName = boxerName;
        this.hand = hand;
        this.stance = stance;
        this.trainingId = trainingId;
    }

    @Override
    public void run() {
        try {
            int highGBufferCapacity = 20;
            int gyroBufferCapacity = 9;
            int hiGZeroThreshold = 4000;
            int gyroZeroThreshold = 1000;

            effectivePunchMass = mainActivityInstance.boxerPunchMassEffect;

            PunchDetectionConfig.getInstance().setPunchMassEff(effectivePunchMass);

            sensorBuffer = new SensorBuffer(highGBufferCapacity, gyroBufferCapacity, hiGZeroThreshold, gyroZeroThreshold, this);

            readDataFromGloveDevice();
//			readDataFromGloveDeviceNew();    //This code for reading data from new chip
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ArrayList<Integer> readPacketFromQueue() {
        Integer[] packet = null;
        ArrayList<Integer> arrayList = null;
        try {
            if (senserDatablockingQueue != null && !this.shouldStop) {
                packet = this.senserDatablockingQueue.take();
                arrayList = new ArrayList<Integer>(Arrays.asList(packet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private int readDeviceDataByte(ArrayList<Integer> packetData) {
        int data = -1;
        try {
            if (packetData != null && !this.shouldStop && packetData.size() > 0) {
                data = packetData.remove(0);
            }
        } catch (Exception e) {
        }
        return data;
    }

    private int readWordBE(ArrayList<Integer> packetData) {

        int dataLsb, dataMsb;
        short data = 0;
        try {

            dataLsb = readDeviceDataByte(packetData);
            dataMsb = readDeviceDataByte(packetData);
            data = (short) ((dataMsb << 8) | (short) dataLsb);

        } catch (Exception ioe) {
        }
        return data;
    }

    /**
     * Method reading lowG & highG data from new chip
     *
     * @throws InterruptedException
     */
    private void readDataFromGloveDevice() throws InterruptedException {
        try {
            Log.i(TAG, "readDataFromGloveDevice");

            PeakPunchValueDetector peakPunchValueDetector = new PeakPunchValueDetector();
            SensorData[] sensorData = new SensorData[EFDConstants.SAMPLE_PACKET_SIZE];
            PunchDetector punchDetector = new PunchDetector();
            LowGPunchDetection lowGPunchDetetction = new LowGPunchDetection();

            PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis = new PunchVFACalculatorOnYYAxis();
            PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis = new PunchVFACalculatorOnYZAxis();
            PunchVFAData punchVFAData = new PunchVFAData();
            int startByte = 0;
            boolean isContinue = true, isValidStartByte = false, isValidMode = false;
            StringTokenizer stringTokenizer = null;

            String line = null;
            boolean csvDataReadingFinished = false;
            BufferedReader bufRdr = null;
            if (isreadFromCSVFile) {
                bufRdr = new BufferedReader(new FileReader(this.file));
                bufRdr.readLine();    // we have to read 1st line
            }
            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MMM_dd_HH_mm_ss");
            String dateNow = formatter.format(currentDate.getTime());

            final char H = ("right".equals(this.hand)) ? 'R' : 'L';
            String logFileNameWith16PacketPerRow = EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + EFDConstants.LOGS_DIRECTORY + File.separator
                    + "With" + EFDConstants.SAMPLE_PACKET_SIZE + "PacketPerRow_" + this.boxerName + "_-" + H + "-_" + dateNow + ".csv";
            //Log.d("~", "logFileNameWith16PacketPerRow: "+logFileNameWith16PacketPerRow);

            File myDirectory = new File(EFDConstants.EFD_COMMON_DATA_DIRECTORY, EFDConstants.LOGS_DIRECTORY);

            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            FileWriter fstreamFor16PacketPerRow = null;
            BufferedWriter bufferedWriterFor16PacketPerRow = null;
            fstreamFor16PacketPerRow = new FileWriter(logFileNameWith16PacketPerRow, true);
            bufferedWriterFor16PacketPerRow = new BufferedWriter(fstreamFor16PacketPerRow);
            bufferedWriterFor16PacketPerRow.write("StartByte" + "," + "MessageID" + "," + "MessageLength(lsb)" + "," + "MessageLength(msb)"
                    + "," + "Time0" + "," + "Time1" + "," + "Time2" + "," + "Time3" + "," + "Time" + "," + "AX" + "," + "AY" + "," + "AZ"
                    + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ"
                    + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ"
                    + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ"
                    + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ"
                    + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ" + "," + "AX" + "," + "AY" + "," + "AZ"
                    + "\n");

            while (isContinue) {

                ArrayList<Integer> packet = readPacketFromQueue();
                if (isreadFromCSVFile) {

                    if ((line = bufRdr.readLine()) != null) {
                        stringTokenizer = new StringTokenizer(line, ",");
                    } else {
                        csvDataReadingFinished = true;
                        break;
                    }
                    String startByteStr = stringTokenizer.nextToken();
                    startByte = Integer.parseInt(startByteStr);

                } else {
                    startByte = readDeviceDataByte(packet);
                }

                isContinue = (startByte != -1) && (!this.shouldStop);

                if (!isContinue) {
                    break;
                }

                isValidStartByte = (EFDConstants.START_BYTE == startByte);
                if (isValidStartByte) {
                    int messageId = 0;
                    if (isreadFromCSVFile) {
                        String messageIDString = stringTokenizer.nextToken();
                        if (messageIDString.endsWith(String.valueOf(EFDConstants.LOW_G_MODE))) {
                            messageId = EFDConstants.LOW_G_MODE;
                        } else if (messageIDString.endsWith(String.valueOf(EFDConstants.HIGH_G_MODE))) {
                            messageId = EFDConstants.HIGH_G_MODE;
                        } else if(messageIDString.endsWith(String.valueOf(EFDConstants.GYRO_MODE))) {
                            messageId = EFDConstants.GYRO_MODE;
                        }
                    } else {
                        messageId = readDeviceDataByte(packet);
                    }

                    String streamMode;
                    switch (messageId) {
                        case EFDConstants.BATTERY_MODE:
                            streamMode = "Battery";
                            break;
                        case EFDConstants.GYRO_MODE:
                            streamMode = "Gyro";
                            break;
                        case EFDConstants.LOW_G_MODE:
                            streamMode = "LowG";
                            break;
                        default:
                            streamMode = "HighG";
                    }

                    isValidMode = (EFDConstants.LOW_G_MODE == messageId || EFDConstants.HIGH_G_MODE == messageId || EFDConstants.BATTERY_MODE == messageId || EFDConstants.GYRO_MODE == messageId);

                    if (isValidMode) {
                        int time = 0;
                        int messageLengthLsb = isreadFromCSVFile ? Integer.parseInt(stringTokenizer.nextToken()) : readDeviceDataByte(packet);
                        int messageLengthMsb = isreadFromCSVFile ? Integer.parseInt(stringTokenizer.nextToken()) : readDeviceDataByte(packet);
                        // Combined time0-time3 for calculating
                        // time(milliseconds)
                        if (messageLengthLsb == EFDConstants.MESSAGE_LENGTH_LSB) {
                            int time0 = isreadFromCSVFile ? Integer.parseInt(stringTokenizer.nextToken()) : readDeviceDataByte(packet);
                            int time1 = isreadFromCSVFile ? Integer.parseInt(stringTokenizer.nextToken()) : readDeviceDataByte(packet);
                            int time2 = isreadFromCSVFile ? Integer.parseInt(stringTokenizer.nextToken()) : readDeviceDataByte(packet);
                            int time3 = isreadFromCSVFile ? Integer.parseInt(stringTokenizer.nextToken()) : readDeviceDataByte(packet);
                            time = isreadFromCSVFile ? Integer.parseInt(stringTokenizer.nextToken())
                                    : ((((((time3 << 8) | time2) << 8) | time1) << 8) | time0);

                            bufferedWriterFor16PacketPerRow.write(
                                    Integer.toString(startByte) + "," + streamMode + " - " + Integer.toString(messageId) + ",");
                            bufferedWriterFor16PacketPerRow.write(
                                    Integer.toString(messageLengthLsb) + "," + Integer.toString(messageLengthMsb) + "," + Integer.toString(time0)
                                            + "," + Integer.toString(time1) + "," + Integer.toString(time2) + "," + Integer.toString(time3) + ","
                                            + Integer.toString(time));


                            if (messageId == EFDConstants.LOW_G_MODE) {
                                short[][] data = new short[EFDConstants.SAMPLE_PACKET_SIZE][3];
//								Log.i(TAG, "Low G mode Time="+formatter.format(currentDate.getTime()));
                                for (int i = 0; i < EFDConstants.SAMPLE_PACKET_SIZE; i++) {
                                    sensorData[i] = new SensorData();
                                    sensorData[i].setHand(this.hand);
                                    sensorData[i].setPrevTime(sensorData[i].getMsgTime());
                                    sensorData[i].setMsgTime(time + (i * EFDConstants.LOWG_SAMPLE_TIME_DIFFERENCE));
                                    short ax = 0, ay = 0, az = 0;
                                    // Changes made for Gen4 sensor data reading
                                    // x = x; y = -y; z = -z;
                                    try {
                                        ax = isreadFromCSVFile ? (short) Integer.parseInt(stringTokenizer.nextToken())
                                                : (short) (((short) readWordBE(packet) >> 4) * 12);
                                        ay = isreadFromCSVFile ? (short) Integer.parseInt(stringTokenizer.nextToken())
                                                : (short) -(((short) readWordBE(packet) >> 4) * 12);    // Negate y (inverted y axis) for gen4
                                        az = isreadFromCSVFile ? (short) Integer.parseInt(stringTokenizer.nextToken())
                                                : (short) -(((short) readWordBE(packet) >> 4) * 12);    // Negate z (inverted z axis) for gen4
                                    } catch (Exception e) {
                                        if (isreadFromCSVFile) {
                                            csvDataReadingFinished = true;
                                            break;
                                        }
                                    }
                                    sensorData[i].setAx(ax);
                                    sensorData[i].setAy(ay);
                                    sensorData[i].setAz(az);
                                    punchVFAData.setHand(sensorData[i].getHand());

                                    bufferedWriterFor16PacketPerRow.write(
                                            "," + Integer.toString((int) sensorData[i].getAx()) + "," + Integer.toString((int) sensorData[i].getAy()) + ","
                                                    + Integer.toString((int) sensorData[i].getAz()));

                                    processLowGData(sensorData[i], punchDetector, lowGPunchDetetction, punchVFACalculatorOnYYAxis,
                                            punchVFACalculatorOnYZAxis, punchVFAData, (time + (i * EFDConstants.LOWG_SAMPLE_TIME_DIFFERENCE)), peakPunchValueDetector);

                                    data[i][0] = (short) sensorData[i].getAx();
                                    data[i][1] = (short) sensorData[i].getAy();
                                    data[i][2] = (short) sensorData[i].getAz();
                                }
                                sensorBuffer.appendSensorData(getSensorData(messageId, time, data));
                            }
                            if (messageId == EFDConstants.HIGH_G_MODE) {
                                short[][] data = new short[EFDConstants.SAMPLE_PACKET_SIZE][3];
//								Log.i(TAG, "high G mode time="+formatter.format(currentDate.getTime()));
                                for (int i = 0; i < EFDConstants.SAMPLE_PACKET_SIZE; i++) {
                                    sensorData[i] = new SensorData();
                                    sensorData[i].setHand(this.hand);
                                    punchVFAData.setHand(sensorData[i].getHand());
                                    sensorData[i].setPrevTime(sensorData[i].getMsgTime());
                                    sensorData[i].setMsgTime(time + i);
                                    try {
                                        // Changes made for Gen4 sensor data reading
                                        // x = y; y = x; z = -z;
                                        if (isreadFromCSVFile) {
                                            sensorData[i].setAx((short) Integer.parseInt(stringTokenizer.nextToken()));
                                            sensorData[i].setAy((short) Integer.parseInt(stringTokenizer.nextToken()));
                                        } else {
                                            sensorData[i].setAy((short) readWordBE(packet));    // Swap y with x for gen4
                                            sensorData[i].setAx((short) readWordBE(packet));    // Swap x with y for gen4
                                        }
                                        sensorData[i].setAz(isreadFromCSVFile ? (short) Integer.parseInt(stringTokenizer.nextToken())
                                                : -(short) readWordBE(packet));    // Negate z (inverted z axis) for gen4
                                    } catch (Exception e) {
                                        if (isreadFromCSVFile) {
                                            csvDataReadingFinished = true;
                                            break;
                                        }
                                    }

                                    data[i][0] = (short) sensorData[i].getAx();
                                    data[i][1] = (short) sensorData[i].getAy();
                                    data[i][2] = (short) sensorData[i].getAz();

                                    bufferedWriterFor16PacketPerRow.write(
                                            "," + Integer.toString((int) sensorData[i].getAx()) + "," + Integer.toString((int) sensorData[i].getAy()) + ","
                                                    + Integer.toString((int) sensorData[i].getAz()));

                                    processHighGData(peakPunchValueDetector, sensorData[i], punchVFACalculatorOnYYAxis, punchVFACalculatorOnYZAxis,
                                            punchVFAData, (time + i), lowGPunchDetetction, punchDetector);
                                }
                                sensorBuffer.appendSensorData(getSensorData(messageId, time, data));
                            }

                            if(messageId == EFDConstants.GYRO_MODE) {
                                short[][] data = new short[EFDConstants.SAMPLE_PACKET_SIZE][3];

                                for (int i = 0; i < EFDConstants.SAMPLE_PACKET_SIZE; i++) {
                                    sensorData[i] = new SensorData();
                                    short ax = 0, ay = 0, az = 0;
                                    // Changes made for Gen4 sensor data reading
                                    // x = x; y = -y; z = -z;
                                    try {
                                        ax = isreadFromCSVFile ? (short) Integer.parseInt(stringTokenizer.nextToken())
                                                : (short) (((short) readWordBE(packet) >> 4) * 12);
                                        ay = isreadFromCSVFile ? (short) Integer.parseInt(stringTokenizer.nextToken())
                                                : (short) -(((short) readWordBE(packet) >> 4) * 12);    // Negate y (inverted y axis) for gen4
                                        az = isreadFromCSVFile ? (short) Integer.parseInt(stringTokenizer.nextToken())
                                                : (short) -(((short) readWordBE(packet) >> 4) * 12);    // Negate z (inverted z axis) for gen4
                                    } catch (Exception e) {
                                        if (isreadFromCSVFile) {
                                            csvDataReadingFinished = true;
                                            break;
                                        }
                                    }
                                    sensorData[i].setAx(ax);
                                    sensorData[i].setAy(ay);
                                    sensorData[i].setAz(az);

                                    bufferedWriterFor16PacketPerRow.write(
                                            "," + Integer.toString((int) sensorData[i].getAx()) + "," + Integer.toString((int) sensorData[i].getAy()) + ","
                                                    + Integer.toString((int) sensorData[i].getAz()));

                                    data[i][0] = (short) sensorData[i].getAx();
                                    data[i][1] = (short) sensorData[i].getAy();
                                    data[i][2] = (short) sensorData[i].getAz();
                                }
                                sensorBuffer.appendSensorData(getSensorData(messageId, time, data));
                            }
                        } else if (messageLengthLsb == EFDConstants.MESSAGE_LENGTH_BATTERY) {
                            int battery = readWordBE(packet);

                            float batteryData = (float) battery / 1000; //voltage is calculated in volt
                            battery = (battery - 3000) / 11;    //voltage is calculated in %
                            DecimalFormat df = new DecimalFormat("#.#");
                            String batteryVoltage = "{\"success\":true,\"batteryVoltage\":" + battery + ",\"hand\":" + this.hand + "}";
                            Message msg = mhandler.obtainMessage(3);
                            Bundle bundle = new Bundle();
                            bundle.putString("batteryVoltage", batteryVoltage);
                            msg.setData(bundle);
                            mhandler.sendMessage(msg);
                        }
                        bufferedWriterFor16PacketPerRow.write("\n");

                    }
                    if (isreadFromCSVFile) {
                        Thread.sleep(30);// delay for reading CSV file data
                    }

                    //continue;
                }
//				Log.i("DataProcessingThread", "Time--------------="+formatter.format(currentDate.getTime())+" SensorData size="+senserDatablockingQueue.size());

            }// end while
            bufferedWriterFor16PacketPerRow.close();
            if (isreadFromCSVFile) {
                if (csvDataReadingFinished) {
                    Log.i(TAG, "Data Reading finished");
                    bufRdr.close();
                }
            }

        } catch (IOException e) {
            if (!this.shouldStop) {
                System.err.println("GloveMessageReader exting due to IOException");
                System.err.println("IOException in " + getClass().getCanonicalName() + ": ");
                e.printStackTrace(System.err);
            }
        }
        this.shouldStop = true;
    }

    protected String getCommonLoggerPattern() {
        return "";
    }

    /**
     * Method for processing lowG data from device.
     *
     * @param sensorData
     * @param punchDetector
     * @param lowGPunchDetetction
     * @param punchVFACalculatorOnYYAxis
     * @param punchVFACalculatorOnYZAxis
     * @param punchVFAData
     * @param time
     * @param peakPunchValueDetector
     * @throws IOException
     */
    private void processLowGData(SensorData sensorData, PunchDetector punchDetector, LowGPunchDetection lowGPunchDetetction,
                                 PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis, PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis, PunchVFAData punchVFAData,
                                 double time, PeakPunchValueDetector peakPunchValueDetector) throws IOException {


        lowGPunchDetetction.detectPunchType(sensorData, punchDetector, stance, getCommonLoggerPattern());

        if (punchDetector.isJab()) {
            punchVFAData.setJabFound(punchDetector.isJab());
            punchVFAData.setPunchFound(punchDetector.isJab());
        } else if (punchDetector.isStraight()) {
            punchVFAData.setStraightFound(punchDetector.isStraight());
            punchVFAData.setPunchFound(punchDetector.isStraight());
        } else if (punchDetector.isHook()) {
            punchVFAData.setHookFound(punchDetector.isHook());
            punchVFAData.setPunchFound(punchDetector.isHook());

        } else if (punchDetector.isUpperCut()) {
            punchVFAData.setUpperCutFound(punchDetector.isUpperCut());
            punchVFAData.setPunchFound(punchDetector.isUpperCut());
        } else if (punchDetector.isUnrecognized()) {
            punchVFAData.setUnrecognizedPunch(punchDetector.isUnrecognized());
            punchVFAData.setPunchFound(punchDetector.isUnrecognized());
        }
        if (punchVFAData.isPunchFound() && !lowGPunchDetetction.getPunchDetectedMap().containsKey(punchDetector.getPunchDetectedTime())) {
            lowGPunchDetetction.getPunchDetectedMap().put(punchDetector.getPunchDetectedTime(), punchVFAData);
        }
    }

    /**
     * Method for processing highG data
     *
     * @param peakPunchValueDetector
     * @param sensorData
     * @param punchVFACalculatorOnYYAxis
     * @param punchVFACalculatorOnYZAxis
     * @param punchVFAData
     * @param time
     * @throws IOException
     */
    private void processHighGData(PeakPunchValueDetector peakPunchValueDetector, SensorData sensorData, PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis,
                                  PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis, PunchVFAData punchVFAData, double time,
                                  LowGPunchDetection lowGPunchDetetction, PunchDetector punchDetector) throws IOException {

        punchVFACalculatorOnYYAxis.estimateVelocity(sensorData, getCommonLoggerPattern());
        punchVFACalculatorOnYZAxis.estimateVelocity(sensorData, getCommonLoggerPattern());

        PunchDetectionConfig punchDetectionConfig = PunchDetectionConfig.getInstance();
        double punchTime = getNearestLowGPunchTime(lowGPunchDetetction, (time - punchDetectionConfig.getVfaBufferSize()));
        if (punchTime > 0) {
            // For yy Axis............
            VFAMap vfaMapOnYY = null;
            try {
                vfaMapOnYY = new VFAMap();
                vfaMapOnYY.putAll(punchVFACalculatorOnYYAxis.getVfaMap().subMap((punchTime - punchDetectionConfig.getVfaBufferSize()), true, (punchTime + punchDetectionConfig.getVfaBufferSize()), true));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (vfaMapOnYY != null && vfaMapOnYY.size() > 0) {
                punchVFAData.setVelocityFoundOnYYAxis(true);
                VFA vfaOnYY = vfaMapOnYY.getMaxValueOfVFA();
                punchVFAData.setVelocityOnYYAxis(vfaOnYY.getVxyzMPH());
                punchVFAData.setForceOnYYAxis(vfaOnYY.getForceLSB());
                punchVFAData.setAccOnYYAxis(vfaOnYY.getAxyz());
                punchVFAData.setVelocity(vfaOnYY.getVxyzMPH());
                punchVFAData.setForce(vfaOnYY.getForceLSB());
            }
           /* //For yz Axis............
            VFAMap vfaMapOnYZ = null;
            try {
                vfaMapOnYZ = new VFAMap();
                vfaMapOnYZ.putAll(punchVFACalculatorOnYZAxis.getVfaMap().subMap((punchTime - punchDetectionConfig.getVfaBufferSize()), true, (punchTime + punchDetectionConfig.getVfaBufferSize()), true));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (vfaMapOnYZ != null && vfaMapOnYZ.size() > 0) {
                punchVFAData.setVelocityFoundOnYZAxis(true);
                VFA vfaOnYZ = vfaMapOnYZ.getMaxValueOfVFA();
                punchVFAData.setVelocityOnYZAxis(vfaOnYZ.getVxyzMPH());
                punchVFAData.setForceOnYZAxis(vfaOnYZ.getForceLSB());
                punchVFAData.setAccOnYZAxis(vfaOnYZ.getAxyz());
                punchVFAData.setVelocity(vfaOnYZ.getVxyzMPH());
                punchVFAData.setForce(vfaOnYZ.getForceLSB());
            }*/
            if (punchVFAData.isVelocityFoundOnYYAxis() /*|| punchVFAData.isVelocityFoundOnYZAxis()*/) {
                sendHighGAndLowGProcessedData(punchDetector, punchVFACalculatorOnYYAxis, punchVFACalculatorOnYZAxis,
                        punchVFAData, sensorData, peakPunchValueDetector, lowGPunchDetetction.getPunchDetectedMap(), punchTime);
            }
        }
    }

    /**
     * Method set velocity & force depending on Y/Z axis in bean according to punch type
     *
     * @param punchVFAData
     * @param isSendMatchData
     * @return
     * @throws IOException
     */
    private boolean isValidPunchFound(PunchVFAData punchVFAData, boolean isSendMatchData) throws IOException {
        if ((punchVFAData.isJabFound() || punchVFAData.isStraightFound()) && punchVFAData.isVelocityFoundOnYYAxis()) {
            punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
            punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
            isSendMatchData = true;
        } else if (punchVFAData.isHookFound() && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())) {
            if (punchVFAData.getVelocityOnYZAxis() != 0) {
                punchVFAData.setVelocity(punchVFAData.getVelocityOnYZAxis());
            } else {
                punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
            }
            if (punchVFAData.getForceOnYZAxis() != 0) {
                punchVFAData.setForce(punchVFAData.getForceOnYZAxis());
            } else {
                punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
            }
            isSendMatchData = true;
        } else if (punchVFAData.isUpperCutFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
            punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
            punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
            isSendMatchData = true;
        } else if (punchVFAData.isUnrecognizedPunch() && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())) {
            if (punchVFAData.getVelocityOnYYAxis() != 0) {
                punchVFAData.setVelocity(punchVFAData.getVelocityOnYYAxis());
            } else {
                punchVFAData.setVelocity(punchVFAData.getVelocityOnYZAxis());
            }
            if (punchVFAData.getForceOnYYAxis() != 0) {
                punchVFAData.setForce(punchVFAData.getForceOnYYAxis());
            } else {
                punchVFAData.setForce(punchVFAData.getForceOnYZAxis());
            }
            isSendMatchData = true;
        }

        return isSendMatchData;
    }

    /**
     * Check for :- punch type get detected on LowG & Velocity get detected on HighG. If punch get detected then send it to server otherwise ignore it.
     *
     * @param punchDetector
     * @param punchVFACalculatorOnYYAxis
     * @param punchVFACalculatorOnYZAxis
     * @param punchVFAData
     * @param sensorDataObj
     * @param peakPunchValueDetector
     * @return
     * @throws IOException
     */
    private void sendHighGAndLowGProcessedData(PunchDetector punchDetector, PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis,
                                               PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis, PunchVFAData punchVFAData,
                                               SensorData sensorDataObj, PeakPunchValueDetector peakPunchValueDetector, PunchDetectedMap punchDetectedMap, double punchTime)
            throws IOException {
        boolean isSendMatchData = false;
        if (punchVFAData.isPunchFound()) {
            //Reset data when slap punch is detected
            if ((punchVFACalculatorOnYYAxis.isSlapPunch() || punchVFACalculatorOnYZAxis.isSlapPunch())) {
                resetAllData(punchDetector, punchVFACalculatorOnYYAxis, punchVFACalculatorOnYZAxis, punchVFAData, sensorDataObj, punchDetectedMap, punchTime);
                return;
            }
            isSendMatchData = isValidPunchFound(punchVFAData, isSendMatchData);
            if (isSendMatchData) {
                if (punchVFAData.getVelocity() > PunchDetectionConfig.getInstance().getMaxVelocity() || punchVFAData.getVelocity() < PunchDetectionConfig.getInstance().getMinVelocity()
                        || punchVFAData.getForce() > PunchDetectionConfig.getInstance().getMaxForce() || punchVFAData.getForce() < PunchDetectionConfig.getInstance().getMinForce()) {

                    Log.i(TAG, getCommonLoggerPattern() + "Not sending Data to Arena System .... =====> velocity = " + punchVFAData.getVelocity() + " force = "
                            + punchVFAData.getForce());
                } else {
                    if (punchVFAData.isJabFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
                        punchVFAData.setJabVelocity(punchVFAData.getVelocity());
                        punchVFAData.setJabForce(punchVFAData.getForce());
                        punchVFAData.setJabAcc(punchVFAData.getAccOnYYAxis());
                        if (punchVFAData.getHand().equalsIgnoreCase(EFDConstants.LEFT_HAND)) {
                            punchVFAData.setPunchType(EFDConstants.LEFT_JAB);
                        } else {
                            punchVFAData.setPunchType(EFDConstants.RIGHT_JAB);
                        }
//							this.setChanged();
//							this.notifyObservers("Jab Punch Detected...");
                    } else if (punchVFAData.isStraightFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
                        punchVFAData.setStraightVelocity(punchVFAData.getVelocity());
                        punchVFAData.setStraightForce(punchVFAData.getForce());
                        punchVFAData.setStraightAcc(punchVFAData.getAccOnYYAxis());
                        if (punchVFAData.getHand().equalsIgnoreCase(EFDConstants.LEFT_HAND)) {
                            punchVFAData.setPunchType(EFDConstants.LEFT_STRAIGHT);
                        } else {
                            punchVFAData.setPunchType(EFDConstants.RIGHT_STRAIGHT);
                        }
//							this.setChanged();
//							this.notifyObservers("Straight Punch Detected...");
                    } else if (punchVFAData.isHookFound() && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())) {
                        punchVFAData.setHookVelocity(punchVFAData.getVelocity());
                        punchVFAData.setHookForce(punchVFAData.getForce());
                        if (punchVFAData.getAccOnYZAxis() != 0) {
                            punchVFAData.setHookAcc(punchVFAData.getAccOnYZAxis());
                        } else {
                            punchVFAData.setHookAcc(punchVFAData.getAccOnYYAxis());
                        }
                        if (punchVFAData.getHand().equalsIgnoreCase(EFDConstants.LEFT_HAND)) {
                            punchVFAData.setPunchType(EFDConstants.LEFT_HOOK);
                        } else {
                            punchVFAData.setPunchType(EFDConstants.RIGHT_HOOK);
                        }
//							this.setChanged();
//							this.notifyObservers("Hook Punch Detected...");
                    } else if (punchVFAData.isUpperCutFound() && punchVFAData.isVelocityFoundOnYYAxis()) {
                        punchVFAData.setUpperCutVelocity(punchVFAData.getVelocity());
                        punchVFAData.setUpperCutForce(punchVFAData.getForce());
                        punchVFAData.setUpperCutAcc(punchVFAData.getAccOnYYAxis());
                        if (punchVFAData.getHand().equalsIgnoreCase(EFDConstants.LEFT_HAND)) {
                            //punchVFAData.setPunchType(EFDConstants.LEFT_UPPERCUT);         //commented to avoid left upper-cut punches (Note:-  Please uncomment it when uppercut punch shown)
                            punchVFAData.setPunchType(EFDConstants.LEFT_UNRECOGNIZED);        //delete it when left upper-cut punch shown
                        } else {
                            //punchVFAData.setPunchType(EFDConstants.RIGHT_UPPERCUT);          //commented to avoid right upper-cut punches (Note:-  Please uncomment it when uppercut punch shown)
                            punchVFAData.setPunchType(EFDConstants.RIGHT_UNRECOGNIZED);            //delete it when left upper-cut punch shown
                        }
//							this.setChanged();
//							this.notifyObservers("Upper cut Punch Detected...");

                    } else if (punchVFAData.isUnrecognizedPunch() && (punchVFAData.isVelocityFoundOnYYAxis() || punchVFAData.isVelocityFoundOnYZAxis())) {
                        punchVFAData.setUnrecognizedVelocity(punchVFAData.getVelocity());
                        punchVFAData.setUnrecognisedForce(punchVFAData.getForce());
                        if (punchVFAData.getAccOnYYAxis() != 0) {
                            punchVFAData.setUnrecognisedAcc(punchVFAData.getAccOnYYAxis());
                        } else if (punchVFAData.getAccOnYZAxis() != 0) {
                            punchVFAData.setUnrecognisedAcc(punchVFAData.getAccOnYZAxis());
                        }
                        if (punchVFAData.getHand().equalsIgnoreCase(EFDConstants.LEFT_HAND)) {
                            punchVFAData.setPunchType(EFDConstants.LEFT_UNRECOGNIZED);
                        } else {
                            punchVFAData.setPunchType(EFDConstants.RIGHT_UNRECOGNIZED);
                        }
                    }
                    //	setWebServiceData(punchVFAData, webserviceData, sensorDataObj, peakPunchValueDetector);
                    Log.i(TAG, getCommonLoggerPattern() + "Arena System sending.... =====> velocity = " + punchVFAData.getVelocity() + " force = "
                            + punchVFAData.getForce() + "Punch Type=" + punchVFAData.getPunchType());

                    /**if (!mainActivityInstance.isGuestBoxerActive()) {
                        savePunchData(punchVFAData, trainingId);
                        saveMatchDataDetails(punchVFAData, sensorDataObj, trainingId);
                        savePunchPeakSummary(punchVFAData, peakPunchValueDetector, trainingId);
                    }
                    sendMatchData(punchVFAData);*/

                    Log.d("~", "Punch detected!");
                    punchVFAData.setDataSample(new SensorDataSample(
                            (int) sensorDataObj.getMsgTime(),
                            (int) sensorDataObj.getAx(),
                            (int) sensorDataObj.getAy(),
                            (int) sensorDataObj.getAz(),
                            sensorDataObj.getTemperature(),
                            sensorDataObj.getGx(),
                            sensorDataObj.getGy(),
                            sensorDataObj.getGz(),
                            punchDetector.getPunchDetectedTime()
                    ));

                    sensorBuffer.calcPunch(punchVFAData);


                    this.setChanged();
                    this.notifyObservers("Punch Detected...");
                }
                resetAllData(punchDetector, punchVFACalculatorOnYYAxis, punchVFACalculatorOnYZAxis, punchVFAData, sensorDataObj, punchDetectedMap, punchTime);
            }
        }
        return;
    }

    /**
     * @param punchDetector
     * @param punchVFACalculatorOnYYAxis
     * @param punchVFACalculatorOnYZAxis
     * @param punchVFAData
     * @param sensorDataObj
     */
    private void resetAllData(PunchDetector punchDetector,
                              PunchVFACalculatorOnYYAxis punchVFACalculatorOnYYAxis,
                              PunchVFACalculatorOnYZAxis punchVFACalculatorOnYZAxis,
                              PunchVFAData punchVFAData, SensorData sensorDataObj, PunchDetectedMap punchDetectedMap, double punchTime) {
//		Log.i(TAG, "vfaMap on YY before reset= "+punchVFACalculatorOnYYAxis.getVfaMap());
//		Log.i(TAG, "vfaMap on YZ before reset= "+punchVFACalculatorOnYZAxis.getVfaMap());
//		Log.i(TAG, "PunchDetectedMap before reset= "+punchDetectedMap);

        sensorDataObj.resetSensorData();
        punchVFAData.resetPunchVFAData();
        punchVFACalculatorOnYYAxis.resetVFA();
        punchVFACalculatorOnYZAxis.resetVFA();
        punchDetector.resetPunchDetector();
        punchVFACalculatorOnYYAxis.resetBufferAfterPunchFound(getCommonLoggerPattern(), punchTime);
        punchVFACalculatorOnYZAxis.resetBufferAfterPunchFound(getCommonLoggerPattern(), punchTime);
        punchDetectedMap.removeContentBeforeTime(punchTime);
        Log.i(TAG, "--------------------------------Reseted all data---------------------------------");
//		Log.i(TAG, "vfaMap on YY After reset= "+punchVFACalculatorOnYYAxis.getVfaMap());
//		Log.i(TAG, "vfaMap on YZ After reset= "+punchVFACalculatorOnYZAxis.getVfaMap());
//		Log.i(TAG, "PunchDetectedMap after reset= "+punchDetectedMap);
    }

    private double getNearestLowGPunchTime(LowGPunchDetection lowGPunchDetection, double time) {
        Double punchTime = null;
        try {
            punchTime = lowGPunchDetection.getPunchDetectedMap().lowerKey(time);
            if (punchTime == null) {
                return 0;
            }

        } catch (Exception e) {
            return 0;
        }

        return punchTime;
    }


    private void savePunchPeakSummary(PunchVFAData punchVFAData,
                                      PeakPunchValueDetector peakPunchValueDetector, Integer trainingId) {


        if (punchVFAData.isJabFound()) {
            Integer currentSpeedJab = (int) Math.round(punchVFAData.getVelocity());
            if (currentSpeedJab > 0 && currentSpeedJab > Integer.parseInt(peakPunchValueDetector.getMaxSpeedJab())) {
                peakPunchValueDetector.setMaxSpeedJab(currentSpeedJab.toString());
                saveMatchDataPeakSummary("0", peakPunchValueDetector.getMaxSpeedJab(), "0", "0", true, trainingId);
            }
            Integer currentPsiJab = (int) Math.round(punchVFAData.getForce());
            if (currentPsiJab > 0 && currentPsiJab > Integer.parseInt(peakPunchValueDetector.getMaxPsiJab())) {
                peakPunchValueDetector.setMaxPsiJab(currentPsiJab.toString());
                saveMatchDataPeakSummary("0", peakPunchValueDetector.getMaxPsiJab(), "0", "0", false, trainingId);
            }
        } else if (punchVFAData.isStraightFound()) {
            Integer currentSpeedStraight = (int) Math.round(punchVFAData.getVelocity());
            if (currentSpeedStraight > 0 && currentSpeedStraight > Integer.parseInt(peakPunchValueDetector.getMaxSpeedStraight())) {
                peakPunchValueDetector.setMaxSpeedStraight(currentSpeedStraight.toString());
                saveMatchDataPeakSummary(peakPunchValueDetector.getMaxSpeedStraight(), "0", "0", "0", true, trainingId);
            }
            Integer currentPsiStraight = (int) Math.round(punchVFAData.getForce());
            if (currentPsiStraight > 0 && currentPsiStraight > Integer.parseInt(peakPunchValueDetector.getMaxPsiStraight())) {
                peakPunchValueDetector.setMaxPsiStraight(currentPsiStraight.toString());
                saveMatchDataPeakSummary(peakPunchValueDetector.getMaxPsiStraight(), "0", "0", "0", false, trainingId);
            }
        } else if (punchVFAData.isHookFound()) {
            Integer currentSpeedHook = (int) Math.round(punchVFAData.getVelocity());
            if (currentSpeedHook > 0 && currentSpeedHook > Integer.parseInt(peakPunchValueDetector.getMaxSpeedHook())) {
                peakPunchValueDetector.setMaxSpeedHook(currentSpeedHook.toString());
                saveMatchDataPeakSummary("0", "0", peakPunchValueDetector.getMaxSpeedHook(), "0", true, trainingId);
            }
            Integer currentPsiHook = (int) Math.round(punchVFAData.getForce());
            if (currentPsiHook > 0 && currentPsiHook > Integer.parseInt(peakPunchValueDetector.getMaxPsiHook())) {
                peakPunchValueDetector.setMaxPsiHook(currentPsiHook.toString());
                saveMatchDataPeakSummary("0", "0", peakPunchValueDetector.getMaxPsiHook(), "0", false, trainingId);
            }
        } else if (punchVFAData.isUpperCutFound()) {
            Integer currentSpeedUpperCut = (int) Math.round(punchVFAData.getVelocity());
            if (currentSpeedUpperCut > 0 && currentSpeedUpperCut > Integer.parseInt(peakPunchValueDetector.getMaxSpeedUpper())) {
                peakPunchValueDetector.setMaxSpeedUpper(currentSpeedUpperCut.toString());
                saveMatchDataPeakSummary("0", "0", "0", peakPunchValueDetector.getMaxSpeedUpper(), true, trainingId);
            }
            Integer currentForceUpperCut = (int) Math.round(punchVFAData.getForce());
            if (currentForceUpperCut > 0 && currentForceUpperCut > Integer.parseInt(peakPunchValueDetector.getMaxPsiUpper())) {
                peakPunchValueDetector.setMaxPsiUpper(currentForceUpperCut.toString());
                saveMatchDataPeakSummary("0", "0", "0", peakPunchValueDetector.getMaxPsiUpper(), false, trainingId);
            }
        }

    }

    public String[] getResponseFromWebService(String url, Map<String, String> punchdata) {
        String webServiceUrl = PunchDetectionConfig.getInstance().getWEB_SERVICE_DOMAIN();

        RestClient client = new RestClient(webServiceUrl + "/" + url);
        try {
            //client.AddParam("jsonData", "jsonData");

            for (Map.Entry<String, String> punch : punchdata.entrySet()) {
                client.AddParam(punch.getKey(), punch.getValue());
            }

            client.Execute(RestClient.RequestMethod.POST);

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        String[] response = {client.getResponse()};
        Log.d(TAG, "response in GetResponse = " + response.toString());
        return response;
    }

    private void saveMatchDataPeakSummary(String maxSpeedStraight, String maxSpeedJab,
                                          String maxSpeedHook, String maxSpeedUpper, boolean isSpeed, Integer trainingId) {

        String isSpeedFlag = (isSpeed) ? "1" : "0";

        JSONObject result_trainingPunchDataPeakSummary_save = db.trainingPunchDataPeakSummarySave(maxSpeedStraight, maxSpeedHook, maxSpeedJab, maxSpeedUpper, isSpeedFlag, trainingId);
    }

    private void saveMatchDataDetails(PunchVFAData punchVFAData, SensorData sensorData, Integer trainingId) {

        Date dateRecieved = new Date();
        java.sql.Timestamp dataReceiveTime = new java.sql.Timestamp(dateRecieved.getTime());

        JSONObject result_trainingDataDetails_save =
                db.trainingDataDetailsSave(Double.valueOf(sensorData.getAx()).intValue(), Double.valueOf(sensorData.getAy()).intValue(), Double.valueOf(sensorData.getAz()).intValue(), punchVFAData.getForce(), punchVFAData.getVelocity(),
                        dataReceiveTime, Double.valueOf(sensorData.getGx()).intValue(), Double.valueOf(sensorData.getGy()).intValue(), Double.valueOf(sensorData.getGz()).intValue(), 0, sensorData.getMsgTime(), sensorData.getTemperature(),
                        trainingId);
    }

    private void savePunchData(PunchVFAData punchVFAData, Integer trainingId) {
        Date currentTime = new Date();
        java.sql.Timestamp punchTime = new java.sql.Timestamp(currentTime.getTime());

        JSONObject result_trainingPunchData_save = db.trainingPunchDataSave(punchVFAData.getPunchType(), punchVFAData.getForce(), punchVFAData.getVelocity(), punchTime, trainingId);
    }

    /**
     * Method sending match data to client
     *
     */
    private void sendMatchData(PunchVFAData punVfaData) {
        String jsonData = "{" +
                "\"success\":true," +
                "\"jsonObject\":{" +
                "\"boxerName\":\"" + boxerName + "\"," +
                "\"hand\":\"" + hand + "\"," +
                "\"punchType\":\"" + punVfaData.getPunchType() + "\"," +
                "\"headTrauma\":\"" + Math.round(0) + "\"," +
                "\"speed\":\"" + Math.round(punVfaData.getVelocity()) + "\"," +
                "\"force\":\"" + Math.round(punVfaData.getForce()) + "\"," +
                "\"boxerStatics\":{" +
                "\"maxSpeed\":{" +
                "\"straight\":\"" + punVfaData.getStraightVelocity() + "\"," +
                "\"jab\":\"" + punVfaData.getJabVelocity() + "\"," +
                "\"hook\":\"" + punVfaData.getHookVelocity() + "\"," +
                "\"upper\":\"" + punVfaData.getUpperCutVelocity() + "\"" +
                "}," +
                "\"maxPsi\":{" +
                "\"straight\":\"" + punVfaData.getStraightForce() + "\"," +
                "\"jab\":\"" + punVfaData.getJabForce() + "\"," +
                "\"hook\":\"" + punVfaData.getHookForce() + "\"," +
                "\"upper\":\"" + punVfaData.getUpperCutForce() + "\"" +
                "}" +
                "}" +
                "}" +
                "}";


        if (punVfaData.getPunchType().equals(EFDConstants.LEFT_JAB) || punVfaData.getPunchType().equals(EFDConstants.RIGHT_JAB)) {
            mainActivityInstance.punchDataDTO.setJabCount(mainActivityInstance.punchDataDTO.getJabCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        } else if (punVfaData.getPunchType().equals(EFDConstants.LEFT_STRAIGHT) || punVfaData.getPunchType().equals(EFDConstants.RIGHT_STRAIGHT)) {
            mainActivityInstance.punchDataDTO.setStraightCount(mainActivityInstance.punchDataDTO.getStraightCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        } else if (punVfaData.getPunchType().equals(EFDConstants.LEFT_HOOK)) {
            mainActivityInstance.punchDataDTO.setLeftHookCount(mainActivityInstance.punchDataDTO.getLeftHookCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        } else if (punVfaData.getPunchType().equals(EFDConstants.RIGHT_HOOK)) {
            mainActivityInstance.punchDataDTO.setRightHookCount(mainActivityInstance.punchDataDTO.getRightHookCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        } else if (punVfaData.getPunchType().equals(EFDConstants.LEFT_UPPERCUT)) {
            mainActivityInstance.punchDataDTO.setLeftUppercutCount(mainActivityInstance.punchDataDTO.getLeftUppercutCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        } else if (punVfaData.getPunchType().equals(EFDConstants.RIGHT_UPPERCUT)) {
            mainActivityInstance.punchDataDTO.setRightUppercutCount(mainActivityInstance.punchDataDTO.getRightUppercutCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        } else if (punVfaData.getPunchType().equals(EFDConstants.LEFT_UNRECOGNIZED)) {
            mainActivityInstance.punchDataDTO.setLeftUnrecognizedCount(mainActivityInstance.punchDataDTO.getLeftUnrecognizedCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        } else if (punVfaData.getPunchType().equals(EFDConstants.RIGHT_UNRECOGNIZED)) {
            mainActivityInstance.punchDataDTO.setRightUnrecognizedCount(mainActivityInstance.punchDataDTO.getRightUnrecognizedCount() + 1);
            mainActivityInstance.punchDataDTO.setTotalPunchCount(mainActivityInstance.punchDataDTO.getTotalPunchCount() + 1);
        }
        Log.i(TAG, "After  sendMatchData......" + formatter.format(currentDate.getTime()));
        //uncomment below code to show unrecognized punches in live screen
        /*if (punVfaData.getPunchType().endsWith("RR") || punVfaData.getPunchType().endsWith("LR")) {
			String punchType = "";
			if (punVfaData.getPunchType().endsWith("RR")) {
				punchType = "Right Unrecognized";
			} else if (punVfaData.getPunchType().endsWith("LR")) {
				punchType = "Left Unrecognized";
			}
			String infoMsg = punchType + " Punch Detected";
			Message msg = mhandler.obtainMessage(MainActivity.MESSAGE_TOAST);
			Bundle bundle = new Bundle();
			bundle.putString("TOAST", infoMsg);
			bundle.putString("CONNECTION", "failure");
			msg.setData(bundle);
			mhandler.sendMessage(msg);
		} else {*/
        Message msg = mhandler.obtainMessage(3);
        Bundle bundle = new Bundle();
        bundle.putString("jsonData", jsonData);
        msg.setData(bundle);
        mhandler.sendMessage(msg);
//		}
    }

    @Override
    public void punchOccurred(PunchDetails punchDetails) {

        /**** super added this code ****
         * this thread is alive evenif training is finished, so send data only when round starts**/
        if (!mainActivityInstance.trainingManager.isTrainingRunning()) {
            return;
        }

        /************************************/

        Log.d("~", "punchOccurred callback!");
        Log.d("~", "punchDetails: " + punchDetails.toString());

        PunchVFAData punchVFAData = punchDetails.getPuncVFAData();
        Log.d("~", "punchVFAData: "+punchVFAData);

        SensorData sensorData = new SensorData();
        SensorDataSample sensorDataSample = punchVFAData.getDataSample();
        sensorData.setAx(sensorDataSample.getAx());
        sensorData.setAy(sensorDataSample.getAy());
        sensorData.setAz(sensorDataSample.getAz());
        sensorData.setGx((short) sensorDataSample.getGx());
        sensorData.setGy((short) sensorDataSample.getGy());
        sensorData.setGz((short) sensorDataSample.getGz());
        sensorData.setMsgTime(sensorDataSample.getMsgTime());
        sensorData.setTemperature((short)sensorDataSample.getTemperature());

        if (!mainActivityInstance.isGuestBoxerActive()) {
            savePunchData(punchVFAData, trainingId);
            saveMatchDataDetails(punchVFAData, sensorData, trainingId);
            savePunchPeakSummary(punchVFAData, punchDetails.getPeakPunchValueDetector(), trainingId);
        }

        sendMatchData(punchVFAData);
    }

    private SensorDataPackage getSensorData(final int type, final long time, final short[][] data) {
        return new SensorDataPackage() {

            @Override
            public void setSensorType(int type) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setSamples(short[][] data) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setPackageTime(long time) {
            }

            @Override
            public int getSensorType() {
                return type;
            }

            @Override
            public short[][] getSamples() {
                return data;
            }

            @Override
            public long getPackageTime() {
                return time;
            }

            @Override
            public void clear() {
                // TODO Auto-generated method stub

            }
        };
    }
}
