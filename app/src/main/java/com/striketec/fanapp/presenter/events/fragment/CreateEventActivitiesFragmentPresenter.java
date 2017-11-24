package com.striketec.fanapp.presenter.events.fragment;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Sukhbirs on 21-11-2017.
 * This is presenter interface for Create Event Step 2 Select Activity screen.
 */

public interface CreateEventActivitiesFragmentPresenter {
    void setActivityListData(RecyclerView recyclerView);

    void validateEventSelectedActivity(String selectedActivity);

    void onDestroy();
}
