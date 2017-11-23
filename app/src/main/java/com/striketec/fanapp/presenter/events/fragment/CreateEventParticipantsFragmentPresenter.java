package com.striketec.fanapp.presenter.events.fragment;


/**
 * Created by Sukhbirs on 23-11-2017.
 * This is presenter interface for Create event Step 3 add participants screen.
 */

public interface CreateEventParticipantsFragmentPresenter {
    void loadUsersListFromServer();

    void onDetach();
}
