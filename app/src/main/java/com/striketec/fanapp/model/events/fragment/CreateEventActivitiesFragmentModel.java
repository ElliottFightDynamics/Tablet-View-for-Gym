package com.striketec.fanapp.model.events.fragment;

import com.striketec.fanapp.view.events.CreateEventActivityInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 21-11-2017.
 */

public interface CreateEventActivitiesFragmentModel {
    /**
     * Method to get the list of activities to select while creating the event.
     * @return
     */
    List<CreateEventActivityInfo> getActivityInfoList();
}
