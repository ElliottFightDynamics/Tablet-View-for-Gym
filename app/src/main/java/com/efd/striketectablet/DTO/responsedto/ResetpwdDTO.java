package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class ResetpwdDTO implements Parcelable {

    private boolean success;
    private String sendStatus;

    public ResetpwdDTO() {}

    protected ResetpwdDTO(Parcel in) {
        this.success = in.readByte() != 0;
        this.sendStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeString(this.sendStatus);
    }

    public boolean getSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public String getSendStatus(){
        return sendStatus;
    }

    public void setSendStatus(String sendStatus){
        this.sendStatus = sendStatus;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResetpwdDTO> CREATOR = new Creator<ResetpwdDTO>() {
        @Override
        public ResetpwdDTO createFromParcel(Parcel source) {
            return new ResetpwdDTO(source);
        }

        @Override
        public ResetpwdDTO[] newArray(int size) {
            return new ResetpwdDTO[size];
        }
    };
}
