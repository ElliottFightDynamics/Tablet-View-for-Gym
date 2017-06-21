package com.efd.striketectablet.activity.credential;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.striketectablet.DTO.responsedto.AuthenticationDTO;
import com.efd.striketectablet.DTO.responsedto.QuestionDTO;
import com.efd.striketectablet.DTO.responsedto.ResetpwdDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.util.IndicatorCallback;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;

import retrofit2.Call;
import retrofit2.Response;

public class LostPasswordActivity extends AppCompatActivity {

    private enum STATUS{
        CREDENTIAL,
        EMAIL_SUCCESS,
        EMAIL_FAIELD,
        ANSWER_SUCCESS,
        ANSWER_FAIELD
    }

    ImageView backBtn;
    Button resetBtn, continueBtn;

    LinearLayout credentialLayout, resultLayout;
    TextView resultView;

    EditText emailView;
    EditText answerView;
    Spinner securitySpinner;
    CustomSpinnerAdapter securitySpinnerAdapter;

    STATUS step = STATUS.CREDENTIAL;
    String email, answer;

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

        emailView = (EditText)findViewById(R.id.email);
        answerView = (EditText)findViewById(R.id.answer);

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
        email = emailView.getText().toString();
        answer = answerView.getText().toString();

        if (TextUtils.isEmpty(email)){
            StatisticUtil.showToastMessage("Please type email");
            emailView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(answer)){
            //this is reset param with
            if (CommonUtils.isOnline(getApplicationContext())) {
                RetrofitSingleton.CREDENTIAL_REST.resetPwdwithEmail(email).enqueue(new IndicatorCallback<ResetpwdDTO>(this) {
                    @Override
                    public void onResponse(Call<ResetpwdDTO> call, Response<ResetpwdDTO> response) {
                        super.onResponse(call, response);
                        if (response.body() != null) {

                            ResetpwdDTO resetpwdDTO = response.body();
                            if (resetpwdDTO.getSuccess()){
                                step = STATUS.EMAIL_SUCCESS;
                                updateView();
                            }else {
                                step = STATUS.EMAIL_FAIELD;
                                updateView();
                            }
                        } else {
                            step = STATUS.EMAIL_FAIELD;
                            updateView();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetpwdDTO> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
            }else {

            }
        }else {
            //this is reset param with security
            if (CommonUtils.isOnline(getApplicationContext())) {
                Log.e("Super", "ques id = " + PresetUtil.questionIDList.get(securitySpinner.getSelectedItemPosition()));
                RetrofitSingleton.CREDENTIAL_REST.resetPwdwithQuestion(email, PresetUtil.questionIDList.get(securitySpinner.getSelectedItemPosition()), answer).enqueue(new IndicatorCallback<ResetpwdDTO>(this) {
                    @Override
                    public void onResponse(Call<ResetpwdDTO> call, Response<ResetpwdDTO> response) {
                        super.onResponse(call, response);
                        if (response.body() != null) {

                            ResetpwdDTO resetpwdDTO = response.body();
                            Log.e("Super", "reset pwd = " + resetpwdDTO.getSuccess() + "   " + resetpwdDTO.getSendStatus());
                            if (resetpwdDTO.getSuccess()){
                                step = STATUS.ANSWER_SUCCESS;
                                updateView();
                            }else {
                                step = STATUS.ANSWER_FAIELD;
                                updateView();
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetpwdDTO> call, Throwable t) {
                        super.onFailure(call, t);
                    }
                });
            }else {

            }
        }

//        step = 1;
//        updateView();
    }

    private void attemptContinue(){
        Log.e("Super", "status = " + step);
        if (step == STATUS.EMAIL_FAIELD){
            step = STATUS.CREDENTIAL;
            updateView();
        }else if (step == STATUS.EMAIL_SUCCESS) {
            finish();
        }else if (step == STATUS.ANSWER_FAIELD) {
            step = STATUS.CREDENTIAL;
            updateView();
        }else if (step == STATUS.ANSWER_SUCCESS){
            //start new password screen
            Log.e("Super", "111111111");
            Intent changePwd = new Intent(this, ChangePasswordActivity.class);
            changePwd.putExtra("email", email);
            startActivity(changePwd);
            Log.e("Super", "222222222");
            finish();

        }

//        if (step == 1){
//            step = 2;
//            updateView();
//        }else if (step == 2){
//            step = 0;
//            updateView();
//        }
    }

    private void updateView(){
        if (step == STATUS.CREDENTIAL){
            emailView.setText("");
            answerView.setText("");
            credentialLayout.setVisibility(View.VISIBLE);
            resultLayout.setVisibility(View.GONE);
        }else if (step == STATUS.ANSWER_SUCCESS){
            credentialLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
            resultView.setText(getResources().getString(R.string.reset_success));
            resultView.setTextColor(getResources().getColor(R.color.punches_text_color));
        }else if (step == STATUS.ANSWER_FAIELD){
            credentialLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
            resultView.setText(getResources().getString(R.string.reset_fail));
            resultView.setTextColor(getResources().getColor(R.color.speed_text_color));
        }else if (step == STATUS.EMAIL_FAIELD){
            credentialLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
            resultView.setText(getResources().getString(R.string.reset_sentemailfailed));
            resultView.setTextColor(getResources().getColor(R.color.speed_text_color));
        }else if (step == STATUS.EMAIL_SUCCESS){
            credentialLayout.setVisibility(View.GONE);
            resultLayout.setVisibility(View.VISIBLE);
            resultView.setText(getResources().getString(R.string.reset_sentemail));
            resultView.setTextColor(getResources().getColor(R.color.punches_text_color));
        }
    }

    @Override
    public void onBackPressed() {
        if (step == STATUS.CREDENTIAL){
            super.onBackPressed();
        }else{
            step = STATUS.CREDENTIAL;
            updateView();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
