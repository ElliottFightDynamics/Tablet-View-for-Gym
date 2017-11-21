package com.striketec.fanapp.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Sukhbirs on 14-11-2017.
 */

public class Utils {
    /**
     * Check if this device has a camera
     */
    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
