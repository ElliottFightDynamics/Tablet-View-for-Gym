package com.efd.striketectablet.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.credential.LoginActivity;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 100;
    private static final int WRITE_PERMISSION = 144;

    private String appVersion;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

    }

    private void startLoginActivity(){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void startMainActivity(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadSharedPreference();

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_PERMISSION);
            return;
        }

        copyProperties();
    }

    private void loadSharedPreference() {
        final SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        // Obtain the sharedPreference, default to true if not available
        userId = sharedPreference.getString(EFDConstants.KEY_USER_ID, null);
        appVersion = sharedPreference.getString("appVersion", null);

        Log.e("Super", "userid = " + userId + "    " + appVersion);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            copyProperties();
        }
    }

    private void startMainActivityOrWelcome() {
        if(userId == null){
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            finish();
        }else {
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(mainIntent);
            finish();
        }
    }

    private void copyProperties() {
        Log.e("Super", "copy propertiese");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                copyAssets(appVersion, SplashActivity.this);

                if (userId != null) {
                    startMainActivity();
                } else {
                    startLoginActivity();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

//    private void copyProperties() {
//        Log.e("Super", "copy propertise");
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startMainActivity();
//                finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
//
//        copyAssets(appVersion, SplashActivity.this);
//    }

    public void copyAssets(String appVersion, Context context) {

        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {

            in = assetManager.open(EFDConstants.PROPERTIESFILEPATH);

            File myDirectory = new File(EFDConstants.EFD_COMMON_DATA_DIRECTORY, EFDConstants.CONFIG_DIRECTORY);
            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }
            File outFile = new File(EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + EFDConstants.CONFIG_DIRECTORY, EFDConstants.PROPERTIESFILEPATH);
            if (!CommonUtils.getApplicationVersion(context).equals(appVersion)) {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("appVersion", CommonUtils.getApplicationVersion(context));
                editor.commit();
                if (outFile.exists()) {
                    outFile.delete();
                }
            }
            if (!outFile.exists()) {
                out = new FileOutputStream(outFile);
                copyFile(in, out);
                out.flush();
                out.close();
                out = null;
            }
            in.close();
            in = null;

        } catch (IOException e) {
            Log.e("tag", "Failed to copy asset file: " + e);
        }
    }

//    public void copyAssets(Context context) {
//
//        AssetManager assetManager = context.getAssets();
//
//        InputStream in = null;
//        OutputStream out = null;
//        try {
//
//            in = assetManager.open(EFDConstants.PROPERTIESFILEPATH);
//
//            File myDirectory = new File(EFDConstants.EFD_COMMON_DATA_DIRECTORY, EFDConstants.CONFIG_DIRECTORY);
//            if (!myDirectory.exists()) {
//                myDirectory.mkdirs();
//            }
//            File outFile = new File(EFDConstants.EFD_COMMON_DATA_DIRECTORY + File.separator + EFDConstants.CONFIG_DIRECTORY, EFDConstants.PROPERTIESFILEPATH);
//
//            if (!outFile.exists()) {
//                out = new FileOutputStream(outFile);
//                copyFile(in, out);
//                out.flush();
//                out.close();
//                out = null;
//            }
//            in.close();
//            in = null;
//
//        } catch (IOException e) {
//            Log.e("tag", "Failed to copy asset file: " + e);
//        }
//    }


    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
