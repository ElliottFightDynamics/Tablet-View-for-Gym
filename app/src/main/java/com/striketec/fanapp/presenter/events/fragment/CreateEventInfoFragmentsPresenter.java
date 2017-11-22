package com.striketec.fanapp.presenter.events.fragment;

import android.widget.EditText;

import com.striketec.fanapp.model.events.EventGeneralInfo;
import com.striketec.fanapp.model.users.dto.UserInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 22-11-2017.
 */

public interface CreateEventInfoFragmentsPresenter {
    void showDatePicker(EditText mEditText);
    void showTimePicker(EditText mEditText);
    void validateEventGeneralInfoOnNext(EventGeneralInfo eventGeneralInfo);
    /*void validateEventActivitiesOnNext(String selectedActivity);
    void validateEventParticipantsOnNext(List<UserInfo> userInfoList);*/
}
