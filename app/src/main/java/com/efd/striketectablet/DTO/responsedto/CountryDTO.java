package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class CountryDTO implements Parcelable {

    private int id;
    private String name;

    public CountryDTO() {}

    protected CountryDTO(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
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
