package com.striketec.fanapp.view.events.fragment;

/**
 * Created by Sukhbirs on 17-11-2017.
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
    void navigateToCreateEventStep2();
}

