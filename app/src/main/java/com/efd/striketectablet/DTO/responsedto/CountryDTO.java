package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class CountryDTO extends HasId{

    private String name;

    public CountryDTO() {}

    protected CountryDTO(Parcel in) {
        this.name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
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
