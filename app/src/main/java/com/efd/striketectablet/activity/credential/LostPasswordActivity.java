package com.efd.striketectablet.activity.credential;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.striketectablet.R;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.utilities.EFDConstants;

public class LostPasswordActivity extends AppCompatActivity {

    ImageView backBtn;
    Button resetBtn, continueBtn;

    LinearLayout credentialLayout, resultLayout;
    TextView resultView;

    Spinner securitySpinner;
    CustomSpinnerAdapter securitySpinnerAdapter;

    int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lost_password);

        initViews();
    }

    private void initViews(){
        backBtn = (ImageView)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        securitySpinner = (Spinner)findViewById(R.id.security_question_spinner);
        securitySpinnerAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_text_with_img, PresetUtil.questionList, EFDConstants.SPINNER_TEXT_ORANGE);
        securitySpinner.setAdapter(securitySpinnerAdapter);

        resetBtn = (Button)findViewById(R.id.reset_btn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptResetPwd();
            }
        });

        credentialLayout = (LinearLayout)findViewById(R.id.credential_layout);
        resultLayout = (LinearLayout)findViewById(R.id.reset_result);
        resultView = (TextView)findViewById(R.id.result_text);

        continueBtn = (Button)findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptContinue();
            }
        });

        updateView();
    }

    private void attemptResetPwd(){
        step = 1;
        updateView();
    }

    private void attemptContinue(){
        if (step == 1){
            step = 2;
            updateView();
        }else if (step == 2){
            step = 0;
            updateView();
        }
    }

    private void updateView(){
        if (step == 0){
            credentialLayout.setVisibility(View.VISIBLE);
            resultLayout.setVisibility(View.GONE);
        }else if (step == 1){
            credentialLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
            resultView.setText(getResources().getString(R.string.reset_success));
            resultView.setTextColor(getResources().getColor(R.color.punches_text_color));
        }else if (step == 2){
            credentialLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
            resultView.setText(getResources().getString(R.string.reset_fail));
            resultView.setTextColor(getResources().getColor(R.color.speed_text_color));
        }
    }

    @Override
    public void onBackPressed() {
        if (step == 1 || step == 2){
            step = 0;
            updateView();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
