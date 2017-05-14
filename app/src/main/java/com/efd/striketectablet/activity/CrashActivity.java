package com.efd.striketectablet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class CrashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crash);
        //TextView crashText = (TextView)findViewById(R.id.crash_message_text);
        //crashText.setText(getIntent().getExtras().getString("error"));
        alert(getIntent().getExtras().getString("error"));

    }

    private void alert(String errorMessage) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Error :" + errorMessage);
        alertDialog.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
                System.exit(10);
            }
        });
        alertDialog.create();
        alertDialog.show();
    }


}
