package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class ResetpwdDTO extends HasSuccess {

    private String sendStatus;

    public ResetpwdDTO() {}

    protected ResetpwdDTO(Parcel in) {
        this.sendStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sendStatus);
    }

    public String getSendStatus(){
        return sendStatus;
    }

    public void setSendStatus(String sendStatus){
        this.sendStatus = sendStatus;
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
