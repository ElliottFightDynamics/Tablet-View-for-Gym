package com.striketec.fanapp.view.events.fragment;

import com.striketec.fanapp.model.users.dto.UserInfo;

import java.util.List;

/**
 * Created by Sukhbirs on 17-11-2017.
 * This is interactor interface for Create Event Step 3 add participants screen.
 */

public interface CreateEventParticipantsFragmentInteractor {
    void showProgressBar();

    void hideProgressBar();

    void setUsersList(List<UserInfo> userInfoList);

    void showProgressDialog();

    void hideProgressDialog();

    void navigateToPreviousScreen(String message);

    void setWebApiError(String errorMessage);
}

