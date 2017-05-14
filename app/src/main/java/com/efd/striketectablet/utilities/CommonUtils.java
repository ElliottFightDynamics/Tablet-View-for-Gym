package com.efd.striketectablet.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint({"DefaultLocale", "SimpleDateFormat"})
public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public static String getMacAddress(String address) {
        String macAddress = "";

        for (int i = 0; i < address.length(); i++) {
            char ch = address.charAt(i);
            if (i % 2 == 0 && i != 0) {

                macAddress = macAddress + ":";
                macAddress = macAddress + ch;
            } else {
                macAddress = macAddress + ch;
            }
        }
        return macAddress.toUpperCase();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void openSocialMediaScreen(Context packageContext, Context activeScreenContext, View activeScreenview) {
        try {
            if (CommonUtils.isOnline(activeScreenContext)) {
/* rkh
                Intent intent = new Intent(packageContext, SocialMediaShareActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String shareImagePath = loadBitmapFromView(activeScreenContext, activeScreenview);
                intent.putExtra("shareImagePath", shareImagePath);
                activeScreenContext.startActivity(intent);
                */
            } else {
                Toast.makeText(activeScreenContext, "Internet is not available.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadBitmapFromView(Context context, View view) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        view.measure(MeasureSpec.makeMeasureSpec(dm.widthPixels, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(dm.heightPixels, MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(returnedBitmap);
        view.draw(c);

        String dirName = EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + File.separator + EFDConstants.SCREENSHOTS_DIRECTORY + File.separator;
        String mPath;
        if (makeDirectory(dirName)) {
            mPath = dirName + getCurrentDateTime()
                    + ".jpeg";
        } else {
            mPath = dirName + getCurrentDateTime()
                    + ".jpeg";
        }
        File imageFile = new File(mPath);
        OutputStream fout = null;
        try {
            fout = new FileOutputStream(imageFile);
            if (returnedBitmap == null) {
                Log.d(TAG, "No bitmap image captured.....");
            } else {
                returnedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                fout.flush();
            }
            return mPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            try {
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static boolean makeDirectory(String dirName) {
        boolean result = false;
        File directory = new File(dirName);
        if (directory.exists()) {
            Log.d(TAG, "Folder already exists");
        } else {
            result = directory.mkdirs();
        }
        return result;

    }

    public static void deleteScreenShotImage(String imagePath) {
        File file = new File(imagePath);
        boolean fileDeleted = file.delete();
        Log.d(TAG, "deleted File : " + fileDeleted);
    }

    public static String getCurrentDateTime() {
        final Calendar c = Calendar.getInstance();

        return (new StringBuilder().append(c.get(Calendar.MONTH) + 1)
                .append("_").append(c.get(Calendar.DAY_OF_MONTH)).append("_")
                .append(c.get(Calendar.YEAR)).append("_")
                .append(c.get(Calendar.HOUR_OF_DAY)).append("_")
                .append(c.get(Calendar.MINUTE)).append("_").append(c
                        .get(Calendar.SECOND))).toString();
    }

    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    public static int[] splitDate(String date) {
        String[] splitDate = date.split("-");
        int[] intSplitDate = {Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2])};
        return intSplitDate;
    }

    public static int[] splitTime(String time) {
        String[] splitTime = time.split(":");
        int[] intSplitTime = {Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]), Integer.parseInt(splitTime[2])};
        return intSplitTime;
    }

    /**
     * calculatePastDate
     *
     * @param date    must have "yyyy-MM-dd" format
     * @param number  if measure = 0 then number is number of past days else if measure = 1 then number is number of past years
     * @param measure pass 0 to deduct from days and pass 1 for year
     * @return
     */
    public static String calculatePastDate(String date, int number, int measure) {

        Calendar calendar = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parsedDate = dateFormat.parse(date);
            calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);

            calendar.setTime(dateFormat.parse(date));
            calendar.add((measure == 1) ? Calendar.YEAR : Calendar.DATE, -number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFormat.format(calendar.getTime());

    }

    /**
     * Prepends 0 if number < 10
     */
    public static String twoDigitString(long number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    public static long strTimeToSeconds(String time) {
        int[] splittedTime = splitTime(time);

        int hours = splittedTime[0];
        int minutes = splittedTime[1];
        int seconds = splittedTime[2];

        long totalTimeInSeconds = seconds + (60 * minutes) + (3600 * hours);

        return totalTimeInSeconds;
    }

    public static String secondsToTime(long seconds) {

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateStringYMD() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        return dateFormat.format(date).toString();
    }

    public static void showAlertDialog(Context applicationContext, String errorMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(applicationContext);

        alertDialogBuilder
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ;
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static void showAlertDialogWithActivityFinish(final Context applicationContext, String errorMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(applicationContext);

        alertDialogBuilder
                .setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((Activity) applicationContext).finish();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public static String getApplicationVersion(Context context) {
        String appVersion;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = null;
        }
        return appVersion;
    }

    /**
     * setAccessTokenValue to set secured access token for web-service security
     *
     * @param applicationContext
     * @param efdValidAccessToken
     */
    public static void setAccessTokenValue(final Context applicationContext, String efdValidAccessToken) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(EFDConstants.KEY_SECURE_ACCESS_TOKEN, efdValidAccessToken);
        editor.commit();
    }

    /**
     * getAccessTokenValue
     *
     * @param applicationContext
     * @return Access token value
     */
    public static String getAccessTokenValue(final Context applicationContext) {
        final SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return sharedPreference.getString(EFDConstants.KEY_SECURE_ACCESS_TOKEN, null);
    }

    /**
     * getServerUserId
     *
     * @param applicationContext
     * @return Server User Id
     */
    public static String getServerUserId(final Context applicationContext) {
        final SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return sharedPreference.getString(EFDConstants.KEY_SERVER_USER_ID, null);
    }
}
