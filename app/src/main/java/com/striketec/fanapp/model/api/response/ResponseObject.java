package com.striketec.fanapp.model.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sukhbirs on 16-11-2017.
 */

public class ResponseObject<T> {
    @SerializedName("error")
    private String mError;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("token")
    private String mToken;
    @SerializedName("data")
    private T mData;

    public String getmError() {
        return mError;
    }

    public void setmError(String mError) {
        this.mError = mError;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public T getmData() {
        return mData;
    }

    public void setmData(T mData) {
        this.mData = mData;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "mError='" + mError + '\'' +
                ", mMessage='" + mMessage + '\'' +
                ", mToken='" + mToken + '\'' +
                ", mData=" + mData +
                '}';
    }
}
