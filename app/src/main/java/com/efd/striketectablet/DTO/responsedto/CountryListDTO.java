package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CountryListDTO implements Parcelable {

    private List<CountryDTO> countryList;

    public CountryListDTO() {}

    protected CountryListDTO(Parcel in) {
        this.countryList = in.createTypedArrayList(CountryDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.countryList);
    }

    public List<CountryDTO> getCountryList(){
        return countryList;
    }

    public void setCountryList(List<CountryDTO> questionList){
        this.countryList = questionList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryListDTO> CREATOR = new Creator<CountryListDTO>() {
        @Override
        public CountryListDTO createFromParcel(Parcel source) {
            return new CountryListDTO(source);
        }

        @Override
        public CountryListDTO[] newArray(int size) {
            return new CountryListDTO[size];
        }
    };
}
