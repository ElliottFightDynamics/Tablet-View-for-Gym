package com.efd.striketectablet.exception;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.efd.striketectablet.activity.CrashActivity;
import com.efd.striketectablet.utilities.EFDConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class EFDExceptionHandler implements
        Thread.UncaughtExceptionHandler {

    private final Activity myContext;
    private final String LINE_SEPARATOR = "\n";

    public EFDExceptionHandler(Activity context) {
        myContext = context;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        exception.printStackTrace();

        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n");
        errorReport.append("Time: " + new Date() + "\n\n");
        errorReport.append(stackTrace.toString());

        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);
        appendLog(errorReport.toString());
        Intent intent = new Intent(myContext, CrashActivity.class);
        intent.putExtra("error", EFDConstants.APPLICATION_CRASH_ERROR);
        myContext.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);

    }

    public void appendLog(String textToLog) {
        String filePath = EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + EFDConstants.ERROR_LOGS_DIRECTORY + File.separator;

        File logFile = new File(filePath);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        logFile = new File(filePath + EFDConstants.ERROR_LOGS_FILE);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        try {
            // BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(textToLog);
            buf.newLine();
            buf.flush();
            buf.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}