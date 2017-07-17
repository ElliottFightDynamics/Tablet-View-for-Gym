package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class PunchLogPlottingDTO extends HasId {

    private String Time;
    private String Acceleration;
    private String Velocity;
    private String ImpactMask;

    public PunchLogPlottingDTO() {}

    protected PunchLogPlottingDTO(Parcel in) {
        this.Time = in.readString();
        this.Acceleration = in.readString();
        this.Velocity = in.readString();
        this.ImpactMask = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Time);
        dest.writeString(this.Acceleration);
        dest.writeString(this.Velocity);
        dest.writeString(this.ImpactMask);
    }

    public String getTime(){
        return Time;
    }

    public String getAcceleration(){
        return Acceleration;
    }

    public String getVelocity(){
        return Velocity;
    }

    public String getImpactMask(){
        return ImpactMask;
    }

    public static final Creator<PunchLogPlottingDTO> CREATOR = new Creator<PunchLogPlottingDTO>() {
        @Override
        public PunchLogPlottingDTO createFromParcel(Parcel source) {
            return new PunchLogPlottingDTO(source);
        }

        @Override
        public PunchLogPlottingDTO[] newArray(int size) {
            return new PunchLogPlottingDTO[size];
        }
    };
}
