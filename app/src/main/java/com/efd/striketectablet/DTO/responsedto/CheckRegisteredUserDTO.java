package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class CheckRegisteredUserDTO extends HasSuccess {

    private boolean isUnRegisteredUser;

    public CheckRegisteredUserDTO() {}

    protected CheckRegisteredUserDTO(Parcel in) {
        this.isUnRegisteredUser = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isUnRegisteredUser ? (byte) 1 : (byte) 0);
    }

    public boolean getUnregisteredUser(){
        return isUnRegisteredUser;
    }

    public void setUnRegisteredUser(boolean sendStatus){
        this.isUnRegisteredUser = isUnRegisteredUser;
    }

    public static final Creator<CheckRegisteredUserDTO> CREATOR = new Creator<CheckRegisteredUserDTO>() {
        @Override
        public CheckRegisteredUserDTO createFromParcel(Parcel source) {
            return new CheckRegisteredUserDTO(source);
        }

        @Override
        public CheckRegisteredUserDTO[] newArray(int size) {
            return new CheckRegisteredUserDTO[size];
        }
    };
}
