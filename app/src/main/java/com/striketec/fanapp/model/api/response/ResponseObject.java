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
                ", mData=" + mData +
                '}';
    }
}
