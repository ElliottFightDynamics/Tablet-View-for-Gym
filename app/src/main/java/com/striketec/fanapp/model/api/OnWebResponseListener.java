package com.striketec.fanapp.model.api;

/**
 * Created by Sukhbirs on 16-11-2017.
 * This is responsible to receive web API response or error message as callback.
 */

public interface OnWebResponseListener {
    void onResponseSuccess(Object responseObject, String whichApi);

    void onResponseError(String errorMessage);
}
