package com.efd.striketectablet.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.efd.striketectablet.DTO.PresetDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by super on 11/5/2017.
 */
public class SharedPreferencesUtils {

    public static final String PREFS = "prefs";
    public static final String PRESET = "preset";
    public static final String BASE_URL = "base.url";


    public static void saveBaseUrl(String username, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BASE_URL, username).commit();
    }

    public static String getBaseUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(BASE_URL, null);
    }

    public static void savePresetLists (Context context, ArrayList<PresetDTO> arrayList){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = gson.toJson(arrayList);
        sharedPreferences.edit().putString(PRESET, jsonString).commit();
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
}