package com.efd.striketectablet.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CountryDTO implements Parcelable {

    private int id;

    public CountryDTO() {}

    protected CountryDTO(Parcel in) {
        this.id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryDTO> CREATOR = new Creator<CountryDTO>() {
        @Override
        public CountryDTO createFromParcel(Parcel source) {
            return new CountryDTO(source);
        }

        @Override
        public CountryDTO[] newArray(int size) {
            return new CountryDTO[size];
        }
    };
}
