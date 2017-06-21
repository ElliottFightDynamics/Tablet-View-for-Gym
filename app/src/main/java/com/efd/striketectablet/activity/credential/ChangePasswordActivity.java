package com.efd.striketectablet.activity.credential;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ChangePasswordActivity extends AppCompatActivity {

    ImageView backBtn;
    Button changeBtn;

    EditText pwdView, confirmPwdView;

    String newpwd, confirmpwd, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);

        email = getIntent().getStringExtra("email");

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

        changeBtn = (Button)findViewById(R.id.reset_btn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptChangePWD();
            }
        });

        pwdView = (EditText)findViewById(R.id.newpwd);
        confirmPwdView = (EditText)findViewById(R.id.confirmpwd);
    }

    private void attemptChangePWD(){
        newpwd = pwdView.getText().toString();
        confirmpwd = confirmPwdView.getText().toString();

        if (TextUtils.isEmpty(newpwd)){
            StatisticUtil.showToastMessage("Please type new password");
            pwdView.requestFocus();
            return;
        }

        if (newpwd.length() < 8){
            StatisticUtil.showToastMessage("Password has to be more than 8 charactors");
            pwdView.requestFocus();
            return;
        }

        if (!confirmpwd.equals(newpwd)){
            StatisticUtil.showToastMessage("New password and confirm password are not matched");
            return;
        }

        if (CommonUtils.isOnline(getApplicationContext())) {
            Log.e("Super", "email = " + email + "   " + newpwd);
            RetrofitSingleton.CREDENTIAL_REST.updatePassword(email, newpwd).enqueue(new IndicatorCallback<ResetpwdDTO>(this) {
                @Override
                public void onResponse(Call<ResetpwdDTO> call, Response<ResetpwdDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {

                        ResetpwdDTO resetpwdDTO = response.body();
                        Log.e("Super", "result = " + resetpwdDTO.getSuccess());
                        if (resetpwdDTO.getSuccess()){
                            StatisticUtil.showToastMessage("Password change successfully");
                        }else {
                            StatisticUtil.showToastMessage("Password doesn't change");
                        }

                        finish();
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResetpwdDTO> call, Throwable t) {
                    super.onFailure(call, t);
                    Log.e("Super", "fail = " + t);
                }
            });
        }else {

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
