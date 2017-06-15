package com.efd.striketectablet.util;

import android.content.Context;
import android.util.Log;

import com.efd.striketectablet.DTO.responsedto.CountryDTO;
import com.efd.striketectablet.DTO.responsedto.CountryListDTO;
import com.efd.striketectablet.DTO.responsedto.QuestionDTO;
import com.efd.striketectablet.DTO.responsedto.QuestionListDTO;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

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
    public static ArrayList<String> reachList = new ArrayList<>();
    public static ArrayList<String> heightList = new ArrayList<>();
    public static ArrayList<String> countryList = new ArrayList<>();
    public static ArrayList<String> questionList = new ArrayList<>();
    public static ArrayList<String> questionIDList = new ArrayList<>();
    public static ArrayList<String> countryIDList = new ArrayList<>();
    public static ArrayList<String> dayList = new ArrayList<>();
    public static ArrayList<String> monthList = new ArrayList<>();
    public static ArrayList<String> threeCharMonthList  = new ArrayList<>();
    public static ArrayList<String> yearList = new ArrayList<>();
    public static ArrayList<String> stanceList = new ArrayList<>();
    public static ArrayList<String> skillLeveList = new ArrayList<>();


    public static void init(Context context){
        gloveList.add("MMA");
        gloveList.add("12");
        gloveList.add("14");
        gloveList.add("16");

        sexList.add("Male");
        sexList.add("Female");

        stanceList.add(EFDConstants.TRADITIONAL_TEXT);
        stanceList.add(EFDConstants.NON_TRADITIONAL_TEXT);

        skillLeveList.add(EFDConstants.SKILL_LEVEL_NOVICE_TEXT);
        skillLeveList.add(EFDConstants.SKILL_LEVEL_AMATEUR_TEXT);
        skillLeveList.add(EFDConstants.SKILL_LEVEL_PROFESSIONAL_TEXT);

        for (int i = 1; i < 32; i++){
            if (i < 10)
                dayList.add("0" + String.valueOf(i));
            else
                dayList.add(String.valueOf(i));
        }

        for (int i = 1900; i < 2101; i++){
            yearList.add(String.valueOf(i));
        }

        monthList.add("JANUARY");
        monthList.add("FEBRUARY");
        monthList.add("MARCH");
        monthList.add("APRIL");
        monthList.add("MAY");
        monthList.add("JUNE");
        monthList.add("JULY");
        monthList.add("AUGUST");
        monthList.add("SEPTEMBER");
        monthList.add("OCTOBER");
        monthList.add("NOVEMBER");
        monthList.add("DECEMBER");

        threeCharMonthList.add("Jan");
        threeCharMonthList.add("Feb");
        threeCharMonthList.add("Mar");
        threeCharMonthList.add("Apr");
        threeCharMonthList.add("May");
        threeCharMonthList.add("Jun");
        threeCharMonthList.add("Jul");
        threeCharMonthList.add("Aug");
        threeCharMonthList.add("Sep");
        threeCharMonthList.add("Oct");
        threeCharMonthList.add("Nov");
        threeCharMonthList.add("Dec");




        getQuestionList(context);
        getCountryList(context);

        for (int i = EFDConstants.HEIGHT_MIN; i <= EFDConstants.HEIGHT_MAX ; i = i + EFDConstants.HEIGHT_INTERVAL){
            heightList.add(0, String.valueOf(i));
        }

        for (int i = EFDConstants.REACH_MIN; i <= EFDConstants.REACH_MAX ; i = i + EFDConstants.REACH_INTERVAL){
            reachList.add(0, String.valueOf(i));
        }

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

    public static void getQuestionList(Context context){
        questionList.clear();
        questionIDList.clear();

        if (CommonUtils.isOnline(context)) {
            RetrofitSingleton.CREDENTIAL_REST.questionList().enqueue(new IndicatorCallback<QuestionListDTO>(context) {
                @Override
                public void onResponse(Call<QuestionListDTO> call, Response<QuestionListDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        QuestionListDTO questionListDTO = response.body();
                        List<QuestionDTO> questionDTOs = questionListDTO.getQuestionList();
                        Log.e("Super", "get question success");

                        for (int i = 0; i < questionDTOs.size(); i++) {
                            questionList.add(questionDTOs.get(i).getQuestionText());
                            questionIDList.add(String.valueOf(questionDTOs.get(i).getId()));
                        }

                        Log.e("Super", "question list size = " + questionList.size());
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<QuestionListDTO> call, Throwable t) {
                    super.onFailure(call, t);
                }
            });
        }else {
        }
    }

    public static void getCountryList (Context context){
        countryList.clear();
        countryIDList.clear();

        if (CommonUtils.isOnline(context)) {
            RetrofitSingleton.CREDENTIAL_REST.countryList().enqueue(new IndicatorCallback<CountryListDTO>(context) {
                @Override
                public void onResponse(Call<CountryListDTO> call, Response<CountryListDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        CountryListDTO countryListDTO = response.body();
                        List<CountryDTO>  countryDTOs = countryListDTO.getCountryList();
                        Log.e("Super", "get country success");

                        for (int i = 0; i < countryDTOs.size(); i++) {
                            countryList.add(countryDTOs.get(i).getName());
                            countryIDList.add(String.valueOf(countryDTOs.get(i).getId()));
                        }
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<CountryListDTO> call, Throwable t) {
                    super.onFailure(call, t);
                }
            });
        }else {
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

    public static String changeSecondsToHours (int secs){
        int hour = secs / 3600;
        int min = (secs % 3600) / 60;
        int sec = secs % 60;

        return String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
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

    public static int getGlovePosition(String gloveType){
        for (int i = 0; i < gloveList.size(); i++){
            if (gloveType.equalsIgnoreCase(gloveList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getReachPosition(String reachvalue){
        for (int i = 0; i < reachList.size(); i++){
            if (reachvalue.equalsIgnoreCase(reachList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getWeightPosition(String weight){
        for (int i = 0; i < weightList.size(); i++){
            if (weight.equalsIgnoreCase(weightList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getHeightPosition(String height){
        for (int i = 0; i < heightList.size(); i++){
            if (height.equalsIgnoreCase(heightList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getGenderPosition(String gender){
        for (int i = 0; i < sexList.size(); i++){
            if (gender.equalsIgnoreCase(sexList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getStanceposition(String stance){
        for (int i = 0; i < stanceList.size(); i++){
            if (stance.equalsIgnoreCase(stanceList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getSkilllevelPosition(String skilllevel){
        for (int i = 0; i < skillLeveList.size(); i++){
            if (skilllevel.equalsIgnoreCase(skillLeveList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getDayPosition(String day){
        for (int i = 0; i < dayList.size(); i++){
            if (day.equalsIgnoreCase(dayList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getMonthPosition(String month){
        for (int i = 0; i < threeCharMonthList.size(); i++){
            if (month.equalsIgnoreCase(threeCharMonthList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getYearPosition(String year){
        for (int i = 0; i < yearList.size(); i++){
            if (year.equalsIgnoreCase(yearList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getCountryPosition(String country){
        for (int i = 0; i < countryList.size(); i++){
            if (country.equalsIgnoreCase(countryList.get(i)))
                return i;
        }

        return 0;
    }

    public static int getQuestionPosition(String question){
        for (int i = 0; i < questionList.size(); i++){
            if (question.equalsIgnoreCase(questionList.get(i)))
                return i;
        }

        return 0;
    }



}
