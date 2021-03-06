package com.efd.striketectablet.util;

import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;

/**
 * Created by super on 10/05/2016.
 */
public class PresetUtil {
    private static int presetNum = 0;

    public static ArrayList<String> roundsList = new ArrayList<>();
    public static ArrayList<String> timeList = new ArrayList<>(); //with time format
    public static ArrayList<String> timerwitSecsList = new ArrayList<>(); //with sec
    public static ArrayList<String> warningTimewithSecList = new ArrayList<>();
    public static ArrayList<String> warningList = new ArrayList<>(); //as secs;
    public static ArrayList<String> weightList = new ArrayList<>();
    public static ArrayList<String> gloveList = new ArrayList<>();
    public static ArrayList<String> sexList = new ArrayList<>();


    public static void init(){
        gloveList.add("12");
        gloveList.add("14");
        gloveList.add("16");

        sexList.add("Male");
        sexList.add("Female");

        for (int i = EFDConstants.WEIGHT_MIN; i <= EFDConstants.WEIGHT_MAX ; i = i + EFDConstants.WEIGHT_INTERVAL){
            weightList.add(0, String.valueOf(i));
        }

        for (int i = EFDConstants.ROUNDS_MIN; i <= EFDConstants.ROUNDS_MAX ; i = i + EFDConstants.ROUNDS_INTERVAL){
            roundsList.add(0, String.valueOf(i));
        }

        for (int i = EFDConstants.PRESET_MIN_TIME; i <= EFDConstants.PRESET_MAX_TIME ; i = i + EFDConstants.PRESET_INTERVAL_TIME){
            timerwitSecsList.add(0, String.valueOf(i));
            timeList.add(0, changeSecondsToMinutes(i));
        }

        for (int i = EFDConstants.WARNING_MIN_TIME; i <= EFDConstants.WARNING_MAX_TIME; i = i + EFDConstants.WARNING_INTERVAL_TIME){
            warningTimewithSecList.add(0, changeSeconsToMinutesforWarning(i));
            warningList.add(0, String.valueOf(i));
        }
    }

    public static String changeSeconsToMinutesforWarning (int secs){
        int min = secs / 60;
        int sec = secs % 60;

        if (sec == 0)
            return min + ":00";
        else if (sec == 5){
            return min + ":05";
        }else
            return min + ":" + sec;
    }

    //change secs to *:** format time
    public static String changeSecondsToMinutes(int secs){
        int min = secs / 60;
        int sec = secs % 60;

        if (sec == 0)
            return min + ":00";
        else
            return min + ":" + sec;
    }

    public static String chagngeSecsToTime(int secs){
        String result = "";

        int hour = secs / 3600;

        int min = secs / 60;
        int sec = secs % 60;

        if (hour == 0) {
            if (sec < 10)
                result = min + ":0" + sec;
            else
                result = min + ":" + sec;
        }else {
            if (sec < 10)
                sec = 0 + sec;

            if (min < 10)
                min = 0+min;

            result = hour + ":" + min + ":" + sec;
        }

        return result;
    }

    public static String generatePresetName(){

        return "PRESET " + String.valueOf(presetNum +1);
    }



}
