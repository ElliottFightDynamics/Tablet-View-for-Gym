package com.striketec.fanapp.view.events.fragment;

/**
 * Created by Sukhbirs on 17-11-2017.
 * This is interactor interface for Create Event Step 2 Select Activity screen.
 */

public interface CreateEventActivitiesFragmentInteractor {
    void setSelectedActivity(String selectedActivity);

    void navigateToCreateEventStep3();

    void setSelectActivityError();
}

