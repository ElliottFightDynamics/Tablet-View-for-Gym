package com.striketec.fanapp.presenter.events.fragment;

import android.widget.EditText;

import com.striketec.fanapp.model.events.EventGeneralInfo;
import com.striketec.fanapp.model.events.EventLocationInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 22-11-2017.
 * This is presenter interface for Create Event Step 1 Screen.
 */

public interface CreateEventInfoFragmentsPresenter {
    void showDatePicker(EditText mEditText);

    void showTimePicker(EditText mEditText);

    void validateEventGeneralInfoOnNext(EventGeneralInfo eventGeneralInfo);

    void onDetach();

    void loadEventLocationsFromServer(String token, boolean isOpenDialogLater);

    void showEventLocationSpinner(List<EventLocationInfo> eventLocationInfoList);
}
