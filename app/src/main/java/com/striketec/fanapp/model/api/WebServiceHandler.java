package com.striketec.fanapp.model.api;

import com.striketec.fanapp.model.api.response.ResponseArray;
import com.striketec.fanapp.model.api.response.ResponseObject;
import com.striketec.fanapp.model.events.EventLocationInfo;
import com.striketec.fanapp.model.login.LoginReqInfo;
import com.striketec.fanapp.model.signup.dto.CompanyInfo;
import com.striketec.fanapp.model.signup.dto.NewUserInfo;
import com.striketec.fanapp.model.signup.dto.SignUpReqInfo;
import com.striketec.fanapp.utils.constants.Constants;
import com.striketec.fanapp.utils.constants.ErrorConstants;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sukhbirs on 16-11-2017.
 * This class is responsible to hit the web API and to send response back to caller.
 */

public class WebServiceHandler {

    // Singleton instance of WebServiceHandler
    private WebServiceHandler() {
    }

    public static WebServiceHandler getInstance() {
        return WebServiceHandlerHelper.INSTANCE;
    }

    /**
     * Method to call get companies list web API.
     *
     * @param onWebResponseListener
     */
    public void getCompaniesList(final OnWebResponseListener onWebResponseListener) {
        // call web API to get list of companies
        Call<ResponseArray<CompanyInfo>> call = RetrofitSingleton.getRestInterface().getCompaniesList();
        call.enqueue(new ResponseArrayCallback<ResponseArray<CompanyInfo>>(onWebResponseListener, RestUrl.GET_COMPANIES_LIST));
    }

    /**
     * Method to call the sign up web API.
     *
     * @param onWebResponseListener
     * @param signUpReqInfo
     */
    public void signUp(final OnWebResponseListener onWebResponseListener, SignUpReqInfo signUpReqInfo) {
        // call web API to sign up
        Call<ResponseObject<NewUserInfo>> call = RetrofitSingleton.getRestInterface().signUp(signUpReqInfo);
        call.enqueue(new ResponseObjectCallback<ResponseObject<NewUserInfo>>(onWebResponseListener, RestUrl.SIGN_UP));
    }

    /**
     * Method to call the login web API.
     *
     * @param onWebResponseListener
     * @param loginReqInfo
     */
    public void login(final OnWebResponseListener onWebResponseListener, LoginReqInfo loginReqInfo) {
        // call web API to sign up
        Call<ResponseObject<NewUserInfo>> call = RetrofitSingleton.getRestInterface().login(loginReqInfo);
        call.enqueue(new ResponseObjectCallback<ResponseObject<NewUserInfo>>(onWebResponseListener, RestUrl.LOGIN));
    }

    /**
     * Method to call get event locations list web API.
     *
     * @param onWebResponseListener
     */
    public void getEventLocationsList(final OnWebResponseListener onWebResponseListener, String token) {
        // call web API to get list of event locations
        Call<ResponseArray<EventLocationInfo>> call = RetrofitSingleton.getRestInterface().getEventLocationsList(token);
        call.enqueue(new ResponseArrayCallback<ResponseArray<EventLocationInfo>>(onWebResponseListener, RestUrl.GET_EVENT_LOCATIONS));
    }

    private static class WebServiceHandlerHelper {
        private static WebServiceHandler INSTANCE = new WebServiceHandler();
    }

    /**
     * To handle the data response of type JSONArray.
     *
     * @param <T>
     */
    class ResponseArrayCallback<T> implements Callback<T> {

        OnWebResponseListener onWebResponseListener;
        // whichApi represents that which web API gets called.
        String whichApi;

        ResponseArrayCallback(OnWebResponseListener onWebResponseListener, String whichApi) {
            this.onWebResponseListener = onWebResponseListener;
            this.whichApi = whichApi;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response != null) {
                ResponseArray<T> responseBody = (ResponseArray<T>) response.body();
                if (responseBody != null) {
                    if (responseBody.getmError().equalsIgnoreCase(Constants.FALSE)) {
                        onWebResponseListener.onResponseSuccess(responseBody, whichApi);
                    } else {
                        onWebResponseListener.onResponseError(responseBody.getmMessage());
                    }
                } else {
                    onWebResponseListener.onResponseError(ErrorConstants.INVALID_RESPONSE_FROM_SERVER);
                }
            } else {
                onWebResponseListener.onResponseError(ErrorConstants.INVALID_RESPONSE_FROM_SERVER);
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            if (t instanceof SocketTimeoutException) {
                onWebResponseListener.onResponseError(ErrorConstants.SOCKET_TIMEOUT_ERROR);
            } else if (t instanceof ConnectException) {
                onWebResponseListener.onResponseError(ErrorConstants.INTERNET_CONNECTION_FAILURE);
            } else {
                onWebResponseListener.onResponseError(ErrorConstants.SERVER_NOT_RESPONDING);
            }
        }
    }

    /**
     * Method to handle data response of type JSONObject.
     *
     * @param <T>
     */
    class ResponseObjectCallback<T> implements Callback<T> {

        OnWebResponseListener onWebResponseListener;
        String whichApi;

        ResponseObjectCallback(OnWebResponseListener onWebResponseListener, String whichApi) {
            this.onWebResponseListener = onWebResponseListener;
            this.whichApi = whichApi;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response != null) {
                ResponseObject<T> responseBody = (ResponseObject<T>) response.body();
                if (responseBody != null) {
                    if (responseBody.getmError().equalsIgnoreCase(Constants.FALSE)) {
                        onWebResponseListener.onResponseSuccess(responseBody, whichApi);
                    } else {
                        onWebResponseListener.onResponseError(responseBody.getmMessage());
                    }
                } else {
                    onWebResponseListener.onResponseError(ErrorConstants.INVALID_RESPONSE_FROM_SERVER);
                }
            } else {
                onWebResponseListener.onResponseError(ErrorConstants.INVALID_RESPONSE_FROM_SERVER);
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            if (t instanceof SocketTimeoutException) {
                onWebResponseListener.onResponseError(ErrorConstants.SOCKET_TIMEOUT_ERROR);
            } else if (t instanceof ConnectException) {
                onWebResponseListener.onResponseError(ErrorConstants.INTERNET_CONNECTION_FAILURE);
            } else {
                onWebResponseListener.onResponseError(ErrorConstants.SERVER_NOT_RESPONDING);
            }
        }
    }
}
