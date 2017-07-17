package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

import java.util.List;

public class PunchWindowPlottingDTO extends HasSuccess {

    String error;
    List<PunchLogPlottingDTO> data;

    public String getError(){
       return error;
   }

    public List<PunchLogPlottingDTO> getData(){
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected PunchWindowPlottingDTO(Parcel in) {
        this.error = in.readString();
        this.data = in.createTypedArrayList(PunchLogPlottingDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
        dest.writeTypedList(this.data);
    }

    public static final Creator<PunchWindowPlottingDTO> CREATOR = new Creator<PunchWindowPlottingDTO>() {
        @Override
        public PunchWindowPlottingDTO createFromParcel(Parcel source) {
            return new PunchWindowPlottingDTO(source);
        }

        @Override
        public PunchWindowPlottingDTO[] newArray(int size) {
            return new PunchWindowPlottingDTO[size];
        }
    };
}
