package com.striketec.fanapp.view.users.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.striketec.fanapp.R;
import com.striketec.fanapp.utils.DialogUtils;
import com.striketec.fanapp.utils.Utils;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.view.users.adapter.SimpleDropDownAdapter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.striketec.fanapp.utils.constants.Constants.IMAGE_DIRECTORY_NAME;
import static com.striketec.fanapp.utils.constants.Constants.MEDIA_TYPE_IMAGE;

/**
 * Created by Sukhbirs on 15-11-2017.
 * This is fragment to display screen, Add User.
 */

public class AddUserDialogFragment extends DialogFragment {

    private RecyclerView mUserRecyclerView;
    private Uri mFileUri;
    private ImageView mUserCapturedImageView;
    private AlertDialog mAlertDialog;
    private EditText mGenderEditText, mNameEditText, mEmailEditText, mDobEditText, mWeightEditText, mHeightEditText;
    private SimpleDateFormat mSimpleDateFormat;

    public AddUserDialogFragment() {

    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setCancelable(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_MM_DD_YYYY, Locale.US);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        mAlertDialogBuilder.setCancelable(false);

        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mDialogView = mInflater.inflate(R.layout.dialog_add_user, null);
        mAlertDialogBuilder.setView(mDialogView);
        mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RelativeLayout mOuterLayout = mDialogView.findViewById(R.id.outer_layout_add_user);
        RelativeLayout mBottomRelativeLayout = mOuterLayout.findViewById(R.id.bottom_relative_layout);
        // Cancel
        Button mCancelButton = mBottomRelativeLayout.findViewById(R.id.button_cancel);
        // Register
        Button mRegisterButton = mBottomRelativeLayout.findViewById(R.id.button_register);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonClicked();
            }
        });

        // Take Picture layout
        LinearLayout mTakePictureLayout = mOuterLayout.findViewById(R.id.layout_take_picture);
        mTakePictureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureClicked();
            }
        });

        // User ImageView
        mUserCapturedImageView = mOuterLayout.findViewById(R.id.img_user_image_captured);

        // Name, Email, Gender, Date of Birth, Weight, Height EditText fields (some fields behave as Spinner)
        mNameEditText = mOuterLayout.findViewById(R.id.edit_user_name);
        mEmailEditText = mOuterLayout.findViewById(R.id.edit_email);
        mGenderEditText = mOuterLayout.findViewById(R.id.edit_spinner_gender);
        mDobEditText = mOuterLayout.findViewById(R.id.edit_spinner_date_of_birth);
        mWeightEditText = mOuterLayout.findViewById(R.id.edit_spinner_weight);
        mHeightEditText = mOuterLayout.findViewById(R.id.edit_spinner_height);

        mGenderEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderDropDownList();
            }
        });

        mDobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        mWeightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightDropDownList();
            }
        });

        mHeightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHeightDropDownList();
            }
        });

        // showing alert dialog
        mAlertDialog.show();

        // adjust width and height of AlertDialog
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int widthPixels = mDisplayMetrics.widthPixels;
        int heightPixels = mDisplayMetrics.heightPixels;
        mAlertDialog.getWindow().setLayout(widthPixels - 450, heightPixels - 350);

        return mAlertDialog;
    }

    /**
     * Method to handle click event of Register button.
     */
    private void registerButtonClicked() {
        if (isAddUserFormValid()) {

        }
    }

    /**
     * Method to validate the add user form.
     *
     * @return
     */
    private boolean isAddUserFormValid() {
        String name = mNameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String gender = mGenderEditText.getText().toString().trim();
        String dob = mDobEditText.getText().toString().trim();
        String weight = mWeightEditText.getText().toString().trim();
        String height = mHeightEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            DialogUtils.showToast(getActivity(), getString(R.string.error_name_is_required));
            return false;
        } else if (TextUtils.isEmpty(email)) {
            DialogUtils.showToast(getActivity(), getString(R.string.error_email_is_required));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            DialogUtils.showToast(getActivity(), getString(R.string.error_invalid_email_id_format));
            return false;
        } else if (TextUtils.isEmpty(gender)) {
            DialogUtils.showToast(getActivity(), getString(R.string.error_gender_is_required));
            return false;
        } else if (TextUtils.isEmpty(dob)) {
            DialogUtils.showToast(getActivity(), getString(R.string.error_dob_is_required));
            return false;
        } else if (TextUtils.isEmpty(weight)) {
            DialogUtils.showToast(getActivity(), getString(R.string.error_weight_is_required));
            return false;
        } else if (TextUtils.isEmpty(height)) {
            DialogUtils.showToast(getActivity(), getString(R.string.error_height_is_required));
            return false;
        }

        return true;
    }

    /**
     * Method to show the Date Picker.
     */
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = 0, month = 0, dayOfMonth = 0;
        String dob = mDobEditText.getText().toString();
        if (dob != null && dob.length() > 0) {
            // showing previous selected date to DatePickerDialog.
            try {
                Date date = mSimpleDateFormat.parse(dob);
                Calendar oldCalendar = Calendar.getInstance();
                oldCalendar.setTime(date);
                year = oldCalendar.get(Calendar.YEAR);
                month = oldCalendar.get(Calendar.MONTH);
                dayOfMonth = oldCalendar.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                mDobEditText.setText(mSimpleDateFormat.format(newDate.getTime()));
            }
        }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    /**
     * Method to handle click event of Take Picture layout/button.
     */
    private void takePictureClicked() {
        if (Utils.checkCameraHardware(getActivity())) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                mFileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                getActivity().startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
            }
        } else {
            DialogUtils.showToast(getActivity(), getString(R.string.error_camera_not_supported));
        }
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Method to set the captured image.
     */
    public void previewCapturedImage() {
        if (mAlertDialog == null || !mAlertDialog.isShowing()) {
            return;
        }
        try {
            // bitmap factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(mFileUri.getPath(), options);

            if (mUserCapturedImageView != null) {
                mUserCapturedImageView.setImageBitmap(bitmap);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to show Gender Drop-down list items.
     */
    public void showGenderDropDownList() {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mDialogView = mInflater.inflate(R.layout.dialog_simple_drop_down, null);
        mAlertDialogBuilder.setView(mDialogView);

        final AlertDialog mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout mOuterLinearLayout = mDialogView.findViewById(R.id.outer_linear_layout);
        RecyclerView mCompanyListRecyclerView = (RecyclerView) mOuterLinearLayout.findViewById(R.id.recycler_view_drop_down_items);
        TextView mDialogTitle = mOuterLinearLayout.findViewById(R.id.drop_down_title);
        mDialogTitle.setText(R.string.label_gender);

        String[] mGenderArray = getActivity().getResources().getStringArray(R.array.gender_list);
        List<String> mGenderList = Arrays.asList(mGenderArray);

        SimpleDropDownAdapter simpleDropDownAdapter = new SimpleDropDownAdapter(getActivity(), mGenderList, mGenderEditText.getText().toString(),
                new SimpleDropDownAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(int position, String itemName) {
                        if (mGenderEditText != null) {
                            mGenderEditText.setText(itemName);
                        }
                        mAlertDialog.dismiss();
                    }
                });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mCompanyListRecyclerView.setLayoutManager(mLinearLayoutManager);
        mCompanyListRecyclerView.setAdapter(simpleDropDownAdapter);

        mAlertDialog.show();
    }

    /**
     * Method to show Weight Drop-down list items.
     */
    public void showWeightDropDownList() {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = mInflater.inflate(R.layout.dialog_simple_drop_down, null);
        mAlertDialogBuilder.setView(dialogView);

        final AlertDialog mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout mOuterLinearLayout = dialogView.findViewById(R.id.outer_linear_layout);
        RecyclerView mCompanyListRecyclerView = (RecyclerView) mOuterLinearLayout.findViewById(R.id.recycler_view_drop_down_items);
        TextView mDialogTitle = mOuterLinearLayout.findViewById(R.id.drop_down_title);
        mDialogTitle.setText(R.string.label_weight);

        String[] mWeightArray = getActivity().getResources().getStringArray(R.array.weight_list);
        List<String> mWeightList = Arrays.asList(mWeightArray);

        SimpleDropDownAdapter mSimpleDropDownAdapter = new SimpleDropDownAdapter(getActivity(), mWeightList, mWeightEditText.getText().toString(),
                new SimpleDropDownAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(int position, String itemName) {
                        if (mWeightEditText != null) {
                            mWeightEditText.setText(itemName);
                        }
                        mAlertDialog.dismiss();
                    }
                });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mCompanyListRecyclerView.setLayoutManager(mLinearLayoutManager);
        mCompanyListRecyclerView.setAdapter(mSimpleDropDownAdapter);

        mAlertDialog.show();
    }

    /**
     * Method to show Height Drop-down list items.
     */
    public void showHeightDropDownList() {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = mInflater.inflate(R.layout.dialog_simple_drop_down, null);
        mAlertDialogBuilder.setView(dialogView);

        final AlertDialog mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout mOuterLinearLayout = dialogView.findViewById(R.id.outer_linear_layout);
        RecyclerView mCompanyListRecyclerView = (RecyclerView) mOuterLinearLayout.findViewById(R.id.recycler_view_drop_down_items);
        TextView mDialogTitle = mOuterLinearLayout.findViewById(R.id.drop_down_title);
        mDialogTitle.setText(R.string.label_height);

        String[] mHeightArray = getActivity().getResources().getStringArray(R.array.height_list);
        List<String> mHeightList = Arrays.asList(mHeightArray);

        SimpleDropDownAdapter mSimpleDropDownAdapter = new SimpleDropDownAdapter(getActivity(), mHeightList, mHeightEditText.getText().toString(),
                new SimpleDropDownAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(int position, String itemName) {
                        if (mHeightEditText != null) {
                            mHeightEditText.setText(itemName);
                        }
                        mAlertDialog.dismiss();
                    }
                });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mCompanyListRecyclerView.setLayoutManager(mLinearLayoutManager);
        mCompanyListRecyclerView.setAdapter(mSimpleDropDownAdapter);

        mAlertDialog.show();
    }
}
