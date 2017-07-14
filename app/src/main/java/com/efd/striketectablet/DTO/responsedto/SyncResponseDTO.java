package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class SyncResponseDTO extends HasId{
    String serverTime;

    protected SyncResponseDTO(Parcel in) {
        this.serverTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.serverTime);
    }

    public SyncResponseDTO(){

    }

    public void setServerTime(String serverTime){
        this.serverTime = serverTime;
    }
    public String getServerTime() {
        return serverTime;
    }

    public static final Parcelable.Creator<SyncResponseDTO> CREATOR = new Parcelable.Creator<SyncResponseDTO>() {
        @Override
        public SyncResponseDTO createFromParcel(Parcel source) {
            return new SyncResponseDTO(source);
        }

        @Override
        public SyncResponseDTO[] newArray(int size) {
            return new SyncResponseDTO[size];
        }
    };

}
