package com.striketec.fanapp.view.events.fragment;

import com.striketec.fanapp.model.events.dto.EventGeneralInfo;
import com.striketec.fanapp.model.events.dto.EventLocationInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 17-11-2017.
 * This is interactor interface for Create Event Step 1 general info screen.
 */

public interface CreateEventInfoFragmentInteractor {
    void setEventTitleError();

    void setEventLocationError();

    void setEventDescriptionError();

    void setEventStartDateError();

    void setEventStartTimeError();

    void setEventEndDateError();

    void setEventEndTimeError();

    void setEventStartDateAfterEndDateError();

    void setEventStartTimeAfterEndTimeError();

    void showProgressBar();

    void hideProgressBar();

    void setEventLocationsList(List<EventLocationInfo> eventLocationsList);

    void setEventLocationsListAndOpenLocationDialog(List<EventLocationInfo> eventLocationsList);

    void setSelectedEventLocationInfo(EventLocationInfo eventLocationInfo);

    void setEnteredEventGeneralInfo(EventGeneralInfo eventGeneralInfo);

    void setWebApiError(String errorMessage);

    void navigateToCreateEventStep2();
}

