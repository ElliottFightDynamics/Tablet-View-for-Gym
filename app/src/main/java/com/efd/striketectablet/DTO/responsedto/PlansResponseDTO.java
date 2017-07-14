package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class PlansResponseDTO extends HasSuccess {

    private String combo;
    private String sets;
    private String workout;
    private String preset;

    public PlansResponseDTO() {}

    protected PlansResponseDTO(Parcel in) {
        this.combo = in.readString();
        this.sets = in.readString();
        this.workout = in.readString();
        this.preset = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.combo);
        dest.writeString(this.sets);
        dest.writeString(this.workout);
        dest.writeString(this.preset);
    }

    public String getCombo(){
        return combo;
    }

    public void setCombo(String combo){
        this.combo = combo;
    }

    public String getSets(){
        return sets;
    }

    public void setSets(String sets){
        this.sets = sets;
    }

    public String getWorkout(){
        return workout;
    }

    public void setWorkout(String workout){
        this.workout = workout;
    }

    public String getPreset(){
        return preset;
    }

    public void setPreset(String preset){
        this.preset = preset;
    }

    public static final Creator<PlansResponseDTO> CREATOR = new Creator<PlansResponseDTO>() {
        @Override
        public PlansResponseDTO createFromParcel(Parcel source) {
            return new PlansResponseDTO(source);
        }

        @Override
        public PlansResponseDTO[] newArray(int size) {
            return new PlansResponseDTO[size];
        }
    };
}
