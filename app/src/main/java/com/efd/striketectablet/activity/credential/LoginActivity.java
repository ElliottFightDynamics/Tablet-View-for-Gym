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
import android.widget.TextView;
import android.widget.Toast;

import com.efd.striketectablet.DTO.AuthenticationDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.util.IndicatorCallback;
import com.efd.striketectablet.util.StatisticUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

import static com.efd.striketectablet.util.StatisticUtil.sha256;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Super";

    Button loginBtn, lostpwdBtn, registerBtn;
    TextView emailView, pwdView;
    String email, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        loginBtn = (Button)findViewById(R.id.btn_login);
        lostpwdBtn = (Button)findViewById(R.id.btn_lostpwd);
        registerBtn = (Button)findViewById(R.id.btn_register);

        emailView = (TextView)findViewById(R.id.email);
        pwdView = (TextView)findViewById(R.id.pwd);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin(){
        emailView.setError(null);
        pwdView.setError(null);

        // Store values at the time of the login attempt.
        email = emailView.getText().toString();
        pwd = pwdView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(pwd)) {
            pwdView.setError(getString(R.string.error_password_field_required));
            focusView = pwdView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_email_field_required));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else{
            pwd = sha256(pwd);
            RetrofitSingleton.CREDENTIAL_REST
                    .login(email, pwd).enqueue(new IndicatorCallback<AuthenticationDTO>(this) {
                @Override
                public void onResponse(Call<AuthenticationDTO> call, Response<AuthenticationDTO> response) {
                    super.onResponse(call, response);
                    if (response.body() != null) {
                        AuthenticationDTO authenticationDTO = response.body();
                        if (authenticationDTO.getSuccess().equalsIgnoreCase("true")){
                            Log.e(TAG, "Login successfull username = " + authenticationDTO.getUser().getEmailId());
                            StatisticUtil.showToastMessage(authenticationDTO.getMessage());
                            startMainActivity();
                        }else {
                            Log.e(TAG, "Login failed username = " + authenticationDTO.getMessage());
                            StatisticUtil.showToastMessage(authenticationDTO.getMessage());
                        }
                    } else {
                        Log.e(TAG, "Login failed message = " + response.message());
                        if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, "Unauthorized", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Application error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AuthenticationDTO> call, Throwable t) {
                    super.onFailure(call, t);
                    Log.e(TAG, "login failed", t);
                }
            });
        }
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
}
