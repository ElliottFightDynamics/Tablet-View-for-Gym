package com.efd.striketectablet.DTO.responsedto;

import android.os.Parcel;
import android.os.Parcelable;

public class DBTrainingPunchDetailDTO implements Parcelable{

    private Integer id;
    private String punchType;
    private Double force;
    private Double speed;
    private String punchTime;
    private Integer userID;
    private String serverTime;

    public DBTrainingPunchDetailDTO(Integer id, String punchType, Double speed, Double force, String punchTime, Integer userID, String serverTime){
        super();
        this.id = id;
        this.punchType = punchType;
        this.speed = speed;
        this.force = force;
        this.punchTime = punchTime;
        this.userID = userID;
        this.serverTime = serverTime;
    }

    public Integer getId() {
        return id;
    }

    public String getPunchType() {
        return punchType;
    }

    public Integer getUserID() {
        return userID;
    }

    public Double getSpeed(){
        return speed;
    }

    public Double getForce(){
        return force;
    }

    public String getPunchTime(){
        return punchTime;
    }

    public String getServerTime(){
        return serverTime;
    }

    @Override
    public String toString() {
        return "DBTrainingpunchDetail [id=" + id + ", punchType=" + punchType
                + ", speed=" + speed + ", force=" + force +", userID=" + userID + ", punchTime=" + punchTime + ", servertime = " + serverTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected DBTrainingPunchDetailDTO(Parcel in){
        this.id = in.readInt();
        this.punchType = in.readString();
        this.punchTime = in.readString();
        this.userID = in.readInt();
        this.speed = in.readDouble();
        this.force = in.readDouble();
        this.userID = in.readInt();
        this.serverTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.punchType);
        dest.writeString(this.punchTime);
        dest.writeInt(this.userID);
        dest.writeDouble(this.speed);
        dest.writeDouble(this.force);
        dest.writeString(this.serverTime);
    }

    public static final Creator<DBTrainingPunchDetailDTO> CREATOR = new Creator<DBTrainingPunchDetailDTO>() {
        @Override
        public DBTrainingPunchDetailDTO createFromParcel(Parcel source) {
            return new DBTrainingPunchDetailDTO(source);
        }

        @Override
        public DBTrainingPunchDetailDTO[] newArray(int size) {
            return new DBTrainingPunchDetailDTO[size];
        }
    };
}
