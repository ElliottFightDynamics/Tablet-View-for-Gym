package com.striketec.fanapp.model.events.fragment;

import com.striketec.fanapp.model.api.OnWebResponseListener;
import com.striketec.fanapp.model.api.WebServiceHandler;
import com.striketec.fanapp.model.events.dto.CreateEventInfo;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This is model implementation class for Create Event Step 3 add participants screen.
 */

public class CreateEventParticipantsFragmentModelImpl implements CreateEventParticipantsFragmentModel, OnWebResponseListener {

    private OnLoadParticipantsListener mOnLoadParticipantsListener;

    public CreateEventParticipantsFragmentModelImpl(OnLoadParticipantsListener onLoadParticipantsListener) {
        this.mOnLoadParticipantsListener = onLoadParticipantsListener;
    }

    @Override
    public void loadUsersListFromServer(String token) {
        WebServiceHandler.getInstance().getUsersList(this, token);
    }

    @Override
    public void createEvent(String token, CreateEventInfo createEventInfo) {
        WebServiceHandler.getInstance().createEvent(this, token, createEventInfo);
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        mOnLoadParticipantsListener.onResponseSuccess(responseObject, whichApi);
    }

    @Override
    public void onResponseError(String errorMessage) {
        mOnLoadParticipantsListener.onResponseError(errorMessage);
    }
}
