package com.striketec.fanapp.presenter.events.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.striketec.fanapp.R;
import com.striketec.fanapp.model.api.RestUrl;
import com.striketec.fanapp.model.api.response.ResponseArray;
import com.striketec.fanapp.model.events.EventGeneralInfo;
import com.striketec.fanapp.model.events.EventLocationInfo;
import com.striketec.fanapp.model.events.fragment.CreateEventInfoFragmentModel;
import com.striketec.fanapp.model.events.fragment.CreateEventInfoFragmentModelImpl;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.view.events.adapter.EventLocationListAdapter;
import com.striketec.fanapp.view.events.fragment.CreateEventInfoFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventInfoFragmentInteractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sukhbirs on 22-11-2017.
 * This is Presenter implementation class for CreateEventInfoFragment i.e. Create Event Step 1 Event Info screen.
 */

public class CreateEventInfoFragmentPresenterImpl implements CreateEventInfoFragmentsPresenter, CreateEventInfoFragmentModel.OnLoadEventLocationListener {

    private CreateEventInfoFragmentInteractor mCreateEventInfoFragmentInteractor;
    private CreateEventInfoFragment mCreateEventInfoFragment;
    private CreateEventInfoFragmentModel mCreateEventInfoFragmentModel;
    private EventLocationInfo mLastSelectedEventLocationInfo, mNewSelectedEventLocationInfo;
    private boolean isOpenDialogLater;

    public CreateEventInfoFragmentPresenterImpl(CreateEventInfoFragmentInteractor mCreateEventInfoFragmentInteractor) {
        this.mCreateEventInfoFragmentInteractor = mCreateEventInfoFragmentInteractor;
        this.mCreateEventInfoFragment = (CreateEventInfoFragment) mCreateEventInfoFragmentInteractor;
        mCreateEventInfoFragmentModel = new CreateEventInfoFragmentModelImpl(this);
    }

    @Override
    public void showDatePicker(final EditText mEditText) {
        final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_MM_DD_YYYY, Locale.US);
        Calendar calendar = Calendar.getInstance();
        int year = 0, month = 0, dayOfMonth = 0;
        String previousSelectedDate = mEditText.getText().toString();
        if (previousSelectedDate != null && previousSelectedDate.length() > 0) {
            // showing previous selected date to DatePickerDialog.
            try {
                Date date = mSimpleDateFormat.parse(previousSelectedDate);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(mCreateEventInfoFragment.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                mEditText.setText(mSimpleDateFormat.format(newDate.getTime()));
            }
        }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();

    }

    @Override
    public void showTimePicker(final EditText mEditText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String previousTime = mEditText.getText().toString().trim();
        if (previousTime != null && previousTime.length() > 0) {
            try {
                String[] split = previousTime.split(":");
                hour = Integer.parseInt(split[0]);
                minute = Integer.parseInt(split[1]);
            } catch (Exception e) {
            }
        }
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mCreateEventInfoFragment.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour, minute;
                if (selectedHour < 10) {
                    hour = "0" + selectedHour;
                } else {
                    hour = String.valueOf(selectedHour);
                }
                if (selectedMinute < 10) {
                    minute = "0" + selectedMinute;
                } else {
                    minute = String.valueOf(selectedMinute);
                }
                mEditText.setText(hour + ":" + minute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @Override
    public void validateEventGeneralInfoOnNext(EventGeneralInfo eventGeneralInfo) {
        // navigate to next step on Create Event Screen.
        mCreateEventInfoFragmentInteractor.navigateToCreateEventStep2();

        /*if (TextUtils.isEmpty(eventGeneralInfo.getEventTitle())) {
            mCreateEventInfoFragmentInteractor.setEventTitleError();
        }
        *//*else if (TextUtils.isEmpty(eventGeneralInfo.getEventLocation())) {
            mCreateEventInfoFragmentInteractor.setEventLocationError();
        } *//*
        *//*else if (TextUtils.isEmpty(eventGeneralInfo.getEventDescription())) {
            mCreateEventInfoFragmentInteractor.setEventDescriptionError();
        } *//*
        else if (TextUtils.isEmpty(eventGeneralInfo.getEventStartDate())) {
            mCreateEventInfoFragmentInteractor.setEventStartDateError();
        } else if (TextUtils.isEmpty(eventGeneralInfo.getEventStartTime())) {
            mCreateEventInfoFragmentInteractor.setEventStartTimeError();
        } else if (TextUtils.isEmpty(eventGeneralInfo.getEventEndDate())) {
            mCreateEventInfoFragmentInteractor.setEventEndDateError();
        } else if (TextUtils.isEmpty(eventGeneralInfo.getEventEndTime())) {
            mCreateEventInfoFragmentInteractor.setEventEndTimeError();
        } else {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_MM_DD_YYYY, Locale.US);
            try {
                if (mSimpleDateFormat.parse(eventGeneralInfo.getEventStartDate()).after(mSimpleDateFormat.parse(eventGeneralInfo.getEventEndDate()))){
                    mCreateEventInfoFragmentInteractor.setEventStartDateAfterEndDateError();
                } else if (mSimpleDateFormat.parse(eventGeneralInfo.getEventStartDate()).equals(mSimpleDateFormat.parse(eventGeneralInfo.getEventEndDate()))){
                    SimpleDateFormat mSTF = new SimpleDateFormat(Constants.TIME_FORMAT_HH_MM, Locale.US);
                    if (mSTF.parse(eventGeneralInfo.getEventStartTime()).after(mSTF.parse(eventGeneralInfo.getEventEndTime()))){
                        mCreateEventInfoFragmentInteractor.setEventStartTimeAfterEndTimeError();
                    } else {
                        // navigate to next step on Create Event Screen.
                        mCreateEventInfoFragmentInteractor.navigateToCreateEventStep2();
                    }
                } else {
                    // navigate to next step on Create Event Screen.
                    mCreateEventInfoFragmentInteractor.navigateToCreateEventStep2();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public void onDetach() {
        mCreateEventInfoFragmentInteractor = null;
        mCreateEventInfoFragment = null;
    }

    @Override
    public void loadEventLocationsFromServer(String token, boolean isOpenDialogLater) {
        this.isOpenDialogLater = isOpenDialogLater;
        if (mCreateEventInfoFragmentInteractor != null) {
            mCreateEventInfoFragmentInteractor.showProgressBar();
        }
        mCreateEventInfoFragmentModel.loadEventLocationsListFromServer(token);
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        if (mCreateEventInfoFragmentInteractor != null) {
            mCreateEventInfoFragmentInteractor.hideProgressBar();
            // check whether it is response of event locations list data web API or other web API.
            if (whichApi != null) {
                if (whichApi.equals(RestUrl.GET_EVENT_LOCATIONS)) {
                    if (responseObject != null) {
                        ResponseArray<EventLocationInfo> responseArray = (ResponseArray<EventLocationInfo>) responseObject;
                        List<EventLocationInfo> eventLocationInfoList = responseArray.getmData();
                        if (isOpenDialogLater) {
                            mCreateEventInfoFragmentInteractor.setEventLocationsListAndOpenLocationDialog(eventLocationInfoList);
                        } else {
                            mCreateEventInfoFragmentInteractor.setEventLocationsList(eventLocationInfoList);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onResponseError(String errorMessage) {
        mCreateEventInfoFragmentInteractor.setWebApiError(errorMessage);
    }

    @Override
    public void showEventLocationSpinner(List<EventLocationInfo> eventLocationInfoList) {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mCreateEventInfoFragment.getActivity());
        LayoutInflater mInflater = (LayoutInflater) (mCreateEventInfoFragment.getActivity()).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mDialogView = mInflater.inflate(R.layout.dialog_event_location_list, null);
        mAlertDialogBuilder.setView(mDialogView);

        final AlertDialog mAlertDialog = mAlertDialogBuilder.create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout mOuterLinearLayout = mDialogView.findViewById(R.id.outer_linear_layout);
        RecyclerView mEventLocationListRecyclerView = mOuterLinearLayout.findViewById(R.id.recycler_view_event_location);
        RelativeLayout mBottomRelativeLayout = mOuterLinearLayout.findViewById(R.id.bottom_relative_layout);
        Button mCancelButton = mBottomRelativeLayout.findViewById(R.id.button_cancel);
        Button mOkButton = mBottomRelativeLayout.findViewById(R.id.button_ok);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initialize the last selected company instance with new selected company info on click of OK button.
                mLastSelectedEventLocationInfo = mNewSelectedEventLocationInfo;
                (mCreateEventInfoFragment.getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCreateEventInfoFragmentInteractor.setSelectedEventLocationInfo(mNewSelectedEventLocationInfo);
                        mAlertDialog.dismiss();
                    }
                });
            }
        });

        // populate the company list data on Alert Dialog using adapter.
        EventLocationListAdapter mCompanyListAdapter = new EventLocationListAdapter(mCreateEventInfoFragment.getActivity(), eventLocationInfoList,
                mLastSelectedEventLocationInfo, new EventLocationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, EventLocationInfo eventLocationInfo) {
                mNewSelectedEventLocationInfo = eventLocationInfo;
            }
        });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mCreateEventInfoFragment.getActivity());
        mEventLocationListRecyclerView.setLayoutManager(mLinearLayoutManager);
        mEventLocationListRecyclerView.setAdapter(mCompanyListAdapter);

        mAlertDialog.show();
    }
}
