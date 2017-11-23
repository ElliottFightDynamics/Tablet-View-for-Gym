package com.striketec.fanapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.striketec.fanapp.R;

/**
 * Created by Sukhbirs on 10-11-2017.
 */

public class DialogUtils {

    private static ProgressDialog mProgressDialog;

    /**
     * Method to show toast message.
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        if (toast == null) {
            return;
        }
        View view = toast.getView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(context.getResources().getDrawable(R.drawable.toast_layout_round_corner));
        } else {
            view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.toast_layout_round_corner));
        }
        toast.setView(view);
        toast.show();
    }

    /**
     * Method to show Progress Dialog with loading message.
     *
     * @param context
     * @param loadingMessage
     */
    public static void showProgressDialog(Context context, String loadingMessage) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(loadingMessage);
            mProgressDialog.show();
        }
    }

    /**
     * Method to dismiss loading message.
     */
    public static void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}
