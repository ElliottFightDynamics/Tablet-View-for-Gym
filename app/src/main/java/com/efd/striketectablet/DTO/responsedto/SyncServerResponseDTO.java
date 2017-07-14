package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SyncServerResponseDTO extends HasSuccess {

    private List<SyncResponseDTO> jsonArrayResponse;

    public SyncServerResponseDTO() {}

    protected SyncServerResponseDTO(Parcel in) {
        this.jsonArrayResponse = in.createTypedArrayList(SyncResponseDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.jsonArrayResponse);
    }

    public List<SyncResponseDTO> getJsonArrayResponse(){
        return jsonArrayResponse;
    }

    public void setJsonArrayResponse(List<SyncResponseDTO> jsonArrayResponse){
        this.jsonArrayResponse = jsonArrayResponse;
    }


    public static final Parcelable.Creator<SyncServerResponseDTO> CREATOR = new Parcelable.Creator<SyncServerResponseDTO>() {
        @Override
        public SyncServerResponseDTO createFromParcel(Parcel source) {
            return new SyncServerResponseDTO(source);
        }

        @Override
        public SyncServerResponseDTO[] newArray(int size) {
            return new SyncServerResponseDTO[size];
        }
    };
}
