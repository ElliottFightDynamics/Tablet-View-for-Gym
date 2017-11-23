package com.striketec.fanapp.model.events.fragment;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This is used as an Model interface for Create Event Info step 1 screen.
 */

public interface CreateEventInfoFragmentModel {
    void loadEventLocationsListFromServer(String token);

    interface OnLoadEventLocationListener {
        void onResponseSuccess(Object responseObject, String whichApi);

        void onResponseError(String errorMessage);
    }
}
