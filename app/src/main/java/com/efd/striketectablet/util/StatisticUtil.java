package com.efd.striketectablet.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by omnic on 8/28/2016.
 */
public class StatisticUtil {

    private static Context mContext;
    public static AssetManager assetManager;

    public static Typeface blenderproBook;
    public static Typeface blenderproMedium;
    public static Typeface efdigits;

    public static void init(Context context){
        mContext = context;

        assetManager = mContext.getAssets();
        blenderproBook = Typeface.createFromAsset(assetManager, "fonts/BlenderPro-Book.otf");
        blenderproMedium = Typeface.createFromAsset(assetManager, "fonts/BlenderPro-Medium.otf");
        efdigits = Typeface.createFromAsset(assetManager, "fonts/efdigits-ExtraCondensed.ttf");
    }

    public static Context getContext(){
        return mContext;
    }

    public static double average(double[] values) {
        double total = 0;
        for (int i = 0; i < values.length; i++) {
            total += values[i];
        }
        double average = total / values.length;
        return average;
    }

    public static int spTopx(float sp){
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.getResources().getDisplayMetrics());
        return px;
    }

    public static int dpTopx (float dp){
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        return px;
    }

    public static void showToastMessage (String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
