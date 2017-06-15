package com.efd.striketectablet.activity.profile;

import android.app.Dialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.efd.striketectablet.DTO.responsedto.AuthenticationDTO;
import com.efd.striketectablet.DTO.responsedto.BoxerProfileDTO;
import com.efd.striketectablet.DTO.responsedto.RegisterResponseDTO;
import com.efd.striketectablet.DTO.responsedto.UserDTO;
import com.efd.striketectablet.R;
import com.efd.striketectablet.activity.MainActivity;
import com.efd.striketectablet.activity.credential.RegisterActivity;
import com.efd.striketectablet.adapter.CustomSpinnerAdapter;
import com.efd.striketectablet.api.RetrofitSingleton;
import com.efd.striketectablet.customview.CustomButton;
import com.efd.striketectablet.customview.CustomEditText;
import com.efd.striketectablet.database.DBAdapter;
import com.efd.striketectablet.util.IndicatorCallback;
import com.efd.striketectablet.util.PresetUtil;
import com.efd.striketectablet.util.StatisticUtil;
import com.efd.striketectablet.utilities.CommonUtils;
import com.efd.striketectablet.utilities.EFDConstants;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "Super";

    ImageView backBtn;

    Spinner countrySpinner, securitySpinner, daySpinner, monthSpinner, yearSpinner, genderSpinner, skilllevelSpinner, stanceSpinner, weightSpinner, heightSpinner, gloveSpinner, reachSpinner;
    CustomSpinnerAdapter countrySpinnerAdapter, securitySpinnerAdapter, dayAdapter, monthAdapter, yearAdapter, genderAdapter, skilllevelAdapter, stanceAdatper, weightAdpater, heightAdapter, gloveAdapter, reachAdapter;

    TextView emailView;
    Button saveBtn;

    PorterShapeImageView userPhoto;
    Button addPhoto, editPhoto;

    EditText firstNameView, lastNameView, zipcodeView, answerView;

    String userId;
    MainActivity mainActivityInstance;

    String birthDate = null;
    String traineeFirstNameValue, traineeLastNameValue, weightCountValue, reachCountValue, traineeHeightValue, traineeSkillLevelValue, traineeGloveTypeValue, traineeEmail,
            stanceValue, genderValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);

        mainActivityInstance = MainActivity.getInstance();

        backBtn = (ImageView)findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firstNameView = (EditText)findViewById(R.id.fname);
        lastNameView = (EditText)findViewById(R.id.lname);
        zipcodeView = (EditText)findViewById(R.id.zip);
        answerView = (EditText)findViewById(R.id.answer);

        countrySpinner = (Spinner)findViewById(R.id.country_spinner);
        countrySpinnerAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_text_with_img, PresetUtil.countryList, EFDConstants.SPINNER_TEXT_ORANGE);
        countrySpinner.setAdapter(countrySpinnerAdapter);

        daySpinner = (Spinner)findViewById(R.id.day_spinner);
        dayAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_digit_with_img, PresetUtil.dayList, EFDConstants.SPINNER_DIGIT_ORANGE);
        daySpinner.setAdapter(dayAdapter);

        monthSpinner = (Spinner)findViewById(R.id.month_spinner);
        monthAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_text_with_img, PresetUtil.monthList, EFDConstants.SPINNER_TEXT_ORANGE);
        monthSpinner.setAdapter(monthAdapter);

        yearSpinner = (Spinner)findViewById(R.id.year_spinner);
        yearAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_digit_with_img, PresetUtil.yearList, EFDConstants.SPINNER_DIGIT_ORANGE);
        yearSpinner.setAdapter(yearAdapter);

        genderSpinner = (Spinner)findViewById(R.id.gender_spinner);
        genderAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_text_with_img, PresetUtil.sexList, EFDConstants.SPINNER_TEXT_ORANGE);
        genderSpinner.setAdapter(genderAdapter);

        skilllevelSpinner = (Spinner)findViewById(R.id.skilllevel_spinner);
        skilllevelAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_text_with_img, PresetUtil.skillLeveList, EFDConstants.SPINNER_TEXT_ORANGE);
        skilllevelSpinner.setAdapter(skilllevelAdapter);

        stanceSpinner = (Spinner)findViewById(R.id.stance_spinner);
        stanceAdatper = new CustomSpinnerAdapter(this, R.layout.custom_spinner_text_with_img, PresetUtil.stanceList, EFDConstants.SPINNER_TEXT_ORANGE);
        stanceSpinner.setAdapter(stanceAdatper);

        weightSpinner = (Spinner)findViewById(R.id.weight_spinner);
        weightAdpater = new CustomSpinnerAdapter(this, R.layout.custom_spinner_digit_with_img, PresetUtil.weightList, EFDConstants.SPINNER_WHITE);
        weightSpinner.setAdapter(weightAdpater);

        heightSpinner = (Spinner)findViewById(R.id.height_spinner);
        heightAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_digit_with_img, PresetUtil.heightList, EFDConstants.SPINNER_WHITE);
        heightSpinner.setAdapter(heightAdapter);

        gloveSpinner = (Spinner)findViewById(R.id.gloves_spinner);
        gloveAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_digit_with_img, PresetUtil.gloveList, EFDConstants.SPINNER_WHITE);
        gloveSpinner.setAdapter(gloveAdapter);

        reachSpinner = (Spinner)findViewById(R.id.reach_spinner);
        reachAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_digit_with_img, PresetUtil.reachList, EFDConstants.SPINNER_WHITE);
        reachSpinner.setAdapter(reachAdapter);

        securitySpinner = (Spinner)findViewById(R.id.security_spinner);
        securitySpinnerAdapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_text_with_img, PresetUtil.questionList, EFDConstants.SPINNER_TEXT_ORANGE);
        securitySpinner.setAdapter(securitySpinnerAdapter);

        emailView = (TextView)findViewById(R.id.email);

        saveBtn = (Button)findViewById(R.id.btn_save);

        userId = mainActivityInstance.userId;

        JSONObject result_Training_UserInfo_display = MainActivity.db.trainingUserInfo(userId);
        showUserDetails(result_Training_UserInfo_display.toString());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        userPhoto = (PorterShapeImageView)findViewById(R.id.user_photo);
        addPhoto = (Button)findViewById(R.id.addphoto);
        editPhoto = (Button)findViewById(R.id.editphoto);

        userPhoto.setImageResource(R.drawable.empty_profile);
        addPhoto.setVisibility(View.VISIBLE);
        editPhoto.setVisibility(View.GONE);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });
    }

    private void showPhotoDialog(){
        final Dialog dialog = new Dialog(this);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_editphoto);
        dialog.setCancelable(false);

        final CropImageView srcPhoto;
        final Button cancelBtn, okBtn;

        srcPhoto = (CropImageView)dialog.findViewById(R.id.src_photo);
        cancelBtn = (Button)dialog.findViewById(R.id.cancel_btn);
        okBtn = (Button)dialog.findViewById(R.id.ok_btn);

//        srcPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.train1));
        srcPhoto.setImageResource(R.drawable.train1);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto.setVisibility(View.GONE);
                editPhoto.setVisibility(View.VISIBLE);
                Bitmap cropedImage = srcPhoto.getCroppedImage();
                userPhoto.setImageBitmap(cropedImage);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showUserDetails(String result) {
        JSONObject json;
        try {
            json = new JSONObject(result);
            if (json.getString("success").equals("true")) {

                JSONObject userInfoJsonObject = json.getJSONObject("userInfo");
                String userName, firstName, lastName, userBirthdate, userWeight, userGender, userReach, userStance, userHeight, userSkillLevel, userGloveType, userEmail/*, user_waist, user_chest, user_inseam, leftDevice, rightDevice, user_email, user_password*/;

                userName = userInfoJsonObject.getString("user_name");
                firstName = userInfoJsonObject.getString("first_name");
                lastName = userInfoJsonObject.getString("last_name");
                userBirthdate = userInfoJsonObject.getString("user_birthdate");
                userGender = (userInfoJsonObject.get("user_gender").equals("F")) ? EFDConstants.GENDER_FEMALE : EFDConstants.GENDER_MALE;
                userWeight = (userInfoJsonObject.get("user_weight").equals("0")) ? "" : userInfoJsonObject.getString("user_weight");
                userReach = (userInfoJsonObject.getString("user_reach").equals("0")) ? "" : userInfoJsonObject.getString("user_reach");
                userStance = (userInfoJsonObject.get("user_stance").equals(EFDConstants.NON_TRADITIONAL)) ? EFDConstants.NON_TRADITIONAL_TEXT : EFDConstants.TRADITIONAL_TEXT;
                userHeight = (userInfoJsonObject.getString("user_height").equals("0")) ? "" : userInfoJsonObject.getString("user_height");
                userSkillLevel = (userInfoJsonObject.get("user_skill_level").equals("null")) ? "" : userInfoJsonObject.getString("user_skill_level");
                userGloveType = (userInfoJsonObject.get("user_glove_type").equals("null")) ? "" : userInfoJsonObject.getString("user_glove_type");
                userEmail = userInfoJsonObject.getString("user_email");

                firstNameView.setText(firstName);
                lastNameView.setText(lastName);
                Log.e("Super", "birthdate = " + userBirthdate);

                if (!TextUtils.isEmpty(userBirthdate)){
                    String month = userBirthdate.substring(0, 3);
                    String day = userBirthdate.substring(4, 6);
                    String year = userBirthdate.substring(8, 12);

                    Log.e("Super", "month =-" + month + "-" + day + "-" + year);
                    monthSpinner.setSelection(PresetUtil.getMonthPosition(month));
                    yearSpinner.setSelection(PresetUtil.getYearPosition(year));
                    daySpinner.setSelection(PresetUtil.getDayPosition(day));
                }
//                birthdayValue.setText(userBirthdate);
                emailView.setText(userEmail);

                weightSpinner.setSelection(PresetUtil.getWeightPosition(userWeight));
                heightSpinner.setSelection(PresetUtil.getHeightPosition(userHeight));
                gloveSpinner.setSelection(PresetUtil.getGlovePosition(userGloveType));
                genderSpinner.setSelection(PresetUtil.getGenderPosition(userGender));
                reachSpinner.setSelection(PresetUtil.getReachPosition(userReach));
                stanceSpinner.setSelection(PresetUtil.getStanceposition(userStance));
                skilllevelSpinner.setSelection(PresetUtil.getSkilllevelPosition(userSkillLevel));

            } else {

            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveProfile() {
        if (validate()) {
            setParamData();

            if (CommonUtils.isOnline(this)) {
                updateUserInfo();
            } else {
                updateUserAndBoxer();
            }
        }
    }

    private void updateUserInfo(){
        int serverUserId = MainActivity.db.getServerID(Integer.valueOf(userId));

        RetrofitSingleton.CREDENTIAL_REST.updateUser(String.valueOf(serverUserId), traineeFirstNameValue, traineeLastNameValue, stanceValue, genderValue, birthDate, weightCountValue,
                reachCountValue, traineeSkillLevelValue, traineeHeightValue, traineeGloveTypeValue, traineeEmail,CommonUtils.getAccessTokenValue(getApplicationContext())).enqueue(new IndicatorCallback<AuthenticationDTO>(this) {
            @Override
            public void onResponse(Call<AuthenticationDTO> call, Response<AuthenticationDTO> response) {
                super.onResponse(call, response);
                if (response.body() != null) {

                    AuthenticationDTO authenticationDTO = response.body();

                    if (authenticationDTO.getAccess().equalsIgnoreCase("true")){
                        if (authenticationDTO.getSuccess().equalsIgnoreCase("true")){
                            UserDTO userDTO = authenticationDTO.getUser();
                            BoxerProfileDTO boxerProfileDTO = authenticationDTO.getBoxerProfile();

                            int updatedUserId = updateUserDetailsOnLocalDB(userDTO, boxerProfileDTO);

                            if (updatedUserId != -1) {
                                updateBoxersStance(); //to update boxers stance
                            }

                            StatisticUtil.showToastMessage(EFDConstants.USER_INFO_UPDATE_MESSAGE);
                            finish();

                        }else {
                            StatisticUtil.showToastMessage(authenticationDTO.getMessage());
                        }
                    }else {
                        mainActivityInstance.showSessionExpiredAlertDialog();
                    }

                } else {
                    CommonUtils.showAlertDialogWithActivityFinish(EditProfileActivity.this, EFDConstants.SERVER_ERROR);
                }
            }

            @Override
            public void onFailure(Call<AuthenticationDTO> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    //code to update the boxers stance after changing spinner stance value by #1044
    private void updateBoxersStance() {
        if (stanceSpinner.getSelectedItemPosition() == 1)
            MainActivity.boxersStance = EFDConstants.NON_TRADITIONAL;
        else if (stanceSpinner.getSelectedItemPosition() == 0)
            MainActivity.boxersStance = EFDConstants.TRADITIONAL;
    }

    /**
     * update user info into local database when network is not available.
     */
    private void updateUserAndBoxer() {
        Toast.makeText(mainActivityInstance, EFDConstants.OFFLINE_USER_INFO_UPDATE_MESSAGE, Toast.LENGTH_SHORT).show();

        ContentValues userContentValues = new ContentValues();
        userContentValues.put(DBAdapter.KEY_USER_BOXERFIRSTNAME, traineeFirstNameValue);
        userContentValues.put(DBAdapter.KEY_USER_BOXERLASTNAME, traineeLastNameValue);
        userContentValues.put(DBAdapter.KEY_USER_DATE_OF_BIRTH, birthDate);
        userContentValues.put(DBAdapter.KEY_USER_GENDER, genderValue);
        userContentValues.put(DBAdapter.KEY_USER_SYNC, EFDConstants.SYNC_FALSE);

        long updateUserResponse = MainActivity.db.updateUserFields(userContentValues, Integer.parseInt(userId));
        long updateUsersBoxerResponse = 0;
        if (updateUserResponse != 0) {
            ContentValues usersBoxerContentValues = new ContentValues();
            usersBoxerContentValues.put(DBAdapter.KEY_BOXER_REACH, reachCountValue);
            usersBoxerContentValues.put(DBAdapter.KEY_BOXER_STANCE, stanceValue);
            usersBoxerContentValues.put(DBAdapter.KEY_BOXER_WEIGHT, weightCountValue);
            usersBoxerContentValues.put(DBAdapter.KEY_BOXER_SKILL_LEVEL, traineeSkillLevelValue);
            usersBoxerContentValues.put(DBAdapter.KEY_BOXER_GLOVE_TYPE, traineeGloveTypeValue);
            usersBoxerContentValues.put(DBAdapter.KEY_BOXER_HEIGHT, traineeHeightValue);
            updateUsersBoxerResponse = MainActivity.db.updateUsersBoxer(usersBoxerContentValues, Integer.parseInt(userId));
        }
        if (updateUsersBoxerResponse != 0) {
            Toast.makeText(mainActivityInstance, EFDConstants.USER_INFO_UPDATE_MESSAGE, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(mainActivityInstance, EFDConstants.USER_INFO_UPDATE_MESSAGE_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    private void setParamData() {

        birthDate = PresetUtil.threeCharMonthList.get(monthSpinner.getSelectedItemPosition()) + " " + daySpinner.getSelectedItem().toString() + ", " + yearSpinner.getSelectedItem().toString();

        Log.e("Super", "birthdate = " + birthDate);

        setGenderValue(genderSpinner.getSelectedItem().toString().trim());
        setStanceValue(stanceSpinner.getSelectedItem().toString().trim());
        traineeSkillLevelValue = skilllevelSpinner.getSelectedItem().toString().trim();
        traineeGloveTypeValue = gloveSpinner.getSelectedItem().toString().trim();
        reachCountValue = reachSpinner.getSelectedItem().toString();
    }

    private void setGenderValue(String genderText) {
        if (genderText.equalsIgnoreCase(EFDConstants.GENDER_FEMALE)) {
            genderValue = "" + EFDConstants.GENDER_FEMALE.charAt(0);
        } else {
            genderValue = "" + EFDConstants.GENDER_MALE.charAt(0);
        }
    }

    private void setStanceValue(String stanceText) {
        if (stanceText.equalsIgnoreCase(EFDConstants.NON_TRADITIONAL_TEXT)) {
            stanceValue = EFDConstants.NON_TRADITIONAL;
        } else {
            stanceValue = EFDConstants.TRADITIONAL;
        }
    }

    private boolean validate(){
        View focusView = null;

        traineeFirstNameValue = firstNameView.getText().toString().trim();
        traineeLastNameValue = lastNameView.getText().toString().trim();

        weightCountValue = weightSpinner.getSelectedItem().toString().trim();
        traineeHeightValue = heightSpinner.getSelectedItem().toString().trim();

        traineeEmail = emailView.getText().toString();

        if (TextUtils.isEmpty(traineeFirstNameValue)){
            firstNameView.setError(getString(R.string.error_firstname_field_required));
            focusView = firstNameView;
            focusView.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(traineeLastNameValue)){
            lastNameView.setError(getString(R.string.error_lastname_field_required));
            focusView = lastNameView;
            focusView.requestFocus();
            return false;
        }

        return true;
    }

    private int updateUserDetailsOnLocalDB(UserDTO loggedInUserDetails, BoxerProfileDTO loggedInBoxerProfileDetails) {
        MainActivity.db = DBAdapter.getInstance(this);
        int localUserId = -1, localBoxerId = -1;

        try {
            int countryId = loggedInUserDetails.getCountry().getId();

            localUserId = MainActivity.db.updateUser(
                    Integer.parseInt(userId),
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

            if (localUserId != -1) {
                Log.e(TAG, "User table Updated successfully.......................");
                localBoxerId = MainActivity.db.updateBoxerProfile(  1,
                        (loggedInBoxerProfileDetails.getChest()),
                        (loggedInBoxerProfileDetails.getInseam()),
                        (loggedInBoxerProfileDetails.getReach()),
                        (TextUtils.isEmpty(loggedInBoxerProfileDetails.getStance())) ? EFDConstants.TRADITIONAL : loggedInBoxerProfileDetails.getStance(),
                        localUserId,
                        (loggedInBoxerProfileDetails.getWaist()),
                        (loggedInBoxerProfileDetails.getWeight()),
                        (loggedInBoxerProfileDetails.getHeight()),
                        loggedInBoxerProfileDetails.getLeftDevice(),
                        loggedInBoxerProfileDetails.getRightDevice(),
                        loggedInBoxerProfileDetails.getGloveType(),
                        loggedInBoxerProfileDetails.getSkillLevel(),
                        loggedInUserDetails.getId());

                if (localBoxerId != -1) {
                    Log.i(TAG, "user and boxer profile updated successfully" + localBoxerId);
                }
            } else {
                Log.e(TAG, "user not updated  successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return localUserId;
    }

    public int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
