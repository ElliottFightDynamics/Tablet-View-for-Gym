package com.striketec.fanapp.model.events.fragment;

import com.striketec.fanapp.model.api.OnWebResponseListener;
import com.striketec.fanapp.model.api.WebServiceHandler;

/**
 * Created by Sukhbirs on 23-11-2017.
 * This is Create Event Step 1 General Info model implementation class.
 */

public class CreateEventInfoFragmentModelImpl implements CreateEventInfoFragmentModel, OnWebResponseListener {

    private OnLoadEventLocationListener mOnLoadEventLocationListener;

    public CreateEventInfoFragmentModelImpl(OnLoadEventLocationListener onLoadEventLocationListener) {
        this.mOnLoadEventLocationListener = onLoadEventLocationListener;
    }

    @Override
    public void loadEventLocationsListFromServer(String token) {
        WebServiceHandler.getInstance().getEventLocationsList(this, token);
    }

    @Override
    public void onResponseSuccess(Object responseObject, String whichApi) {
        mOnLoadEventLocationListener.onResponseSuccess(responseObject, whichApi);
    }

    @Override
    public void onResponseError(String errorMessage) {
        mOnLoadEventLocationListener.onResponseError(errorMessage);
    }
}
