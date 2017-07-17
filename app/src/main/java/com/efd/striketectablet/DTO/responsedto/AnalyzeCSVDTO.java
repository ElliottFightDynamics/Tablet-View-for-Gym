package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AnalyzeCSVDTO implements Parcelable {

    private String error;
    private String fileName;

    public AnalyzeCSVDTO() {}

    protected AnalyzeCSVDTO(Parcel in) {
        this.error = in.readString();
        this.fileName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.error);
        dest.writeString(this.fileName);
    }

    public String getError(){
        return error;
    }

    public String getFileName(){
        return fileName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnalyzeCSVDTO> CREATOR = new Creator<AnalyzeCSVDTO>() {
        @Override
        public AnalyzeCSVDTO createFromParcel(Parcel source) {
            return new AnalyzeCSVDTO(source);
        }

        @Override
        public AnalyzeCSVDTO[] newArray(int size) {
            return new AnalyzeCSVDTO[size];
        }
    };
}
