package com.striketec.fanapp.model.events.fragment;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This is Model interface for Create Event Step 3 Add Participants.
 */

public interface CreateEventParticipantsFragmentModel {
    /**
     * Method to get the list of users to select as participant while creating the event.
     */
    void loadUsersListFromServer();

    interface OnLoadParticipantsListener {
        void onResponseSuccess(Object responseObject, String whichApi);

        void onResponseError(String errorMessage);
    }
}
