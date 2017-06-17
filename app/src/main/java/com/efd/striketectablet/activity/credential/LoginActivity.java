package com.efd.striketectablet.activity.credential;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.efd.striketectablet.DTO.responsedto.AuthenticationDTO;
import com.efd.striketectablet.DTO.responsedto.BoxerProfileDTO;
import com.efd.striketectablet.DTO.responsedto.UserDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.util.IndicatorCallback;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;

import retrofit2.Call;
import retrofit2.Response;

import static com.efd.striketectablet.util.StatisticUtil.sha256;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Super";
    public static final int SERVER_ERROR = -400;

    Button loginBtn, lostpwdBtn, registerBtn;
    TextView emailView, pwdView;
    String email, pwd;

    DBAdapter db;

    /**
     * The settings.
     */
    protected SharedPreferences settings;

    /**
     * The editor.
     */
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = settings.edit();
        db = DBAdapter.getInstance(LoginActivity.this);

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
        lostpwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startResetPwd();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisterActivity();
            }
        });
    }

    private void startRegisterActivity(){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void startResetPwd(){
        Intent lostpwdIntent = new Intent(this, LostPasswordActivity.class);
        startActivity(lostpwdIntent);
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

            Log.e("Super", "email = " + email + "    " + pwd);
            if (CommonUtils.isOnline(getApplicationContext())) {
                RetrofitSingleton.CREDENTIAL_REST.login(email, pwd).enqueue(new IndicatorCallback<AuthenticationDTO>(this) {
                    @Override
                    public void onResponse(Call<AuthenticationDTO> call, Response<AuthenticationDTO> response) {
                        super.onResponse(call, response);
                        if (response.body() != null) {

                            AuthenticationDTO authenticationDTO = response.body();
                            setUserData(authenticationDTO, EFDConstants.BLANK_TEXT, true);

//                            if (authenticationDTO.getSuccess().equalsIgnoreCase("true")) {
//                                Log.e(TAG, "Login successfull username = " + authenticationDTO.getUser().getEmailId());
//                                StatisticUtil.showToastMessage(authenticationDTO.getMessage());
//                                startMainActivity();
//                            } else {
//                                Log.e(TAG, "Login failed username = " + authenticationDTO.getMessage());
//                                StatisticUtil.showToastMessage(authenticationDTO.getMessage());
//                            }
                        } else {
                            Log.e(TAG, "Login failed message = " +  response.message() + " \n\n  " + response.code() +  " \n \n " + response.headers() + "\n\n" +
                            response.errorBody() + "\n\n" + response.raw());
                            CommonUtils.showAlertDialogWithActivityFinish(LoginActivity.this, EFDConstants.SERVER_ERROR);
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticationDTO> call, Throwable t) {
                        super.onFailure(call, t);
                        Log.e(TAG, "login failed", t);
                        int checkUserLoginId = db.userLogin(email, pwd); // check login in local db
                        if (checkUserLoginId == -1) {
                            checkLoginDetails(SERVER_ERROR);// Unable to connect with server.
                        } else {
                            checkLoginDetails(checkUserLoginId);// login when Unable to connect with server but user present in local database.
                        }
                    }
                });
            }else {
                int checkUserLoginId = db.userLogin(email, pwd); // check login in local db
                checkLoginDetails(checkUserLoginId);
            }
        }
    }

    public void setUserData(AuthenticationDTO authenticationDTO, String accessTokenFromRegistrationScreen, boolean isRedirectedFromLogin) {
        int userId = -1;
        if (authenticationDTO.getSuccess().equalsIgnoreCase("true")) {
            UserDTO userDTO = authenticationDTO.getUser();
            BoxerProfileDTO boxerProfileDTO = authenticationDTO.getBoxerProfile();

//                JSONObject loggedInUserDetails = loginDetail.getJSONObject("user");
//                JSONObject loggedInBoxerProfileDetails = loginDetail.getJSONObject("boxerProfile");
            saveUserDetails(userDTO, boxerProfileDTO);
            userId = db.userLogin(userDTO.getUsername(), userDTO.getPassword());
            String accessToken = "";
            if (isRedirectedFromLogin) {
                accessToken = authenticationDTO.getSecureAccessToken();
            } else {
                accessToken = accessTokenFromRegistrationScreen;
            }

            saveUserAccessDetails(accessToken, userId);
            checkLoginDetails(userId);
        } else {
            checkLoginDetails(userId);
        }
    }

    /**
     * Save user details in database.
     *
     * @param loggedInUserDetails the logged in user details
     */
    public void saveUserDetails(UserDTO loggedInUserDetails, BoxerProfileDTO loggedInBoxerProfileDetails) {
        db = DBAdapter.getInstance(LoginActivity.this);
        int recordInsertedOrUpdated = -1, loggedInUserId = -1, boxerProfileRecordInsertedOrUpdated = -1;

        try {
            loggedInUserId = db.userLogin(loggedInUserDetails.getUsername(), loggedInUserDetails.getPassword());

            int countryId = loggedInUserDetails.getCountry().getId();

            if (loggedInUserId == -1) {

                recordInsertedOrUpdated = db.insertUser(
                        1,
                        boolToInt(Boolean.valueOf(loggedInUserDetails.getAccountExpired())),
                        boolToInt(Boolean.valueOf(loggedInUserDetails.getAccountLocked())),
                        loggedInUserDetails.getFirstName(),
                        loggedInUserDetails.getLastName(),
                        (TextUtils.isEmpty(loggedInUserDetails.getDateOfBirth())) ? "" : loggedInUserDetails.getDateOfBirth(),
                        boolToInt(Boolean.valueOf(loggedInUserDetails.getEnabled())),
                        (TextUtils.isEmpty(loggedInUserDetails.getGender())) ? "" : loggedInUserDetails.getGender(),
                        loggedInUserDetails.getPassword(),
                        boolToInt(Boolean.valueOf(loggedInUserDetails.getPasswordExpired())),
                        "".getBytes(),
                        (TextUtils.isEmpty(loggedInUserDetails.getUsername())) ? "" : loggedInUserDetails.getUsername(),
                        (TextUtils.isEmpty(loggedInUserDetails.getEmailId())) ? "" : loggedInUserDetails.getEmailId(),
                        countryId,
                        (TextUtils.isEmpty(loggedInUserDetails.getZipcode())) ? "" : loggedInUserDetails.getZipcode(),
                        loggedInUserDetails.getId());

                boxerProfileRecordInsertedOrUpdated = db.insertBoxerProfile(
                        1, (loggedInBoxerProfileDetails.getChest()),
                        (loggedInBoxerProfileDetails.getInseam()),
                        (loggedInBoxerProfileDetails.getReach()),
                        (TextUtils.isEmpty(loggedInBoxerProfileDetails.getStance())) ? EFDConstants.TRADITIONAL : loggedInBoxerProfileDetails.getStance(),
                        recordInsertedOrUpdated,
                        (loggedInBoxerProfileDetails.getWaist()),
                        (loggedInBoxerProfileDetails.getWeight()),
                        (loggedInBoxerProfileDetails.getHeight()),
                        loggedInBoxerProfileDetails.getLeftDevice(),
                        loggedInBoxerProfileDetails.getRightDevice(),
                        loggedInBoxerProfileDetails.getGloveType(),
                        loggedInBoxerProfileDetails.getSkillLevel(),
                        loggedInUserDetails.getId());
                db.insertUserRole(1, recordInsertedOrUpdated);
            } else {
                recordInsertedOrUpdated = db.updateUser(
                        loggedInUserId,
                        1,
                        boolToInt(Boolean.valueOf(loggedInUserDetails.getAccountExpired())),
                        boolToInt(Boolean.valueOf(loggedInUserDetails.getAccountLocked())),
                        loggedInUserDetails.getFirstName(),
                        loggedInUserDetails.getLastName(),
                        (TextUtils.isEmpty(loggedInUserDetails.getDateOfBirth())) ? "" : loggedInUserDetails.getDateOfBirth(),
                        boolToInt(loggedInUserDetails.getEnabled()),
                        (TextUtils.isEmpty(loggedInUserDetails.getGender())) ? "" : loggedInUserDetails.getGender(),
                        loggedInUserDetails.getPassword(),
                        boolToInt(Boolean.valueOf(loggedInUserDetails.getPasswordExpired())),
                        "".getBytes(),
                        (TextUtils.isEmpty(loggedInUserDetails.getUsername())) ? "" : loggedInUserDetails.getUsername(),
                        (TextUtils.isEmpty(loggedInUserDetails.getEmailId())) ? "" : loggedInUserDetails.getEmailId(),
                        countryId,
                        (TextUtils.isEmpty(loggedInUserDetails.getZipcode())) ? "" : loggedInUserDetails.getZipcode(),
                        EFDConstants.SYNC_TRUE, loggedInUserDetails.getId());

                boxerProfileRecordInsertedOrUpdated = db.updateBoxerProfile(
                        1,
                        (loggedInBoxerProfileDetails.getChest()),
                        (loggedInBoxerProfileDetails.getInseam()),
                        (loggedInBoxerProfileDetails.getReach()),
                        (TextUtils.isEmpty(loggedInBoxerProfileDetails.getStance())) ? EFDConstants.TRADITIONAL : loggedInBoxerProfileDetails.getStance(),
                        recordInsertedOrUpdated,
                        (loggedInBoxerProfileDetails.getWaist()),
                        (loggedInBoxerProfileDetails.getWeight()),
                        (loggedInBoxerProfileDetails.getHeight()),
                        loggedInBoxerProfileDetails.getLeftDevice(),
                        loggedInBoxerProfileDetails.getRightDevice(),
                        loggedInBoxerProfileDetails.getGloveType(),
                        loggedInBoxerProfileDetails.getSkillLevel(),
                        loggedInUserDetails.getId());
            }

            if (recordInsertedOrUpdated != -1 && boxerProfileRecordInsertedOrUpdated == -1)
                Log.e(TAG, "userId = " + recordInsertedOrUpdated);
            else
                Log.e(TAG, "userId = " + recordInsertedOrUpdated);

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return;
    }

    public void saveUserAccessDetails(String secureAccessToken, int userId) {
        int accessRecordInsertedOrUpdated = -1, loggedInUserAccessTokenId = -1;
        try {
            loggedInUserAccessTokenId = db.checkUserAccessDetails(userId);
            if (loggedInUserAccessTokenId == -1) {
                accessRecordInsertedOrUpdated = db.insertUserAccessData(secureAccessToken, userId);
            } else {
                accessRecordInsertedOrUpdated = db.updateUserAccessData(loggedInUserAccessTokenId, secureAccessToken, userId);
            }

            if (accessRecordInsertedOrUpdated != -1) {
                editor.putString(EFDConstants.KEY_SECURE_ACCESS_TOKEN, secureAccessToken);
                editor.commit();
                Log.e(TAG, "user access record inserted or updated successfully");
            } else {
                Log.e(TAG, "user access record not inserted  or updated  successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception while saving user access record " +  e.toString());
        }
        return;
    }

    /**
     * Check the login details specified by user to generate a specified action
     *
     * @param checkUserLoginId
     */
    private void checkLoginDetails(int checkUserLoginId) {
        if (checkUserLoginId == SERVER_ERROR) {
            CommonUtils.showAlertDialog(LoginActivity.this, EFDConstants.SERVER_ERROR);
        } else if (checkUserLoginId != -1) {
            if (!CommonUtils.isOnline(getApplicationContext())) {
                String userAccessToken = db.getUserAccessDetails(checkUserLoginId);
                if (userAccessToken != null) {
                    editor.putString(EFDConstants.KEY_SECURE_ACCESS_TOKEN, userAccessToken);
                }
            }
            editor.putString(EFDConstants.KEY_USER_ID, String.valueOf(checkUserLoginId));
            editor.putString(EFDConstants.KEY_SERVER_USER_ID, String.valueOf(db.getServerUserID(checkUserLoginId)));
            editor.commit();
            startMainActivity();
        } else if (checkUserLoginId == -1 && !CommonUtils.isOnline(LoginActivity.this)) {          //   this code is used for when internet is disable and user is not available in local DB.
            CommonUtils.showAlertDialog(LoginActivity.this, EFDConstants.NETWORK_ERROR);
            pwdView.setText("");
        } else {
            CommonUtils.showAlertDialog(LoginActivity.this, EFDConstants.LOGIN_USERNAME_PASSWORD_INCORRECT_ERROR);
            //mUsernameView.setText(EFDConstants.BLANK_TEXT);
            pwdView.setText(EFDConstants.BLANK_TEXT);
        }
    }

    /**
     * Bool to int.
     *
     * @param b the b
     * @return the int
     */
    public int boolToInt(boolean b) {
        return b ? 1 : 0;
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
