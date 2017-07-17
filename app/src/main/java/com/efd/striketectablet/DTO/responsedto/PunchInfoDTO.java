package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;

public class PunchInfoDTO extends HasId {

    private String punch_window_start;
    private String impact;
    private String thrust_duration;
    private String punch_speed;
    private String punch_window_end;
    private String punch_type;
    private String impulse;
    private String peak;

    public PunchInfoDTO() {}

    protected PunchInfoDTO(Parcel in) {
        this.punch_window_start = in.readString();
        this.impact = in.readString();
        this.thrust_duration = in.readString();
        this.punch_speed = in.readString();
        this.punch_window_end = in.readString();
        this.punch_type = in.readString();
        this.impulse = in.readString();
        this.peak = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.punch_window_start);
        dest.writeString(this.impact);
        dest.writeString(this.thrust_duration);
        dest.writeString(this.punch_speed);
        dest.writeString(this.punch_window_end);
        dest.writeString(this.punch_type);
        dest.writeString(this.impulse);
        dest.writeString(this.peak);
    }

    public PunchInfoDTO(String punch_window_start, String impact, String thrust_duration, String punch_speed, String punch_window_end, String punch_type, String impulse, String peak) {
        this.punch_window_start = punch_window_start;
        this.impact = impact;
        this.thrust_duration = thrust_duration;
        this.punch_speed = punch_speed;
        this.punch_window_end = punch_window_end;
        this.punch_type = punch_type;
        this.impulse = impulse;
        this.peak = peak;
    }

    public String getPunch_window_start(){
        return punch_window_start;
    }

    public String getImpact(){
        return impact;
    }

    public String getThrust_duration(){
        return thrust_duration;
    }

    public String getPunch_speed(){
        return punch_speed;
    }

    public String getPunch_window_end(){
        return punch_window_end;
    }

    public String getPunch_type(){
        return punch_type;
    }

    public String getImpulse(){
        return impulse;
    }

    public String getPeak(){
        return peak;
    }



    public static final Creator<PunchInfoDTO> CREATOR = new Creator<PunchInfoDTO>() {
        @Override
        public PunchInfoDTO createFromParcel(Parcel source) {
            return new PunchInfoDTO(source);
        }

        @Override
        public PunchInfoDTO[] newArray(int size) {
            return new PunchInfoDTO[size];
        }
    };
}
