package com.efd.striketectablet.DTO;

import android.util.Log;

/**
 * Created by erich on 4/24/2017.
 */

public class Quaternion {

    public static int MaxSetting = 50;
    public static int MinSetting = 10;
    public static int DistanceSetting = 200;

    public static Quaternion create() {

        return new Quaternion(MaxSetting, MinSetting, DistanceSetting);
    }

    public int getMaxPotentials() {
        return maxPotentials;
    }

    public int getMinMatching() {
        return minMatching;
    }

    public int getQuietValue() {
        return quietValue;
    }

    private int maxPotentials;
    private int minMatching;
    private int quietValue;

    public Quaternion(int maxPotentials, int minMatching, int quietValue) {
        this.maxPotentials = maxPotentials;
        this.minMatching = minMatching;
        this.quietValue = quietValue;

        potentialCheckpoints = new double[maxPotentials];
    }

    public Quaternion() {
        this(50, 10, 200);
    }

    //  a is the lowG acceleration, A (capitalized) is the highG acceleration.
    //        a_xyz = (ax,ay,az)
    // magnitude(a_xyz) = sqrt(a_xyz[0]^2 + a_xyx[1]^2 + a_xyz[2]^2)^0.5
    // if (abs(magnitude(a_xyz_) - g_counts_on_lowG) < close_enough_criteria):

    double calculatedValue = -1;
    double lastCalculatedValue = -1;
    boolean goodForCheckpoint = false;

    double[] potentialCheckpoints;
    int potentialIndex = 0;

    public double doCalculation(double aX, double aY, double aZ) {

        calculatedValue = Math.abs(Math.sqrt(Math.pow(aX, 2) + Math.pow(aY, 2) + Math.pow(aZ, 2)));

        if (lastCalculatedValue == -1)
        {
            lastCalculatedValue = calculatedValue;
        }
        else {

            if (Math.abs(calculatedValue - lastCalculatedValue) <= quietValue) {
                goodForCheckpoint = true;

                Log.e("Checkpoint-Quiet", "" + aX + "," + aY + "," + aZ + "," + calculatedValue);
            }
            else {
                goodForCheckpoint = false;

                Log.e("Checkpoint-Noisy", "" + aX + "," + aY + "," + aZ + "," + calculatedValue);

                if (potentialIndex == maxPotentials)
                {
                    for (int index = 0; index < maxPotentials - 2; index++) {
                        potentialCheckpoints[index] = potentialCheckpoints[index + 1];
                    }
                    potentialIndex--;
                    potentialCheckpoints[potentialIndex] = calculatedValue;

                }
                else {
                    potentialCheckpoints[potentialIndex++] = calculatedValue;
                }
                goodForCheckpoint = checkPotentialsForGoodTime();
            }
        }

        return calculatedValue;
    }

    private boolean checkPotentialsForGoodTime() {

        int goodCount = 0;
        double last = -1;
        for (double value: potentialCheckpoints) {
            if (last == -1) {
                last = value;
                continue;
            }
            if (Math.abs(last - value) > quietValue) {
                goodCount = 0;
            }
            else {
                goodCount++;

                if (goodCount >= minMatching) {
                    Log.e("Checkpoint-Found", "0,0,0," + value);
                    break;
                }
            }
        }

        return goodCount >= minMatching;
    }

    public boolean IsGoodForCheckpoint() {

        return this.goodForCheckpoint;
    }

    public void checkpoint() {

        this.lastCalculatedValue = this.calculatedValue;

        for (int index = 0; index < maxPotentials; index++) {
            potentialCheckpoints[index] = 0;
        }
        potentialIndex = 0;
    }

    public static void SetMaximum(int position) {

        switch(position) {

            case 0:
                MaxSetting = 20;
                break;
            case 1:
                MaxSetting = 50;
                break;
            case 2:
                MaxSetting = 100;
                break;
            case 3:
                MaxSetting = 150;
                break;
            case 4:
                MaxSetting = 200;
                break;
            case 5:
                MaxSetting = 500;
                break;
            default:
                break;
        }
    }

    public static void SetMinumum(int position) {

        switch(position) {

            case 0:
                MinSetting = 10;
                break;
            case 1:
                MinSetting = 20;
                break;
            case 2:
                MinSetting = 30;
                break;
            case 3:
                MinSetting = 40;
                break;
            case 4:
                MinSetting = 50;
                break;
            case 5:
                MinSetting = 100;
                break;
            case 6:
                MinSetting = 250;
                break;
            default:
                break;
        }
    }

    public static void SetDifference(int position) {

        switch(position) {

            case 0:
                DistanceSetting = 200;
                break;
            case 1:
                DistanceSetting = 500;
                break;
            case 2:
                DistanceSetting = 1000;
                break;
            case 3:
                DistanceSetting = 1500;
                break;
            default:
                break;
        }
    }

    public static int GetMaximum() {

        switch(MaxSetting) {

            case 20:
                return 0;
            case 50:
                return 1;
            case 100:
                return 2;
            case 150:
                return 3;
            case 200:
                return 4;
            case 500:
                return 5;
        }

        return -1;
    }

    public static int GetMinumum() {

        switch(MinSetting) {

            case 10:
                return 0;
            case 20:
                return 1;
            case 30:
                return 2;
            case 40:
                return 3;
            case 50:
                return 4;
            case 100:
                return 5;
            case 250:
                return 6;
        }

        return -1;
    }

    public static int GetDifference() {

        switch(DistanceSetting) {

            case 200:
                return 0;
            case 500:
                return 1;
            case 1000:
                return 2;
            case 1500:
                return 3;
        }

        return -1;
    }
}

