package com.striketec.fanapp.presenter.events.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.striketec.fanapp.model.events.EventGeneralInfo;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.view.events.fragment.CreateEventInfoFragment;
import com.striketec.fanapp.view.events.fragment.CreateEventInfoFragmentInteractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sukhbirs on 22-11-2017.
 * This is Presenter implementation class for CreateEventInfoFragment i.e. Create Event Step 1 Event Info screen.
 */

public class CreateEventInfoFragmentPresenterImpl implements CreateEventInfoFragmentsPresenter {

    private CreateEventInfoFragmentInteractor mCreateEventInfoFragmentInteractor;
    private CreateEventInfoFragment mCreateEventInfoFragment;

    public CreateEventInfoFragmentPresenterImpl(CreateEventInfoFragmentInteractor mCreateEventInfoFragmentInteractor) {
        this.mCreateEventInfoFragmentInteractor = mCreateEventInfoFragmentInteractor;
        this.mCreateEventInfoFragment = (CreateEventInfoFragment) mCreateEventInfoFragmentInteractor;
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
        if (previousTime != null && previousTime.length() > 0){
            try {
                String[] split = previousTime.split(":");
                hour = Integer.parseInt(split[0]);
                minute = Integer.parseInt(split[1]);
            }catch (Exception e){}
        }
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mCreateEventInfoFragment.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour, minute;
                if (selectedHour < 10){
                    hour = "0" + selectedHour;
                } else {
                    hour = String.valueOf(selectedHour);
                }
                if (selectedMinute < 10){
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
        if (TextUtils.isEmpty(eventGeneralInfo.getEventTitle())) {
            mCreateEventInfoFragmentInteractor.setEventTitleError();
        } else if (TextUtils.isEmpty(eventGeneralInfo.getEventLocation())) {
            mCreateEventInfoFragmentInteractor.setEventLocationError();
        } else if (TextUtils.isEmpty(eventGeneralInfo.getEventDescription())) {
            mCreateEventInfoFragmentInteractor.setEventDescriptionError();
        } else if (TextUtils.isEmpty(eventGeneralInfo.getEventStartDate())) {
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

                } else {
                    // navigate to next step on Create Event Screen.
                    mCreateEventInfoFragmentInteractor.navigateToCreateEventStep2();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
