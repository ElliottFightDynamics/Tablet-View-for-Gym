package com.efd.striketectablet.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.efd.striketectablet.DTO.ComboDTO;
import com.efd.striketectablet.DTO.PresetDTO;
import com.efd.striketectablet.DTO.SetsDTO;
import com.efd.striketectablet.DTO.WorkoutDTO;
import com.efd.striketectablet.util.ComboSetUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by vrublevskyi on 5/26/2015.
 */
public class SharedPreferencesUtils {

    public static final String PREFS = "prefs";
    public static final String PRESET = "preset";
    public static final String COMBO = "combo";
    public static final String SET = "set";
    public static final String WORKOUT = "workout";

    public static final String BASE_URL = "base.url";

    public static final String PRESET_DATA = "preset_data";

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static void saveBaseUrl(String username, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BASE_URL, username).commit();
    }

    public static String getBaseUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return "http://fe1-1088333348.us-west-2.elb.amazonaws.com:8090/EFD";
    }

    public static void savePresetLists(String jsonString){
        Log.e("Super", "json string = " + jsonString);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(PRESET, jsonString).commit();
    }

    public static void savePresetLists (Context context, ArrayList<PresetDTO> arrayList){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        final String jsonString = gson.toJson(arrayList);

        String userId = CommonUtils.getServerUserId(mContext);
        String accessToken = CommonUtils.getAccessTokenValue(mContext);
        sharedPreferences.edit().putString(PRESET, jsonString).commit();

//        RetrofitSingleton.TRAINING_REST.savePreset(userId, accessToken, jsonString).enqueue(new IndicatorCallback<PlansResponseDTO>(mContext) {
//            @Override
//            public void onResponse(Call<PlansResponseDTO> call, Response<PlansResponseDTO> response) {
//                super.onResponse(call, response);
//                if (response.body() != null) {
//                    PlansResponseDTO responseDTO = response.body();
//                    if (responseDTO.getAccess()){
//                        if (responseDTO.getSuccess())
//                            sharedPreferences.edit().putString(PRESET, jsonString).commit();
//                    }else {
//                        MainActivity.getInstance().showSessionExpiredAlertDialog();
//                    }
//                } else {
//                    Log.e("Super", "server response is null ");
//                    StatisticUtil.showToastMessage("Server response is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlansResponseDTO> call, Throwable t) {
//                super.onFailure(call, t);
//                Log.e("Super", "server failed = " + t);
//                StatisticUtil.showToastMessage(t.toString());
//            }
//        });
    }

    public static ArrayList<PresetDTO> getPresetList (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ArrayList<PresetDTO> results;
        Type type = new TypeToken<ArrayList<PresetDTO>>(){}.getType();
        String jsonString = sharedPreferences.getString(PRESET, "");
        if (TextUtils.isEmpty(jsonString))
            results = new ArrayList<>();
        else
            results = gson.fromJson(jsonString, type);
        return results;
    }

    public static void saveCombinationList(String jsonString){
        Log.e("Super", "json string = " + jsonString);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(COMBO, jsonString).commit();
    }

    public static void saveSetList(String jsonString){
        Log.e("Super", "json string = " + jsonString);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(SET, jsonString).commit();
    }

    public static void saveWorkoutList(String jsonString){
        Log.e("Super", "json string = " + jsonString);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(WORKOUT, jsonString).commit();
    }

    public static void saveCombinationList (Context context, ArrayList<ComboDTO> arrayList){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        final String jsonString = gson.toJson(arrayList);

        String userId = CommonUtils.getServerUserId(mContext);
        String accessToken = CommonUtils.getAccessTokenValue(mContext);

        Log.e("Super", "combination = " + jsonString + "   " + userId + "    " + accessToken);
        sharedPreferences.edit().putString(COMBO, jsonString).commit();
//        RetrofitSingleton.TRAINING_REST.saveComboPlan(userId, accessToken, jsonString).enqueue(new IndicatorCallback<PlansResponseDTO>(mContext) {
//            @Override
//            public void onResponse(Call<PlansResponseDTO> call, Response<PlansResponseDTO> response) {
//                super.onResponse(call, response);
//                if (response.body() != null) {
//                    PlansResponseDTO responseDTO = response.body();
//                    if (responseDTO.getAccess()){
////                        if (responseDTO.getSuccess())
//
//                    }else {
//                        MainActivity.getInstance().showSessionExpiredAlertDialog();
//                    }
//                } else {
//                    Log.e("Super", "server response is null ");
//                    StatisticUtil.showToastMessage("Server response is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlansResponseDTO> call, Throwable t) {
//                super.onFailure(call, t);
//                Log.e("Super", "server failed = " + t);
//                StatisticUtil.showToastMessage(t.toString());
//            }
//        });
    }

    public static ArrayList<ComboDTO> getSavedCombinationList (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        ArrayList<ComboDTO> results;

        Type type = new TypeToken<ArrayList<ComboDTO>>(){}.getType();
        String jsonString = sharedPreferences.getString(COMBO, "");
        if (TextUtils.isEmpty(jsonString))
            results = new ArrayList<>();
        else
            results = gson.fromJson(jsonString, type);

        if (results.size() == 0){
            results = ComboSetUtil.defaultCombos;
            saveCombinationList(context, results);
        }

//        if (results.size() > 0) {
//            sharedPreferences.edit().putInt(EFDConstants.COMBO_ID, results.get(results.size() - 1).getId()).commit();
//        }else {
//            sharedPreferences.edit().putInt(EFDConstants.COMBO_ID, 0).commit();
//        }

        return results;
    }

    public static void saveSetList (Context context, ArrayList<SetsDTO> arrayList){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        final String jsonString = gson.toJson(arrayList);


        String userId = CommonUtils.getServerUserId(mContext);
        String accessToken = CommonUtils.getAccessTokenValue(mContext);

        Log.e("Super", "sets = " + jsonString + "   " + userId + "    " + accessToken);
        sharedPreferences.edit().putString(SET, jsonString).commit();
//        RetrofitSingleton.TRAINING_REST.saveSetsPlan(userId, accessToken, jsonString).enqueue(new IndicatorCallback<PlansResponseDTO>(mContext) {
//            @Override
//            public void onResponse(Call<PlansResponseDTO> call, Response<PlansResponseDTO> response) {
//                super.onResponse(call, response);
//                if (response.body() != null) {
//                    PlansResponseDTO responseDTO = response.body();
//                    if (responseDTO.getAccess()){
////                        if (responseDTO.getSuccess())
//
//                    }else {
//                        MainActivity.getInstance().showSessionExpiredAlertDialog();
//                    }
//                } else {
//                    Log.e("Super", "server response is null ");
//                    StatisticUtil.showToastMessage("Server response is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlansResponseDTO> call, Throwable t) {
//                super.onFailure(call, t);
//                Log.e("Super", "server failed = " + t);
//                StatisticUtil.showToastMessage(t.toString());
//            }
//        });


    }

    public static ArrayList<SetsDTO> getSavedSetList (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        ArrayList<SetsDTO> results;
        Type type = new TypeToken<ArrayList<SetsDTO>>(){}.getType();
        String jsonString = sharedPreferences.getString(SET, "");
        if (TextUtils.isEmpty(jsonString))
            results = new ArrayList<>();
        else
            results = gson.fromJson(jsonString, type);

//        if (results.size() > 0) {
//            sharedPreferences.edit().putInt(EFDConstants.SET_ID, results.get(results.size() - 1).getId()).commit();
//        }else {
//            sharedPreferences.edit().putInt(EFDConstants.SET_ID, 0).commit();
//        }

        if (results.size() == 0){
            results = ComboSetUtil.defaultSets;
            saveSetList(context, results);
        }

        return results;
    }

    public static void saveWorkoutList (Context context, ArrayList<WorkoutDTO> arrayList){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        final String jsonString = gson.toJson(arrayList);

        String userId = CommonUtils.getServerUserId(mContext);
        String accessToken = CommonUtils.getAccessTokenValue(mContext);
        sharedPreferences.edit().putString(WORKOUT, jsonString).commit();
//        RetrofitSingleton.TRAINING_REST.saveWorkoutPlan(userId, accessToken, jsonString).enqueue(new IndicatorCallback<PlansResponseDTO>(mContext) {
//            @Override
//            public void onResponse(Call<PlansResponseDTO> call, Response<PlansResponseDTO> response) {
//                super.onResponse(call, response);
//                if (response.body() != null) {
//                    PlansResponseDTO responseDTO = response.body();
//                    if (responseDTO.getAccess()){
//                        if (responseDTO.getSuccess()) {
//
//                        }
//
//                    }else {
//                        MainActivity.getInstance().showSessionExpiredAlertDialog();
//                    }
//
//                } else {
//                    Log.e("Super", "server response is null ");
//                    StatisticUtil.showToastMessage("Server response is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PlansResponseDTO> call, Throwable t) {
//                super.onFailure(call, t);
//                Log.e("Super", "server failed = " + t);
//                StatisticUtil.showToastMessage(t.toString());
//            }
//        });
    }

    public static ArrayList<WorkoutDTO> getSavedWorkouts (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        ArrayList<WorkoutDTO> results;
        Type type = new TypeToken<ArrayList<WorkoutDTO>>(){}.getType();
        String jsonString = sharedPreferences.getString(WORKOUT, "");
        if (TextUtils.isEmpty(jsonString))
            results = new ArrayList<>();
        else
            results = gson.fromJson(jsonString, type);

//        if (results.size() > 0) {
//            sharedPreferences.edit().putInt(EFDConstants.WORKOUT_ID, results.get(results.size() - 1).getId()).commit();
//        }else {
//            sharedPreferences.edit().putInt(EFDConstants.WORKOUT_ID, 0).commit();
//        }

        if (results.size() == 0){
            results = ComboSetUtil.defaultWorkouts;
            saveWorkoutList(context, results);
        }

        return results;
    }


    public static int increaseComboID(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int currentComboID = sharedPreferences.getInt(EFDConstants.COMBO_ID, 0);
        currentComboID++;
        sharedPreferences.edit().putInt(EFDConstants.COMBO_ID, currentComboID).commit();

        return currentComboID;
    }

    public static int increaseSetID(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int currentSetId = sharedPreferences.getInt(EFDConstants.SET_ID, 0);
        currentSetId++;
        sharedPreferences.edit().putInt(EFDConstants.SET_ID, currentSetId).commit();

        return currentSetId;
    }

    public static int increaseWorkoutID(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int currentworkID = sharedPreferences.getInt(EFDConstants.WORKOUT_ID, 0);
        currentworkID++;
        sharedPreferences.edit().putInt(EFDConstants.WORKOUT_ID, currentworkID).commit();

        return currentworkID;
    }
}