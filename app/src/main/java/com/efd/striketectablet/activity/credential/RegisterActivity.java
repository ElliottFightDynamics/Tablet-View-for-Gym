package com.efd.striketectablet.activity.credential;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.efd.striketectablet.DTO.responsedto.RegisterResponseDTO;
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

public class RegisterActivity extends AppCompatActivity {

    ImageView backBtn;
    Button registerBtn;

    Spinner securitySpinner;
    CustomSpinnerAdapter securitySpinnerAdapter;

    EditText firstnameView, lastnameView, emailView, pwdView, confirmpwdView, answerView;
    TextView termsView;

    String fname, lname, email, pwd, confirpwd, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

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

        firstnameView = (EditText)findViewById(R.id.fname);
        lastnameView = (EditText)findViewById(R.id.lname);
        emailView = (EditText)findViewById(R.id.email);
        pwdView = (EditText)findViewById(R.id.pwd);
        confirmpwdView = (EditText)findViewById(R.id.repwd);

        answerView = (EditText)findViewById(R.id.answer);
        termsView = (TextView)findViewById(R.id.termsofuse);

        registerBtn = (Button)findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        termsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTermsActivity();
            }
        });
    }

    private void startTermsActivity(){
        Intent termsIntent = new Intent(this, TermsofuseActivity.class);
        startActivity(termsIntent);
    }

    private void attemptRegister(){
        if (checkRegister()){
            Call call = RetrofitSingleton.CREDENTIAL_REST.register(fname, lname, email, "12345", PresetUtil.countryIDList.get(0), email, pwd,
                    PresetUtil.questionIDList.get(securitySpinner.getSelectedItemPosition()), answer, "", "", "", "", "", "");
            Log.e("Super", "call = " + call.request().body().toString());
            Log.e("Super", "params = " + fname + "   " + lname + "   " + email +"  " + pwd + "   " + PresetUtil.questionIDList.get(securitySpinner.getSelectedItemPosition()));
            call.enqueue(new IndicatorCallback<RegisterResponseDTO>(this) {
                @Override
                public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {

                        RegisterResponseDTO registerResponseDTO = response.body();

                        Log.e("Super", "message = " + registerResponseDTO.getSuccess() + "   " + registerResponseDTO.getMessage());

                        if (registerResponseDTO.getSuccess()){
                            showConfirmDialog(EFDConstants.TRAINEE_ACCOUNT_SUBMIT_MESSAGE);
                        }else {

                            StatisticUtil.showToastMessage(registerResponseDTO.getMessage());
                        }
                    } else {
                        Log.e("Super", "message = " + response.message());
                        CommonUtils.showAlertDialogWithActivityFinish(RegisterActivity.this, EFDConstants.SERVER_ERROR);
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                    super.onFailure(call, t);
                    Log.e("Super", "failed message = " + t);
                }
            });
        }else {

        }
    }

    public void showConfirmDialog(String confirmationMessage) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Registration");
        alertDialogBuilder.setMessage(confirmationMessage);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialogBuilder.show();
    }

    private boolean checkRegister(){
        fname = firstnameView.getText().toString();
        lname = lastnameView.getText().toString();
        email = emailView.getText().toString();
        pwd = pwdView.getText().toString();
        confirpwd = confirmpwdView.getText().toString();
        answer = answerView.getText().toString();

        if (TextUtils.isEmpty(fname)){
            StatisticUtil.showToastMessage("First name can not be empty!");
            return false;
        }

        if (TextUtils.isEmpty(lname)){
            StatisticUtil.showToastMessage("Last name can not be empty!");
            return false;
        }

        if (TextUtils.isEmpty(email)){
            StatisticUtil.showToastMessage("Email can not be empty!");
            return false;
        }

        if (pwd.length() < 8){
            StatisticUtil.showToastMessage("Pasword must be more than 8 characters");
            return false;
        }

        if (!pwd.equals(confirpwd)){
            StatisticUtil.showToastMessage("Confirm password must be same");
            return false;
        }

        if (TextUtils.isEmpty(answer)){
            StatisticUtil.showToastMessage("Security answer can not be empty!");
            return false;
        }

        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
