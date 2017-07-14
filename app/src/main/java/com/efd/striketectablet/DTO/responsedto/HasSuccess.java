package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class HasSuccess implements Parcelable {

    private boolean success;
    private boolean access;

    public HasSuccess() {}

    protected HasSuccess(Parcel in) {
        this.success = in.readByte() != 0;
        this.access = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeByte(this.access ? (byte) 1 : (byte) 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }


}
